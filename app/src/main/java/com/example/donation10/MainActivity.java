package com.example.donation10;
/**
 * Author: Hoàng Văn Đô 19020251
 * Thực hành mobile: Bài 12
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.DonationApi;
import com.example.models.Donation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends Base {

    private Button donateButton;
    private RadioGroup paymentMethod;
    private ProgressBar progressBar;
    private NumberPicker amountPicker;
    private EditText amountText;
    private TextView amountTotal;
    public int totalDonated = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action",
                        Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        donateButton = (Button) findViewById(R.id.donateButton);

        paymentMethod = (RadioGroup) findViewById(R.id.paymentMethod);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        amountPicker = (NumberPicker) findViewById(R.id.amountPicker);
        amountText = (EditText) findViewById(R.id.paymentAmount);
        amountTotal = (TextView) findViewById(R.id.totalSoFar);

        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);
        progressBar.setMax(10000);
        amountTotal.setText("$" + app.totalDonated);
    }

    public void donateButtonPressed(View view) {
        String method = paymentMethod.getCheckedRadioButtonId() == R.id.PayPal ? "PayPal" : "Direct";
        int donatedAmount =  amountPicker.getValue();
        if (donatedAmount == 0)
        {
            String text = amountText.getText().toString();
            if (!text.equals(""))
                donatedAmount = Integer.parseInt(text);
        }
        if (donatedAmount > 0)
        {
            Donation new_donation = new Donation(donatedAmount, method, 0);
            app.newDonation(new_donation);
            //new InsertTask(this).execute("/donation", new_donation);
            progressBar.setProgress(totalDonated);
            String totalDonatedStr = "$" + totalDonated;
            amountTotal.setText(totalDonatedStr);
        }

    }
    @Override
    public void reset(MenuItem item)
    {
        app.donations.clear();
        app.totalDonated = 0;
        amountTotal.setText("$" + app.totalDonated);
        progressBar.setProgress(app.totalDonated);
    }

    private class GetAllTask extends AsyncTask<String, Void, List<Donation>> {
        protected ProgressDialog dialog;
        protected Context context;
        public GetAllTask(Context context)
        {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving Donations List");
            this.dialog.show();
        }
        @Override
        protected List<Donation> doInBackground(String... params) {
            try {
                Log.v("donate", "Donation App Getting All Donations");
                return (List<Donation>) DonationApi.getAll((String) params[0]);
            }
            catch (Exception e) {
                Log.v("donate", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<Donation> result) {
            super.onPostExecute(result);
            //app.totalDonated = 0;
            //app.donations = result;
/*
            for (int i = 0; i < result.size(); i++) {
                app.totalDonated = app.totalDonated + result.get(i).amount;
            }

 */
            progressBar.setProgress(app.totalDonated);
            amountTotal.setText("$" + app.totalDonated);
            if (dialog.isShowing())
                dialog.dismiss();
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        new GetAllTask(this).execute("/donations");
    }
}
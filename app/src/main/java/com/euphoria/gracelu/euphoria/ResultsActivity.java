package com.euphoria.gracelu.euphoria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by gracelu on 1/10/18.
 */


public class ResultsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView resultsName = (TextView) findViewById(R.id.resultsName);
        String collegeName = getIntent().getStringExtra("collegeName");

        resultsName.setText(collegeName);
    }
}

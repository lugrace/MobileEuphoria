package com.euphoria.gracelu.euphoria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by gracelu on 1/10/18.
 */


public class ResultsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView resultsName = (TextView) findViewById(R.id.resultsName);
        TextView happinessIndex = (TextView) findViewById(R.id.happinessIndex);
        String collegeName = getIntent().getStringExtra("collegeName");

        resultsName.setText(collegeName);
    }

    public String[] retrieveTweets(String collegeName){
//        return ["idk"];
        return null;
    }

    public int findHappinessLevel(String[] tweets){
        return 0;
    }

}

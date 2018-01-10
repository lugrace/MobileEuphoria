package com.euphoria.gracelu.euphoria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText userInput;
    private TextView titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = (EditText)findViewById(R.id.userCollege);
        Button btn = (Button) findViewById(R.id.enterButton);
        titleName = (TextView)findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String collegeName = userInput.getText().toString();
                Intent i = new Intent(MainActivity.this, ResultsActivity.class);
                i.putExtra("collegeName", collegeName);
                startActivity(i);
            }
        });
    }
}

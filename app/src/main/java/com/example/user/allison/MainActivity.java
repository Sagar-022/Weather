package com.example.user.allison;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {


    RadioGroup rad;
    Spinner spinner;
    Button go;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rad = findViewById(R.id.radio_unit);
        go = findViewById(R.id.activity_main_go);


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isCel = true;
                switch (rad.getCheckedRadioButtonId()) {
                    case R.id.activity_main_celcius:
                        isCel = true;
                        break;

                    case R.id.activity_main_fahrenheit:
                        isCel = false;
                        break;
                }

                LocationGetter getter = new LocationGetter(isCel);
                EditText edtCity = (EditText) findViewById(R.id.edtCity);
                String city = edtCity.getText().toString();
                getter.execute(city, getApplicationContext());

            }
        });

    }
}

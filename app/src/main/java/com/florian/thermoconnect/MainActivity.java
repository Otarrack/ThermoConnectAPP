package com.florian.thermoconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    TextView temperatureTextView;
    TextView humidityTextView;

    FirebaseDatabase database;

    double temperature;
    double humidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        initViews();
        database = FirebaseDatabase.getInstance();
        
        initValues();
    }

    private void initViews() {
        temperatureTextView = findViewById(R.id.main_temperature_value);
        humidityTextView = findViewById(R.id.main_humidity_value);
    }

    private void initValues() {

        final DatabaseReference temperatureRef = database.getReference("temperature");

        Log.d("TAG", temperatureRef.getKey());
        // Read from the database
        temperatureRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object result = dataSnapshot.getValue();
                temperature = Double.parseDouble(String.valueOf(result));
                temperatureTextView.setText(String.format("%s C°", NumberFormat.getInstance().format(temperature)));
                setTemperatureColor();
                Log.d("TAG", "Value is: " + temperature + " C°");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        final DatabaseReference humidityRef = database.getReference("humidity");

        // Read from the database
        humidityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object result = dataSnapshot.getValue();
                humidity = Double.parseDouble(String.valueOf(result));
                humidityTextView.setText(String.format("%s", NumberFormat.getInstance().format(humidity)));
                setHumidityColor();
                Log.d("TAG", "Value is: " + humidity);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    public void setHumidityColor() {
        if (humidity < 20) {
            humidityTextView.setTextColor(getResources().getColor(R.color.humidity_very_low));

        } else if (humidity < 40) {
            humidityTextView.setTextColor(getResources().getColor(R.color.humidity_low));

        } else if (humidity < 60) {
            humidityTextView.setTextColor(getResources().getColor(R.color.humidity_mid));

        } else if (humidity < 80) {
            humidityTextView.setTextColor(getResources().getColor(R.color.humidity_high));

        } else {
            humidityTextView.setTextColor(getResources().getColor(R.color.humidity_very_high));

        }
    }

    public void setTemperatureColor() {
        if (temperature < 22) {
            temperatureTextView.setTextColor(getResources().getColor(R.color.temperature_very_low));

        } else if (temperature < 24) {
            temperatureTextView.setTextColor(getResources().getColor(R.color.temperature_low));

        } else if (temperature < 26) {
            temperatureTextView.setTextColor(getResources().getColor(R.color.temperature_mid));

        } else if (temperature < 28) {
            temperatureTextView.setTextColor(getResources().getColor(R.color.temperature_high));

        } else {
            temperatureTextView.setTextColor(getResources().getColor(R.color.temperature_very_high));

        }
    }
}

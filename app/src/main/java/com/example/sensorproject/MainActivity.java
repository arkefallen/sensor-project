package com.example.sensorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // Layanan sistem untuk mengakses sensor
    private SensorManager sensorManager;

    private TextView textLightSensor;
    private TextView textProximitySensor;

    private Sensor lightSensor;
    private Sensor proximitySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi instance dari sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Mengambil semua daftar sensor yang ada dan disimpan dalam List bertipe data Sensor
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        // Cetak satu-per-satu semua sensor yang berada di dalam list menjadi string
        StringBuilder sensorText = new StringBuilder();
        for ( Sensor currentSensor : sensorList ) {
            Log.d("Sensor : ", currentSensor.getName());
        }

        // Inisialisasi text masing - masing sensor
        textLightSensor = findViewById(R.id.light_label);
        textProximitySensor = findViewById(R.id.proximity_label);

        // Ambil sensor dan simpan ke variabel
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Daftarkan listener untuk light sensor
        if ( lightSensor != null ) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        // Daftarkan listener untuk proximity sensor
        if ( proximitySensor != null ) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // Dieksekusi ketika adanya perubahan data pada sensor
    @Override
    public void onSensorChanged(SensorEvent event) {

        // Mengambil tipe sensor yang dipakai
        int type = event.sensor.getType();

        // Mengambil value yang dibaca oleh sensor
        float result = event.values[0];

        switch (type) {
            // Update aplikasi sesuai dengan sensor yang datanya berubah
            case Sensor.TYPE_LIGHT:
                textLightSensor.setText(getResources().getString(R.string.light_text,result));
                break;
            case Sensor.TYPE_PROXIMITY:
                textProximitySensor.setText(getResources().getString(R.string.proximity_text, result));
                break;
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unregister semua listener sensor yang ada dalam activity
        sensorManager.unregisterListener(this);
    }
}
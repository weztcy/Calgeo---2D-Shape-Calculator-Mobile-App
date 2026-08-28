package com.example.aplikasikalkulatorbidangdatar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText panlasdi, leting;
    TextView outputhasil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initcomponent();
    }
    private void initcomponent() {
        panlasdi = findViewById(R.id.input_panlasdi);
        leting = findViewById(R.id.input_leting);
        outputhasil = findViewById(R.id.output_hasil);
    }
    public void persegi(View v){
        double panjangper = Double.parseDouble(panlasdi.getText().toString());
        double lebarper = Double.parseDouble(leting.getText().toString());
        double luasper = panjangper * lebarper;
        double kelper = 2*(panjangper + lebarper);
        outputhasil.setText("- Persegi -\n"+"Luas = "+luasper+"\nKeliling = "+kelper);
    }
    public void segitiga(View v){
        double alasseg = Double.parseDouble((panlasdi.getText().toString()));
        double tinggiseg = Double.parseDouble((leting.getText().toString()));
        double sisimiring = Math.sqrt(Math.pow(alasseg,2)*Math.pow(tinggiseg,2));
        double luasseg = (alasseg * tinggiseg) / 2;
        double kelseg = (sisimiring*2)+alasseg;
        outputhasil.setText("- Segitiga -\n"+"Luas = "+luasseg+"\nKeliling = "+kelseg);
    }
    public void lingkaran(View v){
        double diameterling = Double.parseDouble(panlasdi.getText().toString());
        double kelling = Math.PI * diameterling;
        double luasling = Math.PI * Math.pow(diameterling, 2) / 4;
        outputhasil.setText("- Lingkaran -\n"+"Luas = "+kelling+"\nKeliling = "+luasling);
    }
}
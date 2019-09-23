package com.anta40.app.readjsonfromasset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText edtTeks;
    Button btnBaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBaca = (Button) findViewById(R.id.btnbaca);
        edtTeks = (EditText) findViewById(R.id.edt);

        btnBaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtTeks.getText().clear();
                String newText = readFileFromAsset(getApplicationContext(), R.raw.karyawan);

                try {
                    JSONObject jsObj = new JSONObject(newText);
                    JSONObject objKarwayan = jsObj.getJSONObject("DaftarKaryawan");
                    JSONArray arrKaryawan = objKarwayan.getJSONArray("Karyawan");

                    for (int x = 0; x < arrKaryawan.length(); x++){
                        JSONObject kobj = arrKaryawan.getJSONObject(x);
                        edtTeks.append(kobj.getString("nama"));
                        edtTeks.append(System.getProperty("line.separator"));
                        edtTeks.append(kobj.getString("nip"));
                        edtTeks.append(System.getProperty("line.separator"));
                        edtTeks.append(kobj.getString("email"));
                        edtTeks.append(System.getProperty("line.separator"));
                        edtTeks.append(System.getProperty("line.separator"));
                    }
                }
                catch (JSONException je){
                    edtTeks.setText(je.getMessage());
                }
            }
        });
    }

    private String readFileFromAsset(Context ctxt, int resourceId){
        InputStream is = ctxt.getResources().openRawResource(resourceId);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bfr = new BufferedReader(isr);
        StringBuilder sbtext = new StringBuilder();
        String line = "";

        try {
            while (( line = bfr.readLine()) != null) {
                sbtext.append(line);
            }
        }
        catch (IOException e) {
            return null;
        }

        return sbtext.toString();
    }
}

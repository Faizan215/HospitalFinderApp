package com.example.hospitalfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity {
    private final String[][] packages = {
            {"package 1 : Full Body Checkup", "Rs.1000"},
            {"package 2 : Blood Glucose Fasting", "Rs.999"},
            {"package 3 : Covid Antibody-Ig", "Rs.2000"},
            {"package 4 : Thyroid Check", "Rs.800"},
            {"package 5 : Immunity Check", "Rs.699"}
    };

    private final String[] package_details = {
            "Blood Glucose Fasting\nComplete Hemogram\nIron Studies\nKidney Function Test\nLDH Lactate Dehydrogenase,Serum\nLipid Profile\nLiver Function Test",
            "Blood Glucode Fasting",
            "Covid-19 Antibody-Ig",
            "Thyroid Profile-Total(T3,T4 & TSH)",
            "Complete Hemogram\nCRP ( C Reactive Protein) Quantitative, Serum\nIron Studies\nKidney Function Test\nLiver Function Test\nLipid Profile"
    };

    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    Button btnGoToCart, btnBack;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        btnGoToCart = findViewById(R.id.buttonLTGoToCart);
        btnBack = findViewById(R.id.ButtonLTBack);
        listView = findViewById(R.id.ListViewBMCart);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LabTestActivity", "Back button clicked");
                startActivity(new Intent(LabTestActivity.this, HomeActivity.class));
            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", "");
            item.put("line4", "");
            item.put("line5", "Total Cost: " + packages[i][1]);
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c,});
        listView.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Log.d("LabTestActivity", "Item clicked: " + i);
                Intent it = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
                it.putExtra("text1", packages[i][0]);
                it.putExtra("text2", package_details[i]);
                it.putExtra("text3", packages[i][1]);
                startActivity(it);
            }
        });

        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LabTestActivity", "Go to Cart button clicked");
                startActivity(new Intent(LabTestActivity.this, CartLabActivity.class));
            }
        });
    }
}

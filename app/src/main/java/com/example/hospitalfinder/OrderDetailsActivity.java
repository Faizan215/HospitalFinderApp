package com.example.hospitalfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderDetailsActivity extends AppCompatActivity {

    private String[][] orderdetails = {
            {"Dolo Tablet x 2", "Delivery Location: Bengaluru", "Rs. 50", "Bengaluru"},
            {"Full Check-Up", "Lab Test: Complete Health Check-Up", "Rs. 2450", "Bengaluru"}
    };

    HashMap<String, String> item;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    ListView listView;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        btnBack = findViewById(R.id.buttonODBack);
        listView = findViewById(R.id.listViewOD);

        // Navigate back to the HomeActivity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderDetailsActivity.this, HomeActivity.class));
            }
        });

        // Prepare list data for SimpleAdapter
        list = new ArrayList<>();
        for (int i = 0; i < orderdetails.length; i++) {
            item = new HashMap<>();
            item.put("line1", orderdetails[i][0]); // Product Name
            item.put("line2", orderdetails[i][1]); // Product Description
            item.put("line3", orderdetails[i][2]); // Price
            item.put("line4", "Delivery: " + orderdetails[i][3]); // Delivery Info
            list.add(item);
        }

        // Set up the adapter
        sa = new SimpleAdapter(
                this,
                list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d}
        );

        listView.setAdapter(sa);
    }
}
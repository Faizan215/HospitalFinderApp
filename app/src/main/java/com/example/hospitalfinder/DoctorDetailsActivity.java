package com.example.hospitalfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {
    private final String[][] doctor_details1 = {
            {"Doctor Name : DR.ANIL KUMAR VR", "MS Ramaiah:Banglore", "Exp:40yrs", "Mobile No:8062153400", "2500"},
            {"Doctor Name : DR.Mohamed Sheik", "Baptist:Banglore", "Exp:30yrs", "Mobile No:8022024700", "1500"},
            {"Doctor Name : DR.Vijay K", "Aster CMI :Banglore", "Exp:45yrs", "Mobile No:8045231245", "4500"},
            {"Doctor Name : DR.Leena", "Manipal Hospital:Banglore", "Exp:20yrs", "Mobile No:7899506432", "500"},
            {"Doctor Name : DR.Sumukha", "Sagar hospitals:Banglore", "Exp:10yrs", "Mobile No:9916504486", "1200"}
    };

    private final String[][] doctor_details2 = {
            {"Doctor Name : DR.Nisha Reddy", "Fortis:Banglore", "Exp:15yrs", "Mobile No:8023456789", "800"},
            {"Doctor Name : DR.Ramesh B", "Apollo:Banglore", "Exp:18yrs", "Mobile No:8034567890", "1000"},
            {"Doctor Name : DR.Kavitha S", "Columbia Asia:Banglore", "Exp:22yrs", "Mobile No:8045678901", "1200"},
            {"Doctor Name : DR.Siddharth Jain", "Narayana Health:Banglore", "Exp:20yrs", "Mobile No:8056789012", "1500"},
            {"Doctor Name : DR.Anita Desai", "Manipal Hospital:Banglore", "Exp:25yrs", "Mobile No:8067890123", "1400"}
    };

    private final String[][] doctor_details3 = {
            {"Doctor Name : DR.Rajesh Kumar", "Apollo:Banglore", "Exp:12yrs", "Mobile No:8078901234", "2500"},
            {"Doctor Name : DR.Priyanka D", "Fortis:Banglore", "Exp:10yrs", "Mobile No:8089012345", "3000"},
            {"Doctor Name : DR.Vikram Singh", "Narayana Health:Banglore", "Exp:15yrs", "Mobile No:8090123456", "3500"},
            {"Doctor Name : DR.Anjali Mishra", "Aster CMI:Banglore", "Exp:8yrs", "Mobile No:8101234567", "4000"},
            {"Doctor Name : DR.Swetha Rao", "Sparsh Hospital:Banglore", "Exp:14yrs", "Mobile No:8112345678", "2000"}
    };

    private final String[][] doctor_details4 = {
            {"Doctor Name : DR.Manish Gupta", "Jayadeva Institute:Banglore", "Exp:25yrs", "Mobile No:8123456789", "4500"},
            {"Doctor Name : DR.Sneha Kapoor", "Manipal Hospital:Banglore", "Exp:18yrs", "Mobile No:8134567890", "5000"},
            {"Doctor Name : DR.Abhay Verma", "Fortis:Banglore", "Exp:20yrs", "Mobile No:8145678901", "3500"},
            {"Doctor Name : DR.Swati Mehta", "Apollo:Banglore", "Exp:15yrs", "Mobile No:8156789012", "4000"},
            {"Doctor Name : DR.Krishna H", "Narayana Health:Banglore", "Exp:30yrs", "Mobile No:8167890123", "5500"}
    };

    TextView tv;
    Button btn;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv = findViewById(R.id.textViewBMCardTitle);
        btn = findViewById(R.id.ButtonLTBack);
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        String[][] doctor_details;
        switch (title) {
            case "Dietitian":
                doctor_details = doctor_details2;
                break;
            case "Dentist":
                doctor_details = doctor_details3;
                break;
            case "Cardiologist":
                doctor_details = doctor_details4;
                break;
            default: // "Family Physician" or any other case
                doctor_details = doctor_details1;
                break;
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });

        list = new ArrayList<>();
        for (String[] details : doctor_details) {
            HashMap<String, String> item = new HashMap<>();
            item.put("line1", details[0]);
            item.put("line2", details[1]);
            item.put("line3", details[2]);
            item.put("line4", details[3]);
            item.put("line5", "Cons Fees:" + details[4] + "/-");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}
        );
        ListView lst = findViewById(R.id.ListViewBMCart);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
                it.putExtra("text1", title);
                it.putExtra("text2", doctor_details[position][0]);
                it.putExtra("text3", doctor_details[position][1]);
                it.putExtra("text4", doctor_details[position][2]);
                it.putExtra("text5", doctor_details[position][3]);
                startActivity(it);
            }
        });
    }
}

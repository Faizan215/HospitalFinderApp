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
    private String[][] doctor_details1=
            {
                    {"Doctor Name : DR.ANIL KUMAR VR","MS Ramaiah:Banglore","Exp:40yrs","Mobile No:8062153400","2500"},
                    {"Doctor Name : DR.Mohamed Sheik","Baptist:Banglore","Exp:30yrs","Mobile No:8022024700","1500"},
                    {"Doctor Name : DR.Vijay K","Aster CMI :Banglore","Exp:45yrs","Mobile No:8045231245","4500"},
                    {"Doctor Name : DR.Leena","Manipal Hospital:Banglore","Exp:20yrs","Mobile No:7899506432","500"},
                    {"Doctor Name : DR.Sumukha","Sagar hospitals:Banglore","Exp:10yrs","Mobile No:9916504486","1200"}


            };
    TextView tv;
    Button btn;
    String[][] getDoctor_details1={};
    ArrayList list;
    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv=findViewById(R.id.textViewCardTitle);
        btn=findViewById(R.id.ButtonLTBack);
        Intent it=getIntent();
        String title=it.getStringExtra("title");
        tv.setText(title);

        assert title != null;
        String[][] doctor_details;
        if(title.compareTo("Family Physician")==0)
            doctor_details=doctor_details1;
        else
        if(title.compareTo("Deitician")==0)
            doctor_details=doctor_details1;
        else
        if(title.compareTo("Dentist")==0)
            doctor_details=doctor_details1;
        else
        if(title.compareTo("Surgeon")==0)
            doctor_details=doctor_details1;
        else
            doctor_details=doctor_details1;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailsActivity.this,FindDoctorActivity.class));
            }
        });

        list=new ArrayList();
        for(int i=0;i<doctor_details.length;i++){
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("line1",doctor_details[i][0]);
            item.put("line2",doctor_details[i][1]);
            item.put("line3",doctor_details[i][2]);
            item.put("line4",doctor_details[i][3]);
            item.put("line5","Cons Fees:"+doctor_details[i][4]+"/-");
            list.add(item);
        }
        sa = new SimpleAdapter(this,list,
                R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c}
        );
        ListView lst=findViewById(R.id.ListViewCart);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent it=new Intent(DoctorDetailsActivity.this,BookAppointmentActivity.class);
                it.putExtra("text1","title");
                int i = 0;
                it.putExtra("text2",doctor_details[i][0]);
                it.putExtra("text3",doctor_details[i][1]);
                it.putExtra("text4",doctor_details[i][2]);
                it.putExtra("text5",doctor_details[i][3]);
                startActivity(it);
            }
        });
    }
}

package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.demo.config.DatasourceHandler;
import com.example.demo.fragment.HistoryFragment;
import com.example.demo.model.Item;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddItemAcitvity extends AppCompatActivity implements View.OnClickListener {

    EditText title;
    Spinner category;
    EditText price;
    EditText date;
    private Button btnCancle;
    private DatasourceHandler db;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        init();
        button.setOnClickListener(this);
        date.setOnClickListener(this);
    }

    void init(){
        title=findViewById(R.id.title_add);
        category=findViewById(R.id.category_add);
        price=findViewById(R.id.price_add);
        date=findViewById(R.id.date_add);
        button=findViewById(R.id.btn_item_add);
        btnCancle=findViewById(R.id.btn_item_cancle);
        db=new DatasourceHandler(getApplicationContext());
        category.setAdapter(new ArrayAdapter<>(this,R.layout.item_spinner,getResources().getStringArray(R.array.category)));

    }


    @Override
    public void onClick(View view) {
        if(view==date){
            final Calendar calendar=Calendar.getInstance();
            int year= calendar.get(Calendar.YEAR);
            int day= calendar.get(Calendar.DAY_OF_MONTH);
            int month= calendar.get(Calendar.MONTH);
            DatePickerDialog dialog=new DatePickerDialog(AddItemAcitvity.this, (datePicker, y, m, d) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(y, m, d);
                date.setText(new SimpleDateFormat("dd/MM/yyyy").format(newDate.getTime()));
            },year,month,day);
            dialog.show();

        }

        if(view==button){
            String t=title.getText().toString();
            String c= category.getSelectedItem().toString();
            String p= price.getText().toString();
            String d=   date.getText().toString();
            Item item=new Item(
                t,c,p,d
            );
             db.insertItem(item);
             finish();
        }
        if(view==btnCancle){
            finish();
        }
    }
}
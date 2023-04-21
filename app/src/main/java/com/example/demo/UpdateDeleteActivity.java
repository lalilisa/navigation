package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.demo.config.DatasourceHandler;
import com.example.demo.model.Item;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener{
    EditText title;
    Spinner category;
    EditText price;
    EditText date;

    Item extraItem;

    private DatasourceHandler db;
    private Button buttonUpdate;

    private Button btnDelete;
    private Button btnCancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatedelete);
        init();
        date.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    void init(){
         title=findViewById(R.id.title_update);
         category=findViewById(R.id.category_update);
         price=findViewById(R.id.price_update);
         date=findViewById(R.id.date_update);
         buttonUpdate=findViewById(R.id.btn_item_update);
         btnDelete=findViewById(R.id.btn_item_delete);
        btnCancle=findViewById(R.id.btn_item_cancle);
         db=new DatasourceHandler(getApplicationContext());
         category.setAdapter(new ArrayAdapter<>(this,R.layout.item_spinner,getResources().getStringArray(R.array.category)));

        extraItem= (Item) getIntent().getSerializableExtra("item");

         for(int i=0;i< category.getCount();i++){
             if(category.getItemAtPosition(i).toString().equals(extraItem.getCategory())) {
                 category.setSelection(i);
                 break;
             }
         }

         title.setText(extraItem.getTitle());
         price.setText(extraItem.getPrice());
         date.setText(extraItem.getDate());

    }

    @Override
    public void onClick(View view) {
        if(view==date){
            final Calendar calendar=Calendar.getInstance();
            int year= calendar.get(Calendar.YEAR);
            int day= calendar.get(Calendar.DAY_OF_MONTH);
            int month= calendar.get(Calendar.MONTH);
            @SuppressLint("SimpleDateFormat") DatePickerDialog dialog=new DatePickerDialog(UpdateDeleteActivity.this, (datePicker, y, m, d) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(y, m, d);
                date.setText(new SimpleDateFormat("dd/MM/yyyy").format(newDate.getTime()));
            },year,month,day);
            dialog.show();
        }
        if(view==buttonUpdate){
            String t=title.getText().toString();
            String c= category.getSelectedItem().toString();
            String p= price.getText().toString();
            String d=   date.getText().toString();
            Item item=new Item(
                  extraItem.getId(), t,c,p,d
            );
            db.updateItem(item);
            finish();
        }
        if(view==btnDelete){
            db.deleteItem(extraItem.getId());
            finish();
        }
        if(view==btnCancle){
            finish();
        }
    }
}
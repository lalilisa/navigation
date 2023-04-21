package com.example.demo.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.AddItemAcitvity;
import com.example.demo.R;
import com.example.demo.adapter.RecyclerItemAdapter;
import com.example.demo.common.Utils;
import com.example.demo.config.DatasourceHandler;
import com.example.demo.model.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SearchFragment extends Fragment  implements View.OnClickListener {

    View view;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private Button btnSearch;
    private Spinner category;
    private EditText fromDate;
    private EditText toDate;

    private TextView totalPrice;
    private DatasourceHandler db;
    private RecyclerItemAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_search,container,false);
        return view;
    }


    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView=view.findViewById(R.id.searchView);
        btnSearch=view.findViewById(R.id.btn_search);
        category=view.findViewById(R.id.spinner_category);
        fromDate=view.findViewById(R.id.fromDate);
        toDate=view.findViewById(R.id.toDate);
        totalPrice =view.findViewById(R.id.tongTien_search);
        recyclerView=view.findViewById(R.id.recycler_search);
        db=new DatasourceHandler(getContext());
        List<String> cate= new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.category)));
        cate.add(0,"All");
        category.setAdapter(new ArrayAdapter<>(getContext(),R.layout.item_spinner,cate.toArray()));
         adapter=new RecyclerItemAdapter();
        List<Item> items=db.findAll();
        adapter.setList(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        totalPrice.setText("Tổng tiền: "+totalPrice(items));
        toDate.setOnClickListener(this);
        fromDate.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        onCategory();
        onSearchView();
    }


    @Override
    public void onClick(View view) {
        if(view==fromDate){
            final Calendar calendar=Calendar.getInstance();
            int year= calendar.get(Calendar.YEAR);
            int day= calendar.get(Calendar.DAY_OF_MONTH);
            int month= calendar.get(Calendar.MONTH);
            DatePickerDialog dialog=new DatePickerDialog(getActivity(), (datePicker, y, m, d) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(y, m, d);
                fromDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(newDate.getTime()));
            },year,month,day);
            dialog.show();
        }
        if(view==toDate){
            final Calendar calendar=Calendar.getInstance();
            int year= calendar.get(Calendar.YEAR);
            int day= calendar.get(Calendar.DAY_OF_MONTH);
            int month= calendar.get(Calendar.MONTH);
            DatePickerDialog dialog=new DatePickerDialog(getActivity(), (datePicker, y, m, d) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(y, m, d);
                toDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(newDate.getTime()));
            },year,month,day);
            dialog.show();
        }
        if (view==btnSearch){
            String from=fromDate.getText().toString();
            String to=toDate.getText().toString();
            if(!Utils.isEmpty(from)&&!Utils.isEmpty(to)){
                List<Item> itemsBydate=db.getItemsByBetweenDate(from,to);
                adapter.setList(itemsBydate);
                totalPrice.setText("Tổng tiền: "+totalPrice(itemsBydate));
            }
        }
    }
    public Integer totalPrice(List<Item> items){

        return  items.stream().mapToInt(item -> {
            Integer price=Integer.parseInt(item.getPrice());
            System.out.println(price);
            return  price;
        }).sum();
    }
 private void onSearchView(){
     searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextSubmit(String s) {
             return false;
         }

         @Override
         public boolean onQueryTextChange(String s) {
             System.out.println(s);
             List<Item> itemsByTitle=db.getItemsByTitle(s);
             totalPrice.setText("Tổng tiền: "+totalPrice(itemsByTitle));
             adapter.setList(itemsByTitle);
             return true;
         }
     });
 }
 private   void  onCategory(){
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String c= category.getSelectedItem().toString();
                List<Item> items1=new ArrayList<>();
                if(c.equalsIgnoreCase("all"))
                    items1=db.findAll();
                else
                    items1=db.getItemsByCategory(c);
                 totalPrice.setText("Tổng tiền: "+totalPrice(items1));
                adapter.setList(items1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}

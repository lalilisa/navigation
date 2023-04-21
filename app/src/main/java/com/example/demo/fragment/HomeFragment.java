package com.example.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.UpdateDeleteActivity;
import com.example.demo.adapter.RecyclerItemAdapter;
import com.example.demo.common.Utils;
import com.example.demo.config.DatasourceHandler;
import com.example.demo.model.Item;

import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements RecyclerItemAdapter.ItemListener{

    private View view;
    private RecyclerView recyclerView;
    private RecyclerItemAdapter adapter;
    private DatasourceHandler datasourceHandler;

    private TextView total;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Item> items ;
        recyclerView=view.findViewById(R.id.recycler_home);
        total=view.findViewById(R.id.tvTong_home);
        adapter=new RecyclerItemAdapter();
        datasourceHandler=new DatasourceHandler(getContext());

        items=datasourceHandler.getItemsByDate(Utils.getTextCurrentDate("dd/MM/yyyy"));
        adapter.setList(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        total.setText("Tổng tiền :"+totalPrice(items));
    }


    @Override
    public void onResume() {
        super.onResume();
        adapter.setList(datasourceHandler.getItemsByDate(Utils.getTextCurrentDate("dd/MM/yyyy")));
    }

    @Override
    public void onItemClick(View view, int position) {
        Item item=adapter.getItem(position);
        Intent intent=new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item",item);
        startActivity(intent);
    }

    public Integer totalPrice(List<Item> items){

     return  items.stream().mapToInt(item -> {
         Integer price=Integer.parseInt(item.getPrice());
         System.out.println(price);
         return  price;
     }).sum();
    }
}

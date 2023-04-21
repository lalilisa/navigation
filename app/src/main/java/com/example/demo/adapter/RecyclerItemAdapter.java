package com.example.demo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.ItemHolder>{

    public interface ItemListener{

        void onItemClick(View view,int position);
    }

    private ItemListener itemListener;
    private List<Item> items=new ArrayList<>();
    private Context context;

    public void setItemListener(ItemListener itemListener){
        this.itemListener=itemListener;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Item> items){
        this.items=items;
        notifyDataSetChanged();
    }
    public Item getItem(int position){
        return  items.get(position);
    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item item=items.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.category.setText(item.getCategory());
        holder.date.setText(item.getDate());
        holder.totalPrice.setText(item.getPrice());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvTitle;
        private TextView date;
        private TextView category;
        private TextView totalPrice;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.title_item);
            category=itemView.findViewById(R.id.category_item);
            date=itemView.findViewById(R.id.date_item);
            totalPrice=itemView.findViewById(R.id.tongtien_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null)
                itemListener.onItemClick(view,getAdapterPosition());
        }
    }
}

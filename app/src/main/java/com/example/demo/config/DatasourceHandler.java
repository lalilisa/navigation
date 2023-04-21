package com.example.demo.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.demo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class DatasourceHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="qlct.db";
    private static final Integer VERSION=1;

    private static final String TABLE_NAME="items";
    public DatasourceHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery="create table items ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "category TEXT," +
                "price TEXT," +
                "date TEXT" +
                " )";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    public List<Item> findAll(){
        List<Item> items=new ArrayList<>();
        String sql ="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase repo= getReadableDatabase();
        String order="date DESC";
        Cursor rs= repo.query("items",null,null,null,null,null,order);

        while (!rs.isAfterLast()&& rs.moveToNext()){
                Integer id=rs.getInt(0);
                String title=rs.getString(1);
                String category=rs.getString(2);
                String price=rs.getString(3);
                String date=rs.getString(4);
                Item item=new Item(id,title,category,price,date);
                items.add(item);
        }
        return items;
    }

    public void insertItem(Item item){
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",item.getTitle());
        contentValues.put("category",item.getCategory());
        contentValues.put("price",item.getPrice());
        contentValues.put("date",item.getDate());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_NAME,null,contentValues);
    }
    public List<Item> getItemsByDate(String date){
        List<Item> items=new ArrayList<>();
        String whereClause = "date like ?";
        String [] args=new String[]{date};
        SQLiteDatabase db=getReadableDatabase();
        Cursor rs=db.query(TABLE_NAME,null,whereClause,args,null,null,null);
        while (!rs.isAfterLast()&& rs.moveToNext()){
            Integer id=rs.getInt(0);
            String title=rs.getString(1);
            String category=rs.getString(2);
            String price=rs.getString(3);
            String dateS=rs.getString(4);
            Item item=new Item(id,title,category,price,dateS);
            items.add(item);
        }
        return items;
    }
    public void updateItem(Item item){
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",item.getTitle());
        contentValues.put("category",item.getCategory());
        contentValues.put("price",item.getPrice());
        contentValues.put("date",item.getDate());
        SQLiteDatabase db=getWritableDatabase();
        db.update(TABLE_NAME,contentValues,"id = ?",new String[]{item.getId().toString()});
    }

    public void deleteItem(Integer id){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_NAME,"id = ?",new String[]{String.valueOf(id)});
    }
    public List<Item> getItemsByCategory(String category){
        List<Item> items=new ArrayList<>();
        String whereClause = "category like ?";
        String [] args=new String[]{category};
        SQLiteDatabase db=getReadableDatabase();
        Cursor rs=db.query(TABLE_NAME,null,whereClause,args,null,null,null);
        while (!rs.isAfterLast()&& rs.moveToNext()){
            Integer id=rs.getInt(0);
            String title=rs.getString(1);
            String categorys=rs.getString(2);
            String price=rs.getString(3);
            String dateS=rs.getString(4);
            Item item=new Item(id,title,categorys,price,dateS);
            items.add(item);
        }
        return items;
    }
    public List<Item> getItemsByTitle(String titles){
        System.out.println(titles);
        List<Item> items=new ArrayList<>();
        String whereClause = " title like ?";
        String [] args=new String[]{"%"+titles+"%"};
        SQLiteDatabase db=getReadableDatabase();
        Cursor rs=db.query(TABLE_NAME,null,whereClause,args,null,null,null);
        while (!rs.isAfterLast()&& rs.moveToNext()){
            Integer id=rs.getInt(0);
            String title=rs.getString(1);
            String categorys=rs.getString(2);
            String price=rs.getString(3);
            String dateS=rs.getString(4);
            Item item=new Item(id,title,categorys,price,dateS);
            items.add(item);
        }
        return items;
    }
    public List<Item> getItemsByBetweenDate (String from ,String to){
        List<Item> items=new ArrayList<>();
        String whereClause = String.format("%s between ? and ?","date");
        String [] args=new String[]{from.toString(),to.toString()};
        SQLiteDatabase db=getReadableDatabase();
        Cursor rs=db.query(TABLE_NAME,null,whereClause,args,null,null,null);
        while (!rs.isAfterLast()&& rs.moveToNext()){
            Integer id=rs.getInt(0);
            String title=rs.getString(1);
            String categorys=rs.getString(2);
            String price=rs.getString(3);
            String dateS=rs.getString(4);
            Item item=new Item(id,title,categorys,price,dateS);
            items.add(item);
        }
        return items;
    }
}

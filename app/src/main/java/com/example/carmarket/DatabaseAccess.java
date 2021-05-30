package com.example.carmarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {
    private static DatabaseAccess instance;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;

    private DatabaseAccess(Context context){
        this.openHelper=new myDatabase(context);
    }
    public static DatabaseAccess get_instance(Context context){
        if (instance==null){
            instance=new DatabaseAccess(context);
        }
        return instance;
    }
    public void open_db(){
        db=this.openHelper.getWritableDatabase();
    }
    public void close_db(){
        if (db!=null)
        db.close();
    }

    public boolean insert(car car){
        ContentValues contentValues=new ContentValues();
        contentValues.put(myDatabase.column_model,car.getModel());
        contentValues.put(myDatabase.column_color,car.getColor());
        contentValues.put(myDatabase.column_image,car.getImage());
        contentValues.put(myDatabase.column_description,car.getDescription());
        contentValues.put(myDatabase.column_distancePerLetter,car.getDpl());
        contentValues.put(myDatabase.column_phone,car.getPhone());
        long result=db.insert(myDatabase.TABLE_NAME,null,contentValues);
        return result != -1;
    }
    public boolean update(car car){
        ContentValues contentValues=new ContentValues();
        contentValues.put(myDatabase.column_model,car.getModel());
        contentValues.put(myDatabase.column_color,car.getColor());
        contentValues.put(myDatabase.column_image,car.getImage());
        contentValues.put(myDatabase.column_description,car.getDescription());
        contentValues.put(myDatabase.column_distancePerLetter,car.getDpl());
        contentValues.put(myDatabase.column_phone,car.getPhone());
        int result=db.update(myDatabase.TABLE_NAME,contentValues," id=? ",new String[]{String.valueOf(car.getId())});
        return result > 0;
    }
    public boolean delete(car car){

        int result=db.delete(myDatabase.TABLE_NAME,myDatabase.column_id + "=?",new String[]{car.getId()+""});
        return result > 0;
    }
    public ArrayList <car> get_cars(){
        ArrayList <car> cars=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from " +myDatabase.TABLE_NAME  ,null);
        if (cursor.moveToFirst() && cursor!=null)
        {
            do {
                int id=cursor.getInt(cursor.getColumnIndex(myDatabase.column_id));
                String model=cursor.getString(cursor.getColumnIndex(myDatabase.column_model));
                String color=cursor.getString(cursor.getColumnIndex(myDatabase.column_color));
                String image=cursor.getString(cursor.getColumnIndex(myDatabase.column_image));
                String description=cursor.getString(cursor.getColumnIndex(myDatabase.column_description));
                double dpl=cursor.getInt(cursor.getColumnIndex(myDatabase.column_distancePerLetter));
                int phone=cursor.getInt(cursor.getColumnIndex(myDatabase.column_phone));
                car car=new car(id,image,model,color,dpl,description,phone);
                cars.add(car);
            }
            while (cursor.moveToNext());
        }
        return cars;
    }
    public ArrayList<car> get_cars(String search){
        ArrayList<car>cars=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from " +myDatabase.TABLE_NAME + " where "+myDatabase.column_model+ " like ?",
                new String[]{"%"+search+"%"});
        if (cursor.moveToFirst() && cursor!=null)
        {
            do {
                int id=cursor.getInt(cursor.getColumnIndex(myDatabase.column_id));
                String model=cursor.getString(cursor.getColumnIndex(myDatabase.column_model));
                String color=cursor.getString(cursor.getColumnIndex(myDatabase.column_color));
                String image=cursor.getString(cursor.getColumnIndex(myDatabase.column_image));
                String description=cursor.getString(cursor.getColumnIndex(myDatabase.column_description));
                double dpl=cursor.getInt(cursor.getColumnIndex(myDatabase.column_distancePerLetter));
                int phone=cursor.getInt(cursor.getColumnIndex(myDatabase.column_phone));
                car car=new car(id,image,model,color,dpl,description,phone);
                cars.add(car);
            }while (cursor.moveToNext());

            }
        return cars;
        }

        public car get_car(String search){
        Cursor cursor=db.rawQuery("select * from " +myDatabase.TABLE_NAME + " where id=?",new String[]{search});
        if (cursor.moveToFirst() && cursor!=null)
        {

                int id=cursor.getInt(cursor.getColumnIndex(myDatabase.column_id));
                String model=cursor.getString(cursor.getColumnIndex(myDatabase.column_model));
                String color=cursor.getString(cursor.getColumnIndex(myDatabase.column_color));
                String image=cursor.getString(cursor.getColumnIndex(myDatabase.column_image));
                String description=cursor.getString(cursor.getColumnIndex(myDatabase.column_description));
                double dpl=cursor.getInt(cursor.getColumnIndex(myDatabase.column_distancePerLetter));
                int phone =cursor.getInt(cursor.getColumnIndex(myDatabase.column_phone));
                car car=new car(id,image,model,color,dpl,description,phone);
                return car;
            }
        return null;
        }

}


package com.example.griview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME,null,Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PERSON_DETAILS_TABLE="CREATE TABLE "+ Util.TABLE_NAME+"("
                + Util.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ Util.KEY_NAME+ " TEXT,"
                +Util.KEY_LINK+" TEXT"+")";

        db.execSQL(CREATE_PERSON_DETAILS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE= String.valueOf(R.string.drop_table);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        onCreate(db);
    }

    public void addDetails(Website website){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Util.KEY_NAME,website.getName());
        values.put(Util.KEY_LINK,website.getLink());

        db.insert(Util.TABLE_NAME,null,values);
        db.close();
    }

    public Website getDetails(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(Util.TABLE_NAME,
                new String[]{Util.KEY_ID,Util.KEY_NAME,Util.KEY_LINK},
                Util.KEY_ID +"=?",new String[]{String.valueOf(id)},
                null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        Website website=new Website();
        website.setId(Integer.parseInt(cursor.getString(0)));
        website.setName(cursor.getString(1));
        website.setLink(cursor.getString(2));
        return website;
    }

    public List<Website> getAllDetails(){
        List<Website> personDetails=new ArrayList<>();

        SQLiteDatabase db=getReadableDatabase();

        String selectAll="SELECT * FROM "+Util.TABLE_NAME;
        Cursor cursor=db.rawQuery(selectAll,null);

        if(cursor.moveToFirst()){
            do{
                Website website=new Website();
                website.setId(Integer.parseInt(cursor.getString(0)));
                website.setName(cursor.getString(1));
                website.setLink(cursor.getString(2));
                personDetails.add(website);
            }while(cursor.moveToNext());
        }
        return personDetails;
    }

    public int updateDetails(Website website){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(Util.KEY_NAME,website.getName());
        values.put(Util.KEY_LINK,website.getLink());

        return db.update(Util.TABLE_NAME,values,Util.KEY_ID +"=?",
                new String[]{String.valueOf(website.getId())});
    }

    public void deleteContact(Website person){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.KEY_ID +"=?",
                new String[]{String.valueOf(person.getId())});
        db.close();
    }
    public int getCount(){
        String countQuery="SELECT*FROM "+Util.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);
        return cursor.getCount();
    }
}
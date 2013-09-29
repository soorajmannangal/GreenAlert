package com.green;

import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBAdapter {

public static final String KEY_ROWID = "_id";
public static final String KEY_PHONE = "phoneno";
public static final String KEY_NAME = "contname";//donot change use in contactList
public static final String KEY_TYPE = "category";
private static final String TAG = "DBAdapter";

private static final String DATABASE_NAME = "greenalert.sql";
private static final String CONTACT_TABLE = "contnames";
private static final String TBL_QUEUE="TBL_QUEUE";
public static final String QUEUE_MOD="MOD";

private static final int DATABASE_VERSION = 1;

private static final String TBL_USER="TBL_USER";
public static final String KEY_USER_NAME="NAME";

private static final String DATABASE_CREATE =
"create table if not exists "+CONTACT_TABLE+" ("+KEY_ROWID+" integer primary key autoincrement, "
+ KEY_PHONE+" text not null, "+KEY_NAME+" text not null, "
+KEY_TYPE+" text not null);";

private static final  String usertbl_create="create table if not exists "+TBL_USER+"("+KEY_USER_NAME+" text);";

private static final  String queuetbl_create="create table if not exists "+TBL_QUEUE+"("+QUEUE_MOD+" text);";

private final Context context;

private DatabaseHelper DBHelper;
private SQLiteDatabase db;

public DBAdapter(Context ctx)
{
this.context = ctx;
DBHelper = new DatabaseHelper(context);
}

private static class DatabaseHelper extends SQLiteOpenHelper
{
	
DatabaseHelper(Context context)
{
super(context, DATABASE_NAME, null, DATABASE_VERSION);
}

@Override
public void onCreate(SQLiteDatabase db)
{
db.execSQL(DATABASE_CREATE);
db.execSQL(usertbl_create);
db.execSQL(queuetbl_create);
}

@Override
public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	// TODO Auto-generated method stub
	
}

}

//---opens the database---
public DBAdapter open() throws SQLException
{
db = DBHelper.getWritableDatabase();
return this;
}

//---closes the database---
public void close()
{
DBHelper.close();
}


//Get name for grid
public Cursor getallname(String type)
{
	Cursor c=db.query(CONTACT_TABLE, new String[]{KEY_ROWID,KEY_NAME}, KEY_TYPE+"='"+type+"'", null, null, null,KEY_NAME );
	return c;
}

public Cursor checkName(String type,String name)
{
	return db.query(CONTACT_TABLE, new String[]{KEY_PHONE}, KEY_TYPE+"='"+type+"' and "+KEY_NAME +"='"+name.trim() +"'", null, null, null,KEY_NAME );
	
}
public Cursor checkPhone(String type,String phone)
{
	return db.query(CONTACT_TABLE, new String[]{KEY_NAME}, KEY_TYPE+"='"+type+"' and "+ KEY_PHONE +"='"+ phone.trim()+"'", null, null, null,KEY_NAME );	
}


//---insert a title into the database---
public long insertTitle(String phoneno, String contname, String category)
{
ContentValues initialValues = new ContentValues();
initialValues.put(KEY_PHONE, phoneno.trim());
initialValues.put(KEY_NAME, contname.trim());
initialValues.put(KEY_TYPE, category.trim());
return db.insert(CONTACT_TABLE, null, initialValues);
}

public long insUsrName(String name)
{
	ContentValues initval=new ContentValues();
	initval.put(KEY_USER_NAME, name.trim());
	
	db.delete(TBL_USER, null, null);
	return db.insert(TBL_USER, null, initval);
}
public Cursor fetchuserName(){
	return db.query(TBL_USER, new String[]{ KEY_USER_NAME}, null, null, null, null, null);
}

public void pushmod(String mod)
{
	
	ContentValues initval=new ContentValues();
	initval.put(QUEUE_MOD, mod.trim());

	 db.insert(TBL_QUEUE, null, initval);
}

	public Cursor popmod(){
		
		Cursor c1= db.query(TBL_QUEUE, new String[]{ QUEUE_MOD}, null, null, null, null, null);
		return c1;
	}

	public void clearq()
	{
		db.delete(TBL_QUEUE, null, null);
	}
	public Cursor findContact(String name,String type){
		
		return db.query(CONTACT_TABLE, new String[] { KEY_PHONE
		 },KEY_NAME +
		"='"+name+"' and "+ KEY_TYPE+"='"+type +"'", null, null,
       null, null);
		
		
	}
	public void deleteContact (String name,String mod){
		db.delete(CONTACT_TABLE, KEY_TYPE +
				"='"+mod+"' and "+KEY_NAME+" ='"+name+"'", null);
	}
	public void updateContact(String name,String orName,String phone,String orPhone,String mod){
		
		ContentValues args = new ContentValues();
		args.put(KEY_PHONE, phone.trim());
		args.put(KEY_NAME, name.trim());
		args.put(KEY_TYPE, mod.trim());
		
		db.delete(CONTACT_TABLE, KEY_TYPE +
				"='"+mod+"' and "+KEY_NAME+" ='"+orName+"'", null);
	
		db.insert(CONTACT_TABLE, null, args);
	}
	
public Cursor fetchAllScores(String mod) {
    return db.query(CONTACT_TABLE, new String[] { KEY_PHONE,
    		KEY_NAME },KEY_TYPE +
    		"='"+mod+"'", null, null,
            null, null);
}

//---deletes a particular contname---
public boolean deleteTitle(String mod)
{

return db.delete(CONTACT_TABLE, KEY_TYPE +
"='"+mod+"'", null) > 0;
}

//---retrieves all the titles---
public Cursor getAllTitles(String mod)
{
return db.query(CONTACT_TABLE, new String[] {
KEY_ROWID,
KEY_PHONE,
KEY_NAME,
KEY_TYPE},
KEY_TYPE +
"='"+mod+"'",null,null,null,null);
}

//---retrieves a particular title---
public Cursor getTitle(long rowId) throws SQLException
{
Cursor mCursor =
db.query(true, CONTACT_TABLE, new String[] {
KEY_ROWID,
KEY_PHONE,
KEY_NAME,
KEY_TYPE
},
KEY_ROWID + "=" + rowId,
null,null,null,null,null);
if (mCursor != null) {
mCursor.moveToFirst();
}
return mCursor;
}

//---updates a title---
public boolean updateTitle(long rowId, String phoneno,
String contname, String category)
{
ContentValues args = new ContentValues();
args.put(KEY_PHONE, phoneno.trim());
args.put(KEY_NAME, contname.trim());
args.put(KEY_TYPE, category.trim());
return db.update(CONTACT_TABLE, args,
KEY_ROWID + "=" + rowId, null) > 0;
}
}
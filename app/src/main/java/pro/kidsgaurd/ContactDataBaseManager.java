package pro.kidsgaurd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactDataBaseManager extends SQLiteOpenHelper {
    private static final String DBName="myfo";
    private static final int DBVersion=1;
    private  final String ID="id";
    private  final String NAME="name";
    private  final String NUMBER="number";
    private  final String TABLENAME="tablecontact";


    public ContactDataBaseManager(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
        String cQuery="CREATE TABLE "+TABLENAME+"("+ID+" INTEGER PRIMARY KEY,"+NAME+" TEXT,"+NUMBER+" TEXT);";
        mydb.execSQL(cQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void Insertcontact(String name, String number){
        SQLiteDatabase idb=this.getWritableDatabase();
        ContentValues icv=new ContentValues();
        icv.put(NAME,name);
        icv.put(NUMBER,number);
        idb.insert(TABLENAME,null,icv);
        idb.close();
    }
    public ArrayList<String> getcontact(){
        ArrayList<String> conta=new ArrayList<String>();
        SQLiteDatabase gdb=this.getReadableDatabase();
        String gQuery="SELECT * FROM tablecontact";
        Cursor gCur=gdb.rawQuery(gQuery,null);
       if (gCur.moveToFirst()){
            while (gCur.moveToNext()){
                conta.add(gCur.getString(2));

            }}
        return conta;
}
    public void delall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM tablecontact"); //delete all rows in a table
        db.close();
    }}

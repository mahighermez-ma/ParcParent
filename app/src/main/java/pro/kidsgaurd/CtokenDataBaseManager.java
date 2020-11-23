package pro.kidsgaurd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class CtokenDataBaseManager extends SQLiteOpenHelper {
    private static final String DBName="myinfotokenc";
    private static final int DBVersion=1;
    private static final String ID="id";
    private static final String NAME="name";
    private static final String TABLENAME="tablectoken";

    public CtokenDataBaseManager(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
        String cQuery="CREATE TABLE "+TABLENAME+"("+ID+" INTEGER PRIMARY KEY,"+NAME+" TEXT);";
        mydb.execSQL(cQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void Insertctoken(String ctoken){
        SQLiteDatabase idb=this.getWritableDatabase();
        ContentValues icv=new ContentValues();
        icv.put(NAME,ctoken);
        idb.insert(TABLENAME,null,icv);
        idb.close();
    }
    public String getctoken(){
        String ctokenget = "12";
        SQLiteDatabase gdb=this.getReadableDatabase();
        String gQuery="Select * From tablectoken";
        Cursor gCur=gdb.rawQuery(gQuery,null);
        if (gCur.moveToFirst()){

            ctokenget=gCur.getString(1);

        }
        return ctokenget;


    }
    public void Updatectoken(String token){
        SQLiteDatabase udb=this.getWritableDatabase();
        ContentValues ucv=new ContentValues();
        ucv.put(NAME,token);

        udb.update(TABLENAME,ucv,ID+"=?",new String[]{"1"});

    }
    public void delall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM tablectoken"); //delete all rows in a table
        db.close();
    }
}

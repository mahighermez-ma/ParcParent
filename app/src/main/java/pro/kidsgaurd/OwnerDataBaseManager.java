package pro.kidsgaurd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class OwnerDataBaseManager extends SQLiteOpenHelper {
    private static final String DBName="myinfo";
    private static final int DBVersion=1;
    private static final String ID="id";
    private static final String NAME="name";
    private static final String TABLENAME="tableowner";


    public OwnerDataBaseManager(@Nullable Context context) {
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
    public void Insertowner(String owner){
        SQLiteDatabase idb=this.getWritableDatabase();
        ContentValues icv=new ContentValues();
        icv.put(NAME,owner);
        idb.insert(TABLENAME,null,icv);
        idb.close();
    }
    public String getowner(){
        String ownerget = null;
        SQLiteDatabase gdb=this.getReadableDatabase();
        String gQuery="SELECT * FROM tableowner";
        Cursor gCur=gdb.rawQuery(gQuery,null);
        if (gCur.moveToFirst()){

            ownerget=gCur.getString(1);

        }
        return ownerget;


    }
    public void delall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM tableowner"); //delete all rows in a table
        db.close();
    }


}

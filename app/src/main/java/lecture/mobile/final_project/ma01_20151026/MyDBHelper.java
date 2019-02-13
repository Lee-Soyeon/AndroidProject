package lecture.mobile.final_project.ma01_20151026;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    public final static String DB_NAME = "my_db";
    public final static String TABLE_NAME = "my_table";

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ( _id integer primary key autoincrement, title text, address text, memo text, photo text);") ;

        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '라고파스타', '대한민국 서울특별시 양천구 목동동로 293', '크림파스타가 맛있음', null);");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '샤이바나', '대한민국 서울특별시 양천구 목동동로 293', '미국 가정식', null);");
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " values (null, '엉터리생고기', '대한민국 서울특별시 양천구 목동 105 동양파라곤', '고기 너무 좋음', null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}

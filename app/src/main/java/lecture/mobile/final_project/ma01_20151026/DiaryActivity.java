package lecture.mobile.final_project.ma01_20151026;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {

    private ListView lvLocationList;
    private ArrayAdapter<MyLocation> myAdapter;
    private MyDBHelper myDBHelper;
    private ArrayList<MyLocation> itemList;

    private ImageView mImageView;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        lvLocationList = (ListView) findViewById(R.id.lvLocationList);

        myAdapter = new ArrayAdapter<MyLocation>(this, android.R.layout.simple_list_item_1);

        lvLocationList.setAdapter(myAdapter);

        myDBHelper = new MyDBHelper(this);

        mImageView = (ImageView) findViewById(R.id.iv_diary);

        lvLocationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final MyLocation item = itemList.get(position);

                SQLiteDatabase db = myDBHelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select * from " + MyDBHelper.TABLE_NAME + " where _id = " + item.get_id(), null);
                int __id;
                String _title;
                String _address;
                String _memo;
                String photo = "";

                while (cursor.moveToNext()) {
                    __id = cursor.getInt(0);
                    _title = cursor.getString(1);
                    _address = cursor.getString(2);
                    _memo = cursor.getString(3);
                    photo = cursor.getString(4);
                }
                mCurrentPhotoPath = photo;
                myDBHelper.close();

                // Get the dimensions of the View
                int targetW = mImageView.getWidth();
                int targetH = mImageView.getHeight();

                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;

                Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                mImageView.setImageBitmap(bitmap);
            }
        });

        lvLocationList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                final MyLocation item = itemList.get(position);

                new AlertDialog.Builder(DiaryActivity.this)
                        .setTitle("삭제")
                        .setMessage(item.getTitle() + "을/를 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                SQLiteDatabase db = myDBHelper.getWritableDatabase();
                                db.execSQL("delete from " + MyDBHelper.TABLE_NAME + " where _id = " + item.get_id());
                                Toast.makeText(DiaryActivity.this, "삭제하였습니다.", Toast.LENGTH_SHORT).show();
                                myDBHelper.close();
                                refreshListView();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show();

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshListView();
    }

    private void refreshListView() {
        itemList = new ArrayList<>();

        SQLiteDatabase db = myDBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + MyDBHelper.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String title = cursor.getString(1);
            String address = cursor.getString(2);
            String memo = cursor.getString(3);

            itemList.add(new MyLocation(_id, title, address, memo, mCurrentPhotoPath));
        }
        cursor.close();

        myDBHelper.close();

        myAdapter.clear();

        myAdapter.addAll(itemList);
    }

}

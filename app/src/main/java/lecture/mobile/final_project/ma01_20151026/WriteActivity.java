package lecture.mobile.final_project.ma01_20151026;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class WriteActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 200;

    private EditText editText_title;
    private EditText editText_address;
    private EditText editText_memo;

    private ImageView mImageView;
    private String mCurrentPhotoPath;

    private MyDBHelper myDBHelper;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        editText_title = (EditText) findViewById(R.id.et_title);
        editText_address = (EditText) findViewById(R.id.et_address);
        editText_memo = (EditText) findViewById(R.id.et_memo);

        mImageView = (ImageView) findViewById(R.id.iv_write);

        File path = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        myDBHelper = new MyDBHelper(this);

        String title = "";
        String address = "";

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            title = "error";
        }
        else {
            title = extras.getString("title");
            address = extras.getString("address");
        }

        String str_title = title;
        editText_title.setText(str_title);
        String str_address = address;
        editText_address.setText(str_address);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                String title = editText_title.getText().toString();
                String address = editText_address.getText().toString();
                String memo = editText_memo.getText().toString();
                String photo = mCurrentPhotoPath;

                SQLiteDatabase db = myDBHelper.getWritableDatabase();

                ContentValues row = new ContentValues();

                row.put("title", title);
                row.put("address", address);
                row.put("memo", memo);
                row.put("photo", photo);

                db.insert(MyDBHelper.TABLE_NAME, null, row);

                myDBHelper.close();

                Toast.makeText(this, "저장하였습니다.", Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn_camera:
                dispatchTakePictureIntent();

                break;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void setPic() {
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
//        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic();
        }
    }


}

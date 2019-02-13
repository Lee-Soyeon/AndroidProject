package lecture.mobile.final_project.ma01_20151026;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);

                break;

            case R.id.btn_diary:
                intent = new Intent(this, DiaryActivity.class);
                startActivity(intent);

                break;

            case R.id.btn_recommend:
                intent = new Intent(this, RecommendActivity.class);
                startActivity(intent);

                break;

        }
    }
}

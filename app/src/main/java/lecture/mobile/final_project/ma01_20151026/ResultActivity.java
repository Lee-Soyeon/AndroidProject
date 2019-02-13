package lecture.mobile.final_project.ma01_20151026;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textView = (TextView)findViewById(R.id.tv_result);

        String title = "";

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            title = "error";
        }
        else {
            title = extras.getString("title");
        }

        textView.setText(title);
    }
}

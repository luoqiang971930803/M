package sum.bihu.com.m;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mIn;
    private String mI;
    private int mMInt;
    private String mMNamm;
    private String mNamm;
    private int mMain;
    private String mMNamm1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        //mMain = R.layout.activity_main;
        // int activity_main =  R.layout.activity_main;

      setContentView(R.layout.activity_main);
        init();
        init();
        init();
        mMInt = 0;
        String mMNamm1 =           "haha";
        init();
        Toast.makeText(this, "HAHAH", Toast.LENGTH_SHORT);

    }


    private void init() {
        mI = "'";
    }

    class text {
        private int age;
        private boolean gende;
        private String namm;
        private String amm;

        public text(int age, String namm, boolean gende) {
            this.age = age;
            this.gende = gende;
            this.namm = namm;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }




}



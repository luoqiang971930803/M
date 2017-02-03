package sum.bihu.com.refreshlist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<String> mList;
    private Myadapter mAdapter;
    private RefreshView mRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRefresh = (RefreshView) findViewById(R.id.refresh);
        mAdapter = new Myadapter();
        mRefresh.setAdapter(mAdapter);
        mRefresh.setRefreshListener(new RefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(300);
                            mList.add(0,"我是下拉加载更多的数据");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.notifyDataSetChanged();
                                    mRefresh.setNoshow();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }

            @Override
            public void onLoadMore() {

            }
        });
    }
    private void initData() {
        mList = new ArrayList<String>();
        for(int i=0;i<41;i++){
            mList.add("我是数据"+i);

        }
    }

   class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public String getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = getTextView(i, viewGroup);
            return textView;
        }

       @NonNull
       private TextView getTextView(int i, ViewGroup viewGroup) {
           TextView textView= new TextView(viewGroup.getContext());
           textView.setTextSize(18);
           textView.setText(mList.get(i));
           return textView;
       }
   }
}

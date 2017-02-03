package sum.bihu.com.refreshlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by luo on 2016/12/30.
 */
public class RefreshView extends ListView implements AbsListView.OnScrollListener{

    private float mDownY;
    private float mMoveY;
    private int mHearHeight;
    private int mFooterHeight;
    private LinearLayout mHearView;
    private LinearLayout mFooterView;
    public static final int PULL_TO_REFRESH = 0;// 下拉刷新
    public static final int RELEASE_REFRESH = 1;// 释放刷新
    public static final int REFRESHING = 2; // 刷新中
    private int currentState = PULL_TO_REFRESH; // 当前刷新模式
    private ImageView mPullImage;
    private RotateAnimation mPullRotate;
    private RotateAnimation mReleaseRotate;
    private TextView mTv1;
    private TextView mTv2;
    private ProgressBar mProgressBar;
    public RefreshView(Context context) {

        super(context);

        init();
    }

    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initHeaderView();
        initFooterView();
        setOnScrollListener(this);
        initAnimation();
    }

    private void initAnimation() {
        mPullRotate = new RotateAnimation(0f,-180f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        mPullRotate.setDuration(300);
        mPullRotate.setFillAfter(true);
        //mPullImage.setAnimation(pullRotate);

        mReleaseRotate = new RotateAnimation(-180f,-360f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        mPullRotate.setDuration(300);
        mPullRotate.setFillAfter(true);
        //mPullImage.setAnimation(releaseRotate);
    }


    private void initHeaderView() {
       mHearView = (LinearLayout) View.inflate(getContext(), R.layout.list_header, null);
        mPullImage = (ImageView) mHearView.findViewById(R.id.image);
        mProgressBar = (ProgressBar) mHearView.findViewById(R.id.progressBar);
        mTv1 = (TextView) mHearView.findViewById(R.id.tv1);
        mTv2 = (TextView) mHearView.findViewById(R.id.tv2);
       mHearView.measure(0,0);
       mHearHeight = mHearView.getMeasuredHeight();
       mHearView.setPadding(0,-mHearHeight,0,0);
        addHeaderView(mHearView);
    }
    private void initFooterView() {
        mFooterView = (LinearLayout) View.inflate(getContext(), R.layout.list_footer, null);
        mFooterView.measure(0,0);
        mFooterHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0,-mFooterHeight,0,0);
        addFooterView(mFooterView);
    }

   @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = ev.getY();
                if(currentState==REFRESHING){
                    return super.onTouchEvent(ev);
                }
                float mdy=mMoveY-mDownY;
                if(mdy>0&&getFirstVisiblePosition()==0){
                    int top= (int) (mdy-mHearHeight+0.5f);
                        mHearView.setPadding(0,top,0,0);
                    if(top<0&&currentState!=PULL_TO_REFRESH){
                        currentState=PULL_TO_REFRESH;
                        updateHeader(currentState);
                    }else if(top>=0&&currentState!=RELEASE_REFRESH){
                        currentState=RELEASE_REFRESH;
                        updateHeader(currentState);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if(currentState==PULL_TO_REFRESH){
                    mHearView.setPadding(0,-mHearHeight,0,0);
                    updateHeader(currentState);
                }else if (currentState==RELEASE_REFRESH){
                    currentState=REFRESHING;
                    mHearView.setPadding(0,0,0,0);
                    updateHeader(currentState);

                }
                break;
        }

        return super.onTouchEvent(ev);
    }
    public String getTime() {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(currentTimeMillis);

    }

    private void updateHeader(int currentState) {
        switch (currentState){
            case PULL_TO_REFRESH:
                mPullImage.startAnimation(mReleaseRotate);
                mTv1.setText("下拉刷新");
                break;
            case RELEASE_REFRESH:
                mPullImage.startAnimation(mPullRotate);
                mTv1.setText("释放刷新");
                break;
            case REFRESHING:
                mPullImage.clearAnimation();
                mProgressBar.setVisibility(View.VISIBLE);
                mPullImage.setVisibility(View.INVISIBLE);
                mTv1.setText("刷新中");
                mTv2.setText("最后刷新时间:"+getTime());
                mListener.onRefresh();
                break;
        }
    }
    private  boolean state=false;
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if(state){
            return;
        }
        if(scrollState == SCROLL_STATE_IDLE&&getLastVisiblePosition()==getCount()-1){
            mFooterView.setPadding(0,0,0,0);
            state=true;
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }
    public OnRefreshListener mListener;
    public interface OnRefreshListener{

        void onRefresh(); // 下拉刷新

        void onLoadMore();// 加载更多
    }
    public void setRefreshListener(OnRefreshListener mListener) {
        this.mListener = mListener;
    }
    public void setNoshow(){
        mHearView.setPadding(0,-mHearHeight,0,0);
    }
}
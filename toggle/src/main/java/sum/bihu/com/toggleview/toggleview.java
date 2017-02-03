package sum.bihu.com.toggleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by luo on 2017/1/18.
 */
public class toggleview extends View {
    private  boolean mSwitchState;

    public toggleview(Context context) {
        super(context);
    }

    public toggleview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public toggleview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        String namespace ="http://schemas.android.com/apk/res-auto";
        int switchBackgroundResource = attrs.getAttributeResourceValue(namespace , "switch_background", -1);
        int slideButtonResource = attrs.getAttributeResourceValue(namespace , "slide_button", -1);

        mSwitchState = attrs.getAttributeBooleanValue(namespace, "switch_state", false);
        setswitchBackgroundResource();
        setslideButtonResource();
    }

    private void setslideButtonResource() {
        //BitmapFactory.decodeResource()
    }

    private void setswitchBackgroundResource() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //setMeasuredDimension(int measuredWidth, int measuredHeight)
    }
}

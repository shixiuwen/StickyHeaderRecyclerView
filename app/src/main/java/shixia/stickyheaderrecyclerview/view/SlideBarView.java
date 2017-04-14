package shixia.stickyheaderrecyclerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShiXiuwen on 2017/4/13.
 * <p>
 * Description:侧边栏滑动定位索引
 */

public class SlideBarView extends View {

    private static final String TAG = SlideBarView.class.getSimpleName();
    private Context context;

    //侧边索引内容
    private List<String> indexList = new ArrayList<>();
    //索引字体大小
    private float textSize;
    //索引字体颜色
    private int textColor;
    //索引宽度
    private float indexWidth;
    private int indexHeight;

    private Paint textPain;

    private int currentSelectedIndex = -1;

    public SlideBarView(Context context) {
        this(context, null);
        setClickable(true);
        this.context = context;
    }

    public SlideBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        textColor = Color.argb(255, 255, 255, 255);

        textPain = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPain.setColor(textColor);
    }

    /**
     * 初始化侧边栏索引的内容
     *
     * @param indexList 侧边栏索引的内容列表
     */
    public void setIndexList(List<String> indexList) {
        this.indexList = indexList;
        requestLayout();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startLocation(event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                startLocation(event.getY());
                break;
            case MotionEvent.ACTION_UP:
                currentSelectedIndex = -1;
                break;
            default:
                break;
        }
        return true;
    }

    private void startLocation(float touchY) {
        int location = (int) (touchY / indexWidth);
        if (location < 0 || location >= indexList.size()) {
            return;
        }
        if (currentSelectedIndex == location) {
            return;
        }
        currentSelectedIndex = location;

        //选中改变后回调
        if (onIndexSelectedChangeListener != null) {
            onIndexSelectedChangeListener.onIndexSelectedChange(currentSelectedIndex, indexList.get(currentSelectedIndex));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //字体大小设置为索引宽度的80%
        textSize = indexWidth * 80 / 100;
        textPain.setTextSize(textSize);
        for (int i = 0; i < indexList.size(); i++) {
            canvas.drawText(indexList.get(i),
                    (indexWidth - textPain.measureText(indexList.get(i))) / 2,
                    (indexWidth + indexWidth * i) - (indexWidth / 2) + (Math.abs(textPain.ascent() + textPain.descent())) / 2, textPain);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            indexWidth = widthSize;
        } else {
            indexWidth = 50;
        }
        indexHeight = (int) (indexWidth * indexList.size());
        setMeasuredDimension((int) indexWidth, indexHeight);
    }


    /**
     * ------------------------------ 选中元素切换的回调 ------------------------------
     */

    private OnIndexSelectedChangeListener onIndexSelectedChangeListener;

    public interface OnIndexSelectedChangeListener {
        void onIndexSelectedChange(int indexSelected, String strIndex);
    }

    public void setOnIndexSelectedChangeListener(OnIndexSelectedChangeListener onIndexSelectedChangeListener) {
        this.onIndexSelectedChangeListener = onIndexSelectedChangeListener;
    }
}

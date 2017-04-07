package shixia.stickyheaderrecyclerview.sticky;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import shixia.stickyheaderrecyclerview.bean.CarBrandInfo;


/**
 * Created by ShiXiuwen on 2017/3/10.
 * <p>
 * Description:汽车品牌分割线，实现了标签吸顶粘滞滑动
 */

public class CarBrandItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private int normalDividerHeight;
    private int indexHeight;

    private Paint normalDividerPaint;
    private TextPaint indexTextPaint;
    private Paint topIndexPaint;

    private List<String> brandIndexList = new ArrayList<>();
    private List<CarBrandInfo> carBrandInfoList = new ArrayList<>();

    public CarBrandItemDecoration(Context context, List<CarBrandInfo> carBrandInfoList) {
        this.context = context;
        this.carBrandInfoList = carBrandInfoList;
        //绘制分割线
        normalDividerPaint = new Paint(Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
        normalDividerPaint.setColor(Color.argb(255, 200, 200, 200));

        //绘制标签文字
        indexTextPaint = new TextPaint(Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
        indexTextPaint.setColor(Color.argb(255, 255, 0, 0));
//        indexTextPaint.setTextSize(context.getResources().getDimension(R.dimen.y54));
        indexTextPaint.setTextSize(54);

        //绘制吸顶的标签，可以使用绘制分割线的paint
        topIndexPaint = new Paint(Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
        topIndexPaint.setColor(Color.argb(255, 200, 200, 200));

        //定义分割线的高度
//        normalDividerHeight = (int) context.getResources().getDimension(R.dimen.y2);
        normalDividerHeight = 2;
        //定义标签的高度
//        indexHeight = (int) context.getResources().getDimension(R.dimen.y66);
        indexHeight = 66;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        CarBrandInfo carBrandInfo = carBrandInfoList.get(position);
        String brand_index = carBrandInfo.getBrand_index();
        /*首先初始化一个存储标签“A”、“B”、……的列表brandIndexList，每拿到一个CarBrandInfo，便取出其brand_index同列表比对，
        如果列表中有，则说明该CarBrandInfo不是改组第一个，如果列表中没有则说明该CarBrandInfo为该组第一个，
        从而将该brand_index加入列表，表示该标签的第一个已标记，接下来的都不是第一个。同时为该CarBrandInfo
        打一个标记isFirstInGroup = true表示这个brand是该组第一个，以便接下来留出标签空隙绘制头部标签*/
        //给每组第一个view打标记
        if (!brandIndexList.contains(brand_index)) {
            brandIndexList.add(brand_index);
            carBrandInfo.isFirstInGroup = true;
        }
        //如果该view为改组第一条数据，则留出位置绘制标签
        if (carBrandInfo.isFirstInGroup) {
            outRect.top = indexHeight;
        } else {
            outRect.bottom = normalDividerHeight;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            CarBrandInfo carBrandInfo = carBrandInfoList.get(position);
            String brand_index = carBrandInfo.getBrand_index();
            int top = view.getTop();
            /*分割线绘制在该条目位置的底部，所以该条目的底部bottom位置为分割线的顶部top，向下偏移normalDividerHeight
            的高度作为分割线的高度，以下两个值记录分割线的顶部和底部*/
            int lineTop = view.getBottom();
            int bottom = view.getBottom() + normalDividerHeight;
            //绘制分割线
            c.drawRect(left, lineTop, right, bottom, normalDividerPaint);

            //如果该carBrandInfo为该组第一个，则绘制头部标签
            if (carBrandInfo.isFirstInGroup) {
                c.drawRect(left, top - indexHeight, right, top, normalDividerPaint);
                //将文字绘制到标签中间垂直区域
                c.drawText(brand_index, left, top - indexHeight / 2 + Math.abs(indexTextPaint.ascent() + indexTextPaint.descent()) / 2, indexTextPaint);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        View topView = parent.getChildAt(0);
        int topViewBottom = topView.getBottom();
        int positionInList = parent.getChildAdapterPosition(topView);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        CarBrandInfo carBrandInfo = carBrandInfoList.get(positionInList);
        if (positionInList != carBrandInfoList.size() - 1) {
            //拿到下一条数据比对
            CarBrandInfo nextCarBrandInfo = carBrandInfoList.get(positionInList + 1);
            //如果下一条数据为该其所在组的第一条，当其移动到顶部相接触的时候，需要偏移绘制吸顶的标签，达到往上或者往下挤出的效果
            if (nextCarBrandInfo.isFirstInGroup) {
                //只有在两个标签接触到的时候才开始偏移，否则还是不偏移
                if (topViewBottom <= indexHeight) {
                    c.drawRect(left, 0, right, topViewBottom, topIndexPaint);
                    //将文字绘制到标签中间垂直区域
                    c.drawText(carBrandInfo.getBrand_index(), left, topViewBottom - indexHeight / 2 + Math.abs(indexTextPaint.ascent() + indexTextPaint.descent()) / 2, indexTextPaint);
                    //如果偏移绘制了，不再执行下面的正常绘制操作
                    return;
                }
            }
        }
        //没有执行上面的偏移绘制，表示顶部标签未和下一条标签相接触，正常绘制出现的标签即可
        c.drawRect(left, 0, right, indexHeight, topIndexPaint);
        //绘制标签上的文字
        c.drawText(carBrandInfo.getBrand_index(), left, indexHeight - indexHeight / 2 + Math.abs(indexTextPaint.ascent() + indexTextPaint.descent()) / 2, indexTextPaint);

    }
}

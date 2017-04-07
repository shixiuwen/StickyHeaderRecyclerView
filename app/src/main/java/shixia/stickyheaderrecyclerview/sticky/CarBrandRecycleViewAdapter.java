package shixia.stickyheaderrecyclerview.sticky;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import shixia.stickyheaderrecyclerview.R;
import shixia.stickyheaderrecyclerview.bean.CarBrandInfo;

/**
 * Created by ShiXiuwen on 2017/3/10.
 * <p>
 * Description:汽车品牌对应 adapter
 */

public class CarBrandRecycleViewAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HOT = 0;      //推荐车型
    private static final int TYPE_NORMAL = 1;   //普通车型

    private Context context;
    private List<CarBrandInfo> brandList;

    public CarBrandRecycleViewAdapter(Context context, List<CarBrandInfo> brandList) {
        this.context = context;
        this.brandList = brandList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HOT) {
            return new HotBrandViewHolder(LayoutInflater.from(context).inflate(R.layout.item_car_brand_hot, parent, false));
        } else {
            return new NormalBrandViewHolder(LayoutInflater.from(context).inflate(R.layout.item_car_brand_normal, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_HOT) {
            ((HotBrandViewHolder) holder).ivBrandHot.setBackgroundColor(Color.argb(255, 255, 0, 0));
        } else {
            ((NormalBrandViewHolder) holder).tvBrandNormal.setText(brandList.get(position).getBrand_name());
        }
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HOT;
        } else {
            return TYPE_NORMAL;
        }
    }

    private class HotBrandViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBrandHot;

        HotBrandViewHolder(View itemView) {
            super(itemView);
            ivBrandHot = (ImageView) itemView.findViewById(R.id.iv_car_brand_hot);
        }
    }

    private class NormalBrandViewHolder extends RecyclerView.ViewHolder {

        TextView tvBrandNormal;

        NormalBrandViewHolder(View itemView) {
            super(itemView);
            tvBrandNormal = (TextView) itemView.findViewById(R.id.tv_car_brand_normal);
        }
    }
}

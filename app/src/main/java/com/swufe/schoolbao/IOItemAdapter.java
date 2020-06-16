package com.swufe.schoolbao;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.percentlayout.widget.PercentRelativeLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;


public class IOItemAdapter extends RecyclerView.Adapter<IOItemAdapter.ViewHolder> {
    private static final String TAG = "IOItemAdapter";
    private final int TYPE_COST = -1;
    private final int TYPE_EARN =  1;

    private List<IOItem> mIOItemList;

    public DecimalFormat decimalFormat = new DecimalFormat("0.00");

    static class ViewHolder extends RecyclerView.ViewHolder {
        PercentRelativeLayout earnLayout, costLayout;
        RelativeLayout dateBar;

        TextView itemNameEarn, itemNameCost;
        TextView itemMoneyEarn, itemMoneyCost;
        TextView itemDate;
        ImageView itemImageEarn, itemImageCost;

        public ViewHolder(View view) {
            super(view);
            earnLayout =  view.findViewById(R.id.earn_left_layout);
            costLayout =  view.findViewById(R.id.cost_right_layout);
            dateBar    =  view.findViewById(R.id.date_bar);

            itemNameEarn  =  view.findViewById(R.id.earn_item_name_main);
            itemNameCost  =  view.findViewById(R.id.cost_item_name_main);
            itemMoneyEarn =  view.findViewById(R.id.earn_item_money_main);
            itemMoneyCost =  view.findViewById(R.id.cost_item_money_main);
            itemDate      =  view.findViewById(R.id.iotem_date);
            itemImageEarn =  view.findViewById(R.id.earn_item_img_main);
            itemImageCost =  view.findViewById(R.id.cost_item_img_main);
        }
    }

    public IOItemAdapter(List<IOItem> ioItemList) {
        mIOItemList = ioItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.io_item, parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IOItem ioItem = mIOItemList.get(position);
        //        showItemDate(holder, ioItem.getTimeStamp());
        //        // 表示支出的布局
        if (ioItem.getType() == TYPE_COST) {       // -1代表支出
            holder.earnLayout.setVisibility(View.GONE);
            holder.costLayout.setVisibility(View.VISIBLE);
            holder.itemImageCost.setImageResource(ioItem.getSrcId());
            holder.itemNameCost.setText(ioItem.getName());
            holder.itemMoneyCost.setText(decimalFormat.format(ioItem.getMoney()));
        //表示收入的布局
        } else if (ioItem.getType() == TYPE_EARN) {   //1代表收入
            holder.earnLayout.setVisibility(View.VISIBLE);
            holder.costLayout.setVisibility(View.GONE);
            holder.itemImageEarn.setImageResource(ioItem.getSrcId());
            holder.itemNameEarn.setText(ioItem.getName());
            holder.itemMoneyEarn.setText(decimalFormat.format(ioItem.getMoney()));
        }

    }

    @Override
    public int getItemCount() {
        return mIOItemList.size();
    }
}
package com.swufe.schoolbao;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class CostFragment extends Fragment {

    private String[] titles = {"一般", "用餐", "零食", "交通", "充值", "购物", "娱乐", "住房", "饮料", "网购",
            "鞋帽", "护肤", "化妆", "电影", "转账", "浪费", "健身", "医疗", "旅游", "教育", "香烟", "酒水", "数码", "捐献",
            "家庭", "宠物", "服装", "日用", "水果", "母婴", "信用卡", "理财", "工作", "家具", "通信"};
    private ViewPager mPager;
    private List<View> mPagerList;
    private List<IOItem> mDatas;
    private TextView itemTitle;
    private ImageView itemImage;
    private ExtensiblePageIndicator extensiblePageIndicator;
    protected Context mContext;//声明一个上下文对象

    // 总的页数
    private int pageCount;

    // 每一页显示的个数
    private int pageSize = 18;

    // 当前显示的是第几页
    private int curIndex = 0;

    private static final String TAG = "CostFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: start");
        mContext = getActivity();

        View view = inflater.inflate(R.layout.cost_fragment, container, false);

        mPager =  view.findViewById(R.id.viewpager_1);
        extensiblePageIndicator =  view.findViewById(R.id.ll_dot_1);

        itemImage =  getActivity().findViewById(R.id.chosen_image);
        itemTitle =  getActivity().findViewById(R.id.chosen_title);

        // 初始化数据源
        initDatas();
        Log.i(TAG, "onCreateView: 是否初始化");

        // 初始化上方banner
        changeBanner(mDatas.get(0));

        // 总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.item_recycler_grid, mPager ,false);
            MyGridLayoutManager layoutManager = new MyGridLayoutManager(getContext(), 6);
            recyclerView.setLayoutManager(layoutManager);
            GridRecyclerAdapter adaper = new GridRecyclerAdapter(mDatas, i, pageSize);
            recyclerView.setAdapter(adaper);

            mPagerList.add(recyclerView);

            adaper.setOnItemClickListener(new GridRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    changeBanner(mDatas.get(position));
                }
            });
        }
        // 设置适配器
        mPager.setAdapter(new ViewPagerAdapter(mPagerList));
        extensiblePageIndicator.initViewPager(mPager);

        return view;
    }

    //初始化数据源
    private void initDatas() {
        mDatas = new ArrayList<IOItem>();
        for (int i = 1; i <= titles.length; i++) {
            mDatas.add(new IOItem("type_big_" + i, titles[i-1]));
        }
        Log.i(TAG, "initDatas: 初始化");
    }

    // 改变banner状态
    public void changeBanner(IOItem tmpItem) {
        itemImage.setImageResource(tmpItem.getSrcId());
        itemTitle.setText(tmpItem.getName());
        itemImage.setTag(-1);                        // 保留图片资源属性，-1表示支出
        itemTitle.setTag(tmpItem.getSrcName());      // 保留图片资源名称作为标签，方便以后调用
    }
}
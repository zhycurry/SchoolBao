package com.swufe.schoolbao;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView inItemRecyclerView;
    private RecyclerView outItemRecyclerView;
    private IOItemAdapter adapter;
    private List<IOItem> ioItemList = new ArrayList<>();
    private TextView monthlyCost, monthlyEarn;

    private Sum sum = new Sum();

    public static String PACKAGE_NAME;
    public static Resources resources;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        // litepal启动
        Connector.getDatabase();

        // 获得包名和资源，方便后面的程序使用
        PACKAGE_NAME = getApplicationContext().getPackageName();
        resources = getResources();

        inItemRecyclerView =  findViewById(R.id.in_items);
        outItemRecyclerView =  findViewById(R.id.out_items);
        monthlyCost =  findViewById(R.id.monthly_cost_money);
        monthlyEarn =  findViewById(R.id.monthly_earn_money);

        initIoItemList(this);

        sum.setMoneyText(sum.MONTHLY_COST, monthlyCost);
        sum.setMoneyText(sum.MONTHLY_EARN, monthlyEarn);

    }



    public void onClick(View btn){
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        startActivity(intent);
    }

    public void setRecyclerView(Context context) {
        // 用于存储recyclerView的日期
        GlobalVariables.setmDate("");

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setStackFromEnd(true);    // 列表从底部开始展示，反转后从上方开始展示
        layoutManager.setReverseLayout(true);   // 列表反转

        for(int i=0;i<ioItemList.size();i++){
            if(ioItemList.get(i).getType()==1){
                inItemRecyclerView.setLayoutManager(layoutManager);
                adapter = new IOItemAdapter(ioItemList);
                inItemRecyclerView.setAdapter(adapter);
            }else if(ioItemList.get(i).getType()==-1){
                outItemRecyclerView.setLayoutManager(layoutManager);
                adapter = new IOItemAdapter(ioItemList);
                outItemRecyclerView.setAdapter(adapter);
            }
        }


        Log.i(TAG, "setRecyclerView: ");
    }

    public void initIoItemList(final Context context) {
        DataSupport.findAllAsync(IOItem.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                ioItemList = (List<IOItem>) t;
                setRecyclerView(context);
            }
        });
        Log.i(TAG, "initIoItemList: ");
    }

}

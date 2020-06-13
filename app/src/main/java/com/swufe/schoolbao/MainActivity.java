package com.swufe.schoolbao;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.litepal.tablemanager.Connector;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import at.markushi.ui.CircleButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView ioItemRecyclerView;
    private CircleButton addBtn;
    private ImageView headerImg;
    private TextView monthlyCost, monthlyEarn;

    private Sum sum = new Sum();

    public static String PACKAGE_NAME;
    public static Resources resources;
    public static final int SELECT_GALLERY_PIC = 1;
    public DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private static final String TAG = "MainActivity";
    private SimpleDateFormat formatItem = new SimpleDateFormat("yyyy年MM月dd日");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme(R.style.AppTheme);

        // litepal启动
        Connector.getDatabase();

        // 获得包名和资源，方便后面的程序使用
        PACKAGE_NAME = getApplicationContext().getPackageName();
        resources = getResources();

        ioItemRecyclerView =  findViewById(R.id.in_and_out_items);
        monthlyCost =  findViewById(R.id.monthly_cost_money);
        monthlyEarn =  findViewById(R.id.monthly_earn_money);

    }



    public void onClick(View btn){
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        startActivity(intent);
    }
}

package com.swufe.schoolbao;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private Button addCostBtn;
    private Button addEarnBtn;
    private Button clearBtn;
    private Button addFinishBtn;

    private ImageView bannerImage;
    private TextView bannerText;

    private TextView moneyText;

    private SimpleDateFormat formatItem = new SimpleDateFormat("yyyy年MM月dd日");
    private SimpleDateFormat formatSum  = new SimpleDateFormat("yyyy年MM月");
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addCostBtn =  findViewById(R.id.add_cost_button);
        addEarnBtn =  findViewById(R.id.add_earn_button);
        addFinishBtn   =  findViewById(R.id.add_finish);
        clearBtn =  findViewById(R.id.clear);

        bannerText =  findViewById(R.id.chosen_title);
        bannerImage =  findViewById(R.id.chosen_image);

        // 设置按钮监听
        addCostBtn.setOnClickListener(new ButtonListener());
        addEarnBtn.setOnClickListener(new ButtonListener());
        addFinishBtn.setOnClickListener(new ButtonListener());
        clearBtn.setOnClickListener(new ButtonListener());

        moneyText =  findViewById(R.id.input_money_text);
        // 及时清零
        moneyText.setText("0.00");
        addCostBtn.setTextColor(0xffff8c00); // 设置“支出“按钮为灰色
        addEarnBtn.setTextColor(0xff908070); // 设置“收入”按钮为橙色

        manager = getSupportFragmentManager();//获取Manager，在活动中可以直接通过调用getFragmentManager()方法得到
        transaction = manager.beginTransaction();//开启一个事务，通过调用beginTransaction()方法开启
        transaction.replace(R.id.item_fragment, new CostFragment());//向容器内添加或替换碎片，一般使用replace()方法实现，需要传入容器的id和待添加的碎片实例
        Log.i(TAG, "onCreate: 是否替换");
        transaction.addToBackStack(null);// 使用addToBackStack()方法，将事务添加到返回栈中，填入的是用于描述返回栈的一个名字
        transaction.commit();

    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            transaction = manager.beginTransaction();

            switch (view.getId()) {
                case R.id.add_cost_button:
                    addCostBtn.setTextColor(0xffff8c00); // 设置“支出“按钮为灰色
                    addEarnBtn.setTextColor(0xff908070); // 设置“收入”按钮为橙色
                    transaction.replace(R.id.item_fragment, new CostFragment());
                    Log.i(TAG, "onClick: add_cost_button");
                    break;
                case R.id.add_earn_button:
                addEarnBtn.setTextColor(0xffff8c00); // 设置“收入“按钮为灰色
                    addCostBtn.setTextColor(0xff908070); // 设置“支出”按钮为橙色
                    transaction.replace(R.id.item_fragment, new EarnFragment());
                    Log.i(TAG, "onClick: add_earn_button");
                    break;
                case R.id.add_finish:
                    String moneyString =  moneyText.getText().toString();
                    if (moneyString.equals("0.00") || GlobalVariables.getmInputMoney().equals(""))
                        Toast.makeText(getApplicationContext(),"你还没输入金额", Toast.LENGTH_SHORT).show();
                    else {
                        putItemInData(Double.parseDouble(moneyText.getText().toString()));
                        calculatorClear();
                        finish();
                    }
                    break;
                case R.id.clear:
                    calculatorClear();
                    moneyText.setText("0.00");
                    break;
            }
            transaction.commit();
        }
    }

    // 清零按钮
    public void calculatorClear() {
        GlobalVariables.setmInputMoney("");
    }

    public void putItemInData(double money) {
        Sum sum = new Sum();
        IOItem ioItem = new IOItem();
        String tagName = (String) bannerText.getTag();
        int tagType = (int) bannerImage.getTag();

        if (tagType < 0) {
            ioItem.setType(ioItem.TYPE_COST);
        } else ioItem.setType(ioItem.TYPE_EARN);

        ioItem.setName(bannerText.getText().toString());
        ioItem.setSrcName(tagName);
        ioItem.setMoney(money);
        ioItem.setTimeStamp(formatItem.format(new Date()));         // 存储记账时间
        ioItem.save();
        
        int type = ioItem.getType();
        String sumDate = formatSum.format(new Date());
        // 计算总额
        sum.calculateMoneyIncludeNull(sum.SUM, "All", money, type, sumDate);
        calculateMonthlyMoney(type, ioItem);

        Log.i(TAG, "putItemInData: start");
    }

    public void calculateMonthlyMoney(int type, IOItem ioItem) {
        Sum sum = new Sum();
        String sumDate = formatSum.format(new Date());
        int id = (int)((double)type / 2 + 2.5);

        if (sum.isThereASum(id)) {
            sum = DataSupport.find(Sum.class, id);
            if (sum.getDate().equals(ioItem.getTimeStamp().substring(0, 8))) {
                sum.calculateMoney(id, ioItem.getMoney(), type*type);
            } else {
                sum.saveSum(sum, id, ioItem.getMoney(), type*type, sumDate);
            }
        } else {
            sum.saveSum(sum, id, ioItem.getMoney(), type*type, sumDate);
        }
    }

    // 数字输入按钮
    public void calculatorNumOnclick(View btn) {
        Button view = (Button) btn;
        String input = view.getText().toString();
        String money = GlobalVariables.getmInputMoney();
        Log.i(TAG, "calculatorNumOnclick: money"+money);
        if (GlobalVariables.getmInputMoney().length()>2) {
            String dot = money.substring(money.length() - 3, money.length() - 2);
            Log.i(TAG, "calculatorNumOnclick: " + dot);
            if (dot.equals(".")) {
                Toast.makeText(getApplicationContext(), "你已经不能继续输入了", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        GlobalVariables.setmInputMoney(money+input);
        moneyText.setText(decimalFormat.format(Double.valueOf(GlobalVariables.getmInputMoney())));
    }
}
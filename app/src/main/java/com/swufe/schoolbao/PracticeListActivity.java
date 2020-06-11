package com.swufe.schoolbao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PracticeListActivity extends AppCompatActivity implements Runnable{

    private final String TAG = "practice_list";
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_list);

        Thread t = new Thread(this);
        t.start();//开启线程
    }

    @Override
    public void run() {
        Bundle bundle = new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://jobzpgl.swufe.edu.cn/home/index/employList/mcit/MDAwMDAwMDAwMJG6n3_DYqS7j6Ouv69jZ5WJutTaynbddricp9CWi5qikaeWacSdqLqGfaK2w4iil5C4zNbGiL-E.html").get();
            Log.i(TAG, "run: " + (doc.title()));
            Elements as = doc.getElementsByTag("a");
            Elements spans = doc.getElementsByTag("span");

            /*int j = 0;
            for(Element span:spans){
                Log.i(TAG, "run: span["+j+"]="+span);
                j++;
            }*/

            for(int i=5;i<=24;i++){
                Element a = as.get(i);
                Log.i(TAG, "run: "+a.text());
        }
            for(int j=0;j<=19;j++){
                Element span = spans.get(j);
                Log.i(TAG, "run: "+span.text());
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

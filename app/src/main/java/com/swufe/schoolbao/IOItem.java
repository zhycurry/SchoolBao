package com.swufe.schoolbao;

import android.content.res.Resources;

import org.litepal.crud.DataSupport;

public class IOItem extends DataSupport {
    public final int TYPE_COST = -1;
    public final int TYPE_EARN =  1;

    private int id;
    private int type;                       // 类型：收入、支出
    private double money;
    private String name;
    private String timeStamp;
    private String srcName;                 // 项目资源名称

    public IOItem() {}

    public IOItem(String srcName, String name) {
        this.srcName = srcName;
        this.name = name;
    }


    public double getMoney()                       { return money; }
    public int getType()                           { return type; }
    public String getName()                        { return name; }
    public String getTimeStamp()                   { return timeStamp; }
    public String getSrcName()                     { return srcName; }
    public int getId()                             { return id; }

    // 设定属性
    public void setMoney(double money)             { this.money = money; }
    public void setType(int type)                  { this.type = type; }
    public void setName(String name)               { this.name = name; }
    public void setTimeStamp(String timeStamp)     { this.timeStamp = timeStamp; }
    public void setSrcName(String srcName)         { this.srcName = srcName; }

    // 返回图片资源的id
    public int getSrcId() {
        Resources resources = MainActivity.resources;
        return resources.getIdentifier(srcName, "drawable", MainActivity.PACKAGE_NAME);
    }
}

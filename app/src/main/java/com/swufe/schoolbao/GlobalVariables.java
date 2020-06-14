package com.swufe.schoolbao;

public class GlobalVariables {   //全局变量
    private static String mDate = "";
    private static boolean mHasDot = false;//小数点
    private static String mInputMoney = "";

    public static void setmDate(String date)      { mDate = date;     }
    public static void setmInputMoney(String a)   { mInputMoney = a;  }

    public static String getmDate()        { return mDate;        }
    public static String getmInputMoney()  { return mInputMoney;  }
}

package com.ebookfrenzy.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedData {

    static final String Id="id", Name="name", Email="email";

    public static SharedPreferences getSharedPreferences(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setId(Context ctx, String id){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Id, id);
        editor.commit();
    }

    public static void setName(Context ctx, String name){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Name, name);
        editor.commit();
    }

    public static void setEmail(Context ctx, String email){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Email, email);
        editor.commit();
    }

    public static String getId(Context context) {
        return getSharedPreferences(context).getString(Id,"");
    }

    public static String getName(Context context) {
        return getSharedPreferences(context).getString(Name,"");
    }

    public static String getEmail(Context context) {
        return getSharedPreferences(context).getString(Email,"");
    }

    public static void clearSharedData(Context context)
    {
        Editor editor = getSharedPreferences(context).edit();
        editor.remove(Id);
        editor.remove(Name);
        editor.remove(Email);
        editor.commit();
    }
}

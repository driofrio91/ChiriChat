package com.ChiriChat.Language;/**
 * Created by neosistec on 03/06/2014.
 */

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * @author Danny Riofrio Jimenez
 */
public class Language {

    private static final String LANGUAGE = "language";
    private static Locale myLocale;

    public static void setLocal(String lang) {
        myLocale = new Locale(lang);
        Resources res = Resources.getSystem();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }
}

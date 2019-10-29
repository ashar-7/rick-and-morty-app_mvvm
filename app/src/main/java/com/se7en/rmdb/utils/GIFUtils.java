package com.se7en.rmdb.utils;

import java.util.Random;

public abstract class GIFUtils {
    private static String[] error_array = {
            "file:///android_asset/error/jerry_cry.gif",
            "file:///android_asset/error/rick_blurp.gif",
            "file:///android_asset/error/rick_cup_blink.gif",
            "file:///android_asset/error/rick_morty_summer.gif",
            "file:///android_asset/error/rick_up.gif",
    };

    private static String[] loading_array = {
            "file:///android_asset/loading/morty_head_loading.gif",
            "file:///android_asset/loading/rick_head_loading.gif",
            "file:///android_asset/loading/rick_eye_load.gif",
            "file:///android_asset/loading/portal.gif",
            "file:///android_asset/loading/rick_morty_spin.gif",
    };

    private static String[] extras_array = {
            "file:///android_asset/extras/rick_dance.gif",
            "file:///android_asset/extras/rick_twerk.gif",
            "file:///android_asset/extras/rick_hanging.gif",
            "file:///android_asset/extras/rick_middle_finger.gif"
    };

    public static String getRandomErrorGIF() {
        Random rand = new Random();
        int random = rand.nextInt(error_array.length);

        return error_array[random];
    }

    public static String getRandomLoadingGIF() {
        Random rand = new Random();
        int random = rand.nextInt(loading_array.length);

        return loading_array[random];
    }

    public static String getRandomExtrasGIF() {
        Random rand = new Random();
        int random = rand.nextInt(extras_array.length);

        return extras_array[random];
    }
}

package com.glriverside.xgqin.ggtimer;

public class TimeUtils {
    public static String intervalToString(Long tick) {
        String timeString;
        if (tick < 0) {
            tick = 0L;
        }

        Long seconds = tick / 1000;
        Long millsec = tick % 1000/10;
        Long mins = seconds / 60;
        timeString = String.format("%02d:%02d:%02d", mins, seconds%60, millsec);
        return timeString;
    }
}

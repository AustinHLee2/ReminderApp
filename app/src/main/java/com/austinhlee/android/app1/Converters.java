package com.austinhlee.android.app1;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Austin Lee on 2/23/2018.
 */

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

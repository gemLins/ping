package org.cncert.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
    private static final SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Long string2long(String date){
        try {
            if(StringUtils.isNotBlank(date)){
                return  sdf.parse(date).getTime() / 1000;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() / 1000;
    }

}

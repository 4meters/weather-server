package com.weather.server.domain.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ISODate {
    public static String toString(Date date){
        SimpleDateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String isoDate = sdfISO.format(date);
        return isoDate;
    }

}

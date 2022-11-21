package com.shariful.kn.task.utils;

public class Constant {
    public static final String BASE_EXCHANGE_RATE_URL = "https://api.exchangerate.host/";
    public static final String TIME_SERIES_RATE_URL =  "timeseries";
    public static final String LATEST_RATE_URL = "latest";
    public static final String BASE_CURRENCY_PARAM = "base";
    public static final String OUTPUT_CURRENCY_PARAM = "symbols";
    public static final String START_DATE_PARAM = "start_date";
    public static final String END_DATE_PARAM = "end_date";
    public static final String BITCOIN = "BTC";
    public static final int TIME_WINDOW = 89;


    private Constant(){
        throw new IllegalStateException("Utility class");
    }
}

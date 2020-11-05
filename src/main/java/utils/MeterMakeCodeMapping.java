package utils;

import java.util.Hashtable;

public class MeterMakeCodeMapping {
    public static Hashtable<String, String> meterMakeCodeMapping;

    public MeterMakeCodeMapping() {
    }

    public static void initMeterMakeCodeMap() {
        if (meterMakeCodeMapping == null) {
            meterMakeCodeMapping = new Hashtable();
            meterMakeCodeMapping.put("1", "SECURE");
            meterMakeCodeMapping.put("4", "GENUS");
            meterMakeCodeMapping.put("6", "HPL");
            meterMakeCodeMapping.put("8", "L&T");
        }

    }
}
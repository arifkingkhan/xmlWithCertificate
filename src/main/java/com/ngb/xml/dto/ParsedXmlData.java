package com.ngb.xml.dto;

import java.io.File;

public class ParsedXmlData {
    private final String meterNumber;
    private final String meterMakeCode;
    private final String readingDate;
    private final String b3Value;
    private final String b5Value;
    private final String b9Value;
    private File xmlFile;

    public File getXmlFile() {
        return this.xmlFile;
    }

    public void setXmlFile(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    public String getMeterNumber() {
        return this.meterNumber;
    }

    public String getMeterMakeCode() {
        return this.meterMakeCode;
    }

    public String getReadingDate() {
        return this.readingDate;
    }

    public String getB3Value() {
        return this.b3Value;
    }

    public String getB5Value() {
        return this.b5Value;
    }

    public String getB9Value() {
        return this.b9Value;
    }

    public ParsedXmlData(String meterNumber, String meterMakeCode, String readingDate, String b3Value, String b5Value, String b9Value) {
        this.meterNumber = meterNumber;
        this.meterMakeCode = meterMakeCode;
        this.readingDate = readingDate;
        this.b3Value = b3Value;
        this.b5Value = b5Value;
        this.b9Value = b9Value;
    }

    public String toString() {
        return "ParsedXmlData{meterNumber=" + this.meterNumber + ", meterMakeCode=" + this.meterMakeCode + ", readingDate=" + this.readingDate + ", b3Value=" + this.b3Value + ", b5Value=" + this.b5Value + ", b9Value=" + this.b9Value + '}';
    }
}

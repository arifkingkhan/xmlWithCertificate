package com.ngb.xml.dto;

import com.google.gson.annotations.Expose;
import java.math.BigDecimal;

public class Read {
    @Expose
    private String consumerNo;
    @Expose
    private String meterIdentifier;
    @Expose
    private String readingDate;
    @Expose
    private String readingType;
    @Expose
    private String meterStatus;
    @Expose
    private String replacementFlag;
    @Expose
    private String source;
    @Expose
    private BigDecimal reading;
    @Expose
    private BigDecimal difference;
    @Expose
    private BigDecimal mf;
    @Expose
    private BigDecimal consumption;
    @Expose
    private BigDecimal assessment;
    @Expose
    private BigDecimal propogatedAssessment;
    @Expose
    private BigDecimal totalConsumption;
    @Expose
    private boolean usedOnBill;
    @Expose
    private ReadMasterKW readMasterKW;
    @Expose
    private ReadMasterPF readMasterPF;

    public Read(String consumerNo, String meterIdentifier, String readingDate, String readingType, String meterStatus, String replacementFlag, String source, BigDecimal reading, BigDecimal difference, BigDecimal mf, BigDecimal consumption, BigDecimal assessment, BigDecimal propogatedAssessment, BigDecimal totalConsumption, boolean usedOnBill, ReadMasterKW readMasterKW, ReadMasterPF readMasterPF) {
        this.consumerNo = consumerNo;
        this.meterIdentifier = meterIdentifier;
        this.readingDate = readingDate;
        this.readingType = readingType;
        this.meterStatus = meterStatus;
        this.replacementFlag = replacementFlag;
        this.source = source;
        this.reading = reading;
        this.difference = difference;
        this.mf = mf;
        this.consumption = consumption;
        this.assessment = assessment;
        this.propogatedAssessment = propogatedAssessment;
        this.totalConsumption = totalConsumption;
        this.usedOnBill = usedOnBill;
        this.readMasterKW = readMasterKW;
        this.readMasterPF = readMasterPF;
    }

    public String getConsumerNo() {
        return this.consumerNo;
    }

    public String getMeterIdentifier() {
        return this.meterIdentifier;
    }

    public String getReadingDate() {
        return this.readingDate;
    }

    public String getReadingType() {
        return this.readingType;
    }

    public String getMeterStatus() {
        return this.meterStatus;
    }

    public String getReplacementFlag() {
        return this.replacementFlag;
    }

    public String getSource() {
        return this.source;
    }

    public BigDecimal getReading() {
        return this.reading;
    }

    public BigDecimal getDifference() {
        return this.difference;
    }

    public BigDecimal getMf() {
        return this.mf;
    }

    public BigDecimal getConsumption() {
        return this.consumption;
    }

    public BigDecimal getAssessment() {
        return this.assessment;
    }

    public BigDecimal getPropogatedAssessment() {
        return this.propogatedAssessment;
    }

    public BigDecimal getTotalConsumption() {
        return this.totalConsumption;
    }

    public boolean isUsedOnBill() {
        return this.usedOnBill;
    }

    public ReadMasterKW getReadMasterKW() {
        return this.readMasterKW;
    }

    public ReadMasterPF getReadMasterPF() {
        return this.readMasterPF;
    }
}
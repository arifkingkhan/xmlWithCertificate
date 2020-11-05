package com.ngb.xml.dto;

import com.google.gson.annotations.Expose;
import java.math.BigDecimal;

public class ReadMasterKW {
    @Expose
    private BigDecimal meterMD;
    @Expose
    private BigDecimal multipliedMD;
    @Expose
    private BigDecimal billingDemand;

    public ReadMasterKW(BigDecimal meterMD, BigDecimal multipliedMD, BigDecimal billingDemand) {
        this.meterMD = meterMD;
        this.multipliedMD = multipliedMD;
        this.billingDemand = billingDemand;
    }

    public BigDecimal getMeterMD() {
        return this.meterMD;
    }

    public BigDecimal getMultipliedMD() {
        return this.multipliedMD;
    }

    public BigDecimal getBillingDemand() {
        return this.billingDemand;
    }
}

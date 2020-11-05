package com.ngb.xml.dto;

import com.google.gson.annotations.Expose;
import java.math.BigDecimal;

public class ReadMasterPF {
    @Expose
    private BigDecimal meterPF;
    @Expose
    private BigDecimal billingPF;

    public ReadMasterPF(BigDecimal meterPF, BigDecimal billingPF) {
        this.meterPF = meterPF;
        this.billingPF = billingPF;
    }

    public BigDecimal getMeterPF() {
        return this.meterPF;
    }

    public BigDecimal getBillingPF() {
        return this.billingPF;
    }
}

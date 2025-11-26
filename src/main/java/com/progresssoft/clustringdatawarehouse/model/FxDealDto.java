package com.progresssoft.clustringdatawarehouse.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class FxDealDto {

    @NotBlank
    private String dealUniqueId;

    @NotBlank
    private String fromCurrencyIso;

    @NotBlank
    private String toCurrencyIso;

    @NotBlank
    private String dealDate;

    @NotNull
    @Positive
    private BigDecimal dealAmount;

    public FxDealDto() {}

    public FxDealDto(String dealUniqueId, String fromCurrencyIso, String toCurrencyIso,
                     String dealDate, BigDecimal dealAmount) {
        this.dealUniqueId = dealUniqueId;
        this.fromCurrencyIso = fromCurrencyIso;
        this.toCurrencyIso = toCurrencyIso;
        this.dealDate = dealDate;
        this.dealAmount = dealAmount;
    }

    public String getDealUniqueId() { return dealUniqueId; }
    public void setDealUniqueId(String dealUniqueId) { this.dealUniqueId = dealUniqueId; }

    public String getFromCurrencyIso() { return fromCurrencyIso; }
    public void setFromCurrencyIso(String fromCurrencyIso) { this.fromCurrencyIso = fromCurrencyIso; }

    public String getToCurrencyIso() { return toCurrencyIso; }
    public void setToCurrencyIso(String toCurrencyIso) { this.toCurrencyIso = toCurrencyIso; }

    public String getDealDate() { return dealDate; }
    public void setDealDate(String dealDate) { this.dealDate = dealDate; }

    public BigDecimal getDealAmount() { return dealAmount; }
    public void setDealAmount(BigDecimal dealAmount) { this.dealAmount = dealAmount; }
}

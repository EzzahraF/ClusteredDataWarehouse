package com.progresssoft.clustringdatawarehouse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "fx_deals")
public class FxDeal {

    @Id
    @Column(name = "deal_id", unique = true)
    private String dealId;
    @Column(name = "deal_unique_id", unique = true)
    private String dealUniqueId; // <-- ajouté
    @NotBlank
    @Column(name = "from_currency_iso")
    private String fromCurrencyIso;

    @NotBlank
    @Column(name = "to_currency_iso")
    private String toCurrencyIso;

    @NotNull
    @Positive
    @Column(name = "deal_amount")
    private BigDecimal dealAmount;

    @NotNull
    @Column(name = "deal_date")
    private LocalDate dealDate;

    public FxDeal() {}

    public FxDeal(FxDealDto dto) {
        this.dealId = dto.getDealUniqueId();
        this.dealUniqueId = dto.getDealUniqueId();
        this.fromCurrencyIso = dto.getFromCurrencyIso();
        this.toCurrencyIso = dto.getToCurrencyIso();
        this.dealAmount = dto.getDealAmount();
        this.dealDate = LocalDate.parse(dto.getDealDate(), DateTimeFormatter.ISO_DATE);
    }

    public String getDealId() { return dealId; }
    public void setDealId(String dealId) { this.dealId = dealId; }

    public String getDealUniqueId() { return dealUniqueId; }
    public void setDealUniqueId(String dealUniqueId) { this.dealUniqueId = dealUniqueId; }

    public String getFromCurrencyIso() { return fromCurrencyIso; }
    public void setFromCurrencyIso(String fromCurrencyIso) { this.fromCurrencyIso = fromCurrencyIso; }

    public String getToCurrencyIso() { return toCurrencyIso; }
    public void setToCurrencyIso(String toCurrencyIso) { this.toCurrencyIso = toCurrencyIso; }

    public BigDecimal getDealAmount() { return dealAmount; }
    public void setDealAmount(BigDecimal dealAmount) { this.dealAmount = dealAmount; }

    public LocalDate getDealDate() { return dealDate; }
    public void setDealDate(LocalDate dealDate) { this.dealDate = dealDate; }


}

package me.bill.currency.dto;

import me.bill.currency.domain.Currency;

public class CurrencyResponseDto {

    private String code;
    private Integer num;
    private Integer d;
    private String currency;

    public CurrencyResponseDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public static CurrencyResponseDto fromEntity(Currency currency) {
        CurrencyResponseDto dto = new CurrencyResponseDto();

        dto.setCode(currency.getCode());
        dto.setCurrency(currency.getCurrency());
        dto.setD(currency.getD());
        dto.setNum(currency.getNum());

        return dto;
    }

}

package me.bill.currency.domain;

import javax.persistence.*;

@Entity
@Table(name = "currency", indexes = {
        @Index(columnList = "code", name = "code_unique_index", unique = true)
})
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private Integer num;
    private Integer d;
    private String currency;

    public Currency() {
    }

    public Long getId() {
        return id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

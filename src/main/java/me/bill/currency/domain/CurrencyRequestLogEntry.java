package me.bill.currency.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "currency_requests_log")
public class CurrencyRequestLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String ip;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;


    public CurrencyRequestLogEntry() {
    }

    public CurrencyRequestLogEntry(String code, String ip, Date datetime) {
        this.code = code;
        this.ip = ip;
        this.datetime = datetime;
    }

    public String getCode() {
        return code;
    }

    public String getIp() {
        return ip;
    }

    public Date getDatetime() {
        return datetime;
    }
}

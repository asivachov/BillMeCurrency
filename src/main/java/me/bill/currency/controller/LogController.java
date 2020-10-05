package me.bill.currency.controller;

import me.bill.currency.domain.CurrencyRequestLogEntry;
import me.bill.currency.repository.CurrencyRequestsLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LogController {

    private final CurrencyRequestsLogRepository currencyRequestsLogRepository;

    @Autowired
    public LogController(CurrencyRequestsLogRepository currencyRequestsLogRepository) {
        this.currencyRequestsLogRepository = currencyRequestsLogRepository;
    }

    @GetMapping("log")
    public String log(Model model) {
        List<CurrencyRequestLogEntry> logEntries = currencyRequestsLogRepository.findAll(Sort.by(Sort.Direction.DESC, "datetime"));

        model.addAttribute("logEntries", logEntries);

        return "log";
    }

}

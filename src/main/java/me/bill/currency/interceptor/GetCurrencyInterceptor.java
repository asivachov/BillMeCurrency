package me.bill.currency.interceptor;

import me.bill.currency.domain.CurrencyRequestLogEntry;
import me.bill.currency.repository.CurrencyRequestsLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class GetCurrencyInterceptor extends HandlerInterceptorAdapter {

    private final CurrencyRequestsLogRepository currencyRequestsLogRepository;

    @Autowired
    public GetCurrencyInterceptor(CurrencyRequestsLogRepository currencyRequestsLogRepository) {
        this.currencyRequestsLogRepository = currencyRequestsLogRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        CurrencyRequestLogEntry logEntry = extractLogEntry(request);
        currencyRequestsLogRepository.save(logEntry);

        return super.preHandle(request, response, handler);
    }

    public CurrencyRequestLogEntry extractLogEntry(HttpServletRequest request) {
        Date now = new Date();
        String code = request.getParameter("code");
        String ip = request.getRemoteAddr();

        return new CurrencyRequestLogEntry(code, ip, now);
    }
}

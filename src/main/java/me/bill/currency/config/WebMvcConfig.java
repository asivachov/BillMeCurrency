package me.bill.currency.config;

import me.bill.currency.interceptor.GetCurrencyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final GetCurrencyInterceptor getCurrencyInterceptor;

    @Autowired
    public WebMvcConfig(GetCurrencyInterceptor getCurrencyInterceptor) {
        this.getCurrencyInterceptor = getCurrencyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getCurrencyInterceptor).addPathPatterns("/api/getCurrency");
    }

}

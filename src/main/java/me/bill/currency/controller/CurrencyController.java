package me.bill.currency.controller;

import me.bill.currency.domain.Currency;
import me.bill.currency.dto.CurrencyResponseDto;
import me.bill.currency.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("api")
public class CurrencyController {

    private static final Logger LOG = LoggerFactory.getLogger(CurrencyController.class);
    private static final String INCORRECT_CURRENCY_CODE_MESSAGE = "Code's length has to be 3 symbols and contain only letters";
    private static final String UNABLE_TO_FIND_CURRENCY_MESSAGE = "Currency with this code doesn't exist";
    private static final String INTERNAL_ERROR_MESSAGE = "Unknown internal error";
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping(value = "/getCurrency", params = "code", method = RequestMethod.GET)
    public CurrencyResponseDto getCurrency(@RequestParam("code") @Pattern(regexp = "[A-Za-z]{3}") String code) {
        Optional<Currency> currencyOptional = currencyService.findByCode(code.toUpperCase());

        Currency currency = currencyOptional.orElseThrow(UnableToFindCurrency::new);

        return CurrencyResponseDto.fromEntity(currency);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleIncorrectCurrencyCode() {
        return INCORRECT_CURRENCY_CODE_MESSAGE;
    }

    @ExceptionHandler(UnableToFindCurrency.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleUnableToFindCurrency() {
        return UNABLE_TO_FIND_CURRENCY_MESSAGE;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleOtherExceptions(Exception e) {
        LOG.error("Unexpected error occurred", e);
        return INTERNAL_ERROR_MESSAGE;
    }

}

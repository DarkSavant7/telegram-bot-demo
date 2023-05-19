package org.dark.savant.telegrambotdemo.web;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.dark.savant.telegrambotdemo.client.CurrencyClient;
import org.dark.savant.telegrambotdemo.model.CurrencyConvert;
import org.dark.savant.telegrambotdemo.model.CurrencyRate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/currency")
public class CurrencyController {

  CurrencyClient currencyClient;

  @GetMapping("/rate")
  public CurrencyRate getCurrencyRates(@RequestParam(name = "base") String base) {
    log.debug("Requested rates for {}", base);
    return currencyClient.getCurrencyRates(base);
  }

  @GetMapping("/convert")
  public CurrencyConvert convert(String base, String to, BigDecimal amount) {
    log.debug("Requested conversion {} {} -> {}",base, amount, to);
    return currencyClient.convert(base, to, amount);
  }
}

package org.dark.savant.telegrambotdemo.client;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.dark.savant.telegrambotdemo.model.CurrencyConvert;
import org.dark.savant.telegrambotdemo.model.CurrencyRate;
import org.dark.savant.telegrambotdemo.props.CurrencyProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CurrencyClient {

  RestTemplate restTemplate;
  CurrencyProperties currencyProperties;

  public CurrencyRate getCurrencyRates(String base) {
    var headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    var httpEntity = new HttpEntity<>(headers);
    return restTemplate.exchange(
        currencyProperties.ratesUrl(),
        HttpMethod.GET,
        httpEntity,
        CurrencyRate.class,
        base,
        currencyProperties.key()
    ).getBody();
  }

  public CurrencyConvert convert(String base, String to, BigDecimal amount) {
    var headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    var httpEntity = new HttpEntity<>(headers);
    return restTemplate.exchange(
        currencyProperties.convertUrl(),
        HttpMethod.GET,
        httpEntity,
        CurrencyConvert.class,
        base,
        currencyProperties.key(),
        to,
        amount
    ).getBody();
  }
}

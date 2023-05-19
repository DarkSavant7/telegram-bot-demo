package org.dark.savant.telegrambotdemo.props;

import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot.api.currency")
public record CurrencyProperties(
    String key,
    String convertUrl,
    String ratesUrl,
    Set<String> availableCurrencies
) {

//  private static String prop;
//
//  public void print() {
//    System.out.println(prop);
//  }
}

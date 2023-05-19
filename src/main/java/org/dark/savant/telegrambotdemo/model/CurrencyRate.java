package org.dark.savant.telegrambotdemo.model;

import java.math.BigDecimal;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyRate {

  Long lastUpdate;
  String base;
  Map<String, BigDecimal> rates;
}

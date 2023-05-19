
package org.dark.savant.telegrambotdemo.model;

import java.math.BigDecimal;
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
public class CurrencyConvert {

  Long lastUpdate;
  String base;
  String to;
  BigDecimal amount;
  BigDecimal converted;
  BigDecimal rate;
}

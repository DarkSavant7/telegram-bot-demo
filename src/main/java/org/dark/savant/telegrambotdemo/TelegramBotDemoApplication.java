package org.dark.savant.telegrambotdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TelegramBotDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(TelegramBotDemoApplication.class, args);
  }

}

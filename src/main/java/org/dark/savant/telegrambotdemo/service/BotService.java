package org.dark.savant.telegrambotdemo.service;

import java.math.BigDecimal;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.dark.savant.telegrambotdemo.client.CurrencyClient;
import org.dark.savant.telegrambotdemo.props.TelegramBotProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BotService extends TelegramLongPollingBot {

  private static final String START_COMMAND = "/start";
  private static final String HELP_COMMAND = "/help";
  private static final String RATE_COMMAND = "/rate";
  private static final String CONVERT_COMMAND = "/convert";
  private static final String CONVERSION_RESPONSE_TEMPLATE = """
      Converting %s %s -> %s
      Rate: %s
      Result: %s
      """;
  private static final String START_MESSAGE = """
      Hi there!
      This is test telegram bot.
      You could get currency rates and convert money between currencies by current rate.
      In order to convert just type:
      /convert <CURRENCY FROM> <CURRENCY TO> <AMOUNT>
      In order to get rates for your currency type:
      /rate <CURRENCY>
      Awailable currencies are:
          "EUR"
          "USD"
          "JPY"
          "BGN"
          "CZK"
          "DKK"
          "GBP"
          "HUF"
          "PLN"
          "RON"
          "SEK"
          "CHF"
          "ISK"
          "NOK"
          "TRY"
          "AUD"
          "BRL"
          "CAD"
          "CNY"
          "HKD"
          "IDR"
          "ILS"
          "INR"
          "KRW"
          "MXN"
          "MYR"
          "NZD"
          "PHP"
          "SGD"
          "THB"
          "ZAR"
      """;

  CurrencyClient currencyClient;
  TelegramBotProperties telegramBotProperties;

  public BotService(CurrencyClient currencyClient, TelegramBotProperties telegramBotProperties) {
    super(telegramBotProperties.token());
    this.currencyClient = currencyClient;
    this.telegramBotProperties = telegramBotProperties;
  }

  @Override
  public String getBotUsername() {
    return telegramBotProperties.name();
  }

  @Override
  public String getBotToken() {
    return telegramBotProperties.token();
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      var messageText = update.getMessage().getText();
      var chatId = update.getMessage().getChatId();
      var command = messageText.split(" ");

      switch (command[0]) {
        case START_COMMAND -> startCommand(chatId);
        case HELP_COMMAND -> startCommand(chatId);
        case CONVERT_COMMAND -> convertCommand(chatId, command);
        case RATE_COMMAND -> rateCommand(chatId, command);
        default -> sendMessage(chatId, "Unknown command");
      }
    }

  }

  private void rateCommand(Long chatId, String[] command) {
    try {
      var base = command[1];
      var rateResponse = currencyClient.getCurrencyRates(base.toUpperCase(Locale.ROOT));
      var sb = new StringBuilder(rateResponse.getBase()).append(System.lineSeparator());
      rateResponse.getRates().forEach((key, value) -> sb.append(key)
          .append(" -> ")
          .append(value)
          .append(System.lineSeparator()));
      sendMessage(chatId, sb.toString());
    } catch (Exception e) {
      log.error("Wrong command", e);
      throw new IllegalArgumentException("Wrong command. See help if needed");
    }
  }

  private void convertCommand(Long chatId, String[] command) {
    String result;
    try {
      var from = command[1];
      var to = command[2];
      var amount = new BigDecimal(command[3]);
      var conversionResponse = currencyClient.convert(from.toUpperCase(Locale.ROOT), to.toUpperCase(
          Locale.ROOT), amount);
      log.debug("Converting: {} {} to {}", amount, from, to);
      result = CONVERSION_RESPONSE_TEMPLATE.formatted(
          conversionResponse.getAmount(),
          conversionResponse.getBase(),
          conversionResponse.getTo(),
          conversionResponse.getRate(),
          conversionResponse.getConverted());
      log.debug("Conversion result: {}", result);
    } catch (Exception e) {
      log.error("Wrong command", e);
      result = e.getMessage();
    }
    sendMessage(chatId, result);

  }

  private void startCommand(Long chatId) {
    sendMessage(chatId, START_MESSAGE);
  }

  private void sendMessage(Long chatId, String textToSend) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(String.valueOf(chatId));
    sendMessage.setText(textToSend);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error("Error sending message to telegram", e);
    }
  }
}

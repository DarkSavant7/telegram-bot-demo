package org.dark.savant.telegrambotdemo.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot.telegram")
public record TelegramBotProperties(
    String name,
    String token) {

}

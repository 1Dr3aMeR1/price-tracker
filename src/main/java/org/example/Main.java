package org.example;

import org.example.config.BotConfig;
import org.example.bot.TelegramBot;
import org.example.config.Database;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        //Запуск бота
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TelegramBot bot = new TelegramBot(
                    BotConfig.getUsername(),
                    BotConfig.getToken()
            );
            botsApi.registerBot(bot);
            System.out.println("Bot started");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (var connect = Database.getConnection()) {
            System.out.println("Connected to DataBase");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
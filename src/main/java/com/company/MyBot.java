package com.company;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class MyBot extends TelegramLongPollingBot {

    private ToDoListService todoListService = new ToDoListService();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            String text = message.getText();

            if (text.startsWith("/add")) {
                String task = text.replace("/add", "").trim();
                if (!task.isEmpty()) {
                    todoListService.add(chatId, task);
                    sendMessage(chatId, "Vazifa qo'shildi: " + task);
                } else {
                    sendMessage(chatId, "Iltimos, vazifa kiriting: /add <vazifa>");
                }
            } else if (text.equals("/list")) {
                List<String> tasks = todoListService.get(chatId);
                if (tasks.isEmpty()) {
                    sendMessage(chatId, "Sizda vazifalar ro'yxati bo'sh.");
                } else {
                    StringBuilder taskList = new StringBuilder("Sizning vazifalaringiz:\n");
                    for (int i = 0; i < tasks.size(); i++) {
                        taskList.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
                    }
                    sendMessage(chatId, taskList.toString());
                }
            } else if (text.startsWith("/delete")) {
                try {
                    int taskIndex = Integer.parseInt(text.replace("/delete", "").trim()) - 1;
                    if (todoListService.delete(chatId, taskIndex)) {
                        sendMessage(chatId, "Vazifa muvaffaqiyatli o'chirildi.");
                    } else {
                        sendMessage(chatId, "Noto'g'ri vazifa raqami. Iltimos, qayta urinib ko'ring.");
                    }
                } catch (NumberFormatException e) {
                    sendMessage(chatId, "Iltimos, vazifa raqamini kiriting: /delete <raqam>");
                }
            } else {
                sendMessage(chatId, "Noto'g'ri buyruq. Quyidagi buyruqlardan foydalaning:\n" +
                        "/add <vazifa> - Yangi vazifa qo'shish.\n" +
                        "/list - Vazifalar ro'yxatini ko'rish.\n" +
                        "/delete <raqam> - Vazifani o'chirish.");
            }
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "@shokh_ake47bot";
    }

    @Override
    public String getBotToken() {
        return "7615080251:AAEbfd6yag5jnYhe4nZR5HeUZCx5CvCysJo";
    }
}

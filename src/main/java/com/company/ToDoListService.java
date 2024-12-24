package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToDoListService {
    private Map<Long, List<String>> userTasks = new HashMap<>();

    public void add(Long chatId, String task) {
        userTasks.computeIfAbsent(chatId, k -> new ArrayList<>()).add(task);
    }

    public List<String> get(Long chatId) {
        return userTasks.getOrDefault(chatId, new ArrayList<>());
    }

    public boolean delete(Long chatId, int taskIndex) {
        List<String> tasks = userTasks.get(chatId);
        if (tasks != null && taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.remove(taskIndex);
            return true;
        }
        return false;
    }
}

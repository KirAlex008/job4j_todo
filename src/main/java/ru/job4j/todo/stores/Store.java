package ru.job4j.todo.stores;

import ru.job4j.todo.models.Task;

import java.util.Collection;

public interface Store {

    Collection<Task> findAllTasks();

    Task createTask(Task task);

    boolean update(Integer id);

    //Task findById(Integer id);
}

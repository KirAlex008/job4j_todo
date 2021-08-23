package ru.job4j.todo.stores;

import ru.job4j.todo.models.Task;
import ru.job4j.todo.models.User;

import java.util.Collection;

public interface Store {

    Collection<Task> findAllTasks(User user);

    Task createTask(Task task);

    boolean update(Integer id);

    //Task findById(Integer id);

    User findByEmail(String email);

    User createUser(User user);
}

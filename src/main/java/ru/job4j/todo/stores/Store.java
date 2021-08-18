package ru.job4j.todo.stores;

import org.hibernate.SessionFactory;
import ru.job4j.todo.models.Task;

import java.util.Collection;

public interface Store {

    Collection<Task> findAllTasks();

    Task createTask(Task task);

    boolean update(Task task);

    Task findById(Integer id);
}

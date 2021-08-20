package ru.job4j.todo.stores;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.models.Task;

import java.util.Collection;
import java.util.List;

public class HbmStore implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();


    @Override
    public Collection<Task> findAllTasks() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todo.models.Task").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Task createTask(Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    @Override
    public boolean update(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task task = session.get(Task.class, id);
        System.out.println(task.isDone());
        System.out.println(!task.isDone());
        task.setDone(!task.isDone());
        boolean success = false;
        try {
            session.update(task);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.getTransaction().commit();
        session.close();
        System.out.println(success);
        return success;
    }

    public Task findById(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task result = session.get(Task.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}

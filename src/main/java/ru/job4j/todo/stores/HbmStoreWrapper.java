package ru.job4j.todo.stores;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.models.Task;
import java.util.Collection;
import java.util.function.Function;

public class HbmStoreWrapper implements Store, AutoCloseable {

    private static final HbmStoreWrapper INSTANCE = new HbmStoreWrapper();

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private HbmStoreWrapper() {
    }

    public static HbmStoreWrapper getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final HbmStoreWrapper INSTANCE = new HbmStoreWrapper();
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Collection<Task> findAllTasks() {
        return this.tx(
                session -> session.createQuery("from Task").list()
        );
    }

    @Override
    public Task createTask(Task task) {
        this.tx(session -> session.save(task));
        return task;
    }

    @Override
    public boolean update(Integer id) {
        return this.tx(
                session -> {
                    Task task = session.get(Task.class, id);
                    task.setDone(!task.isDone());
                    boolean success = false;
                    try {
                        session.update(task);
                        System.out.println(task.toString());
                        success = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return success;
                });
    }


    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }


}

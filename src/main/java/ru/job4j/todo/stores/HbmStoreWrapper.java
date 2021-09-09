package ru.job4j.todo.stores;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.models.Category;
import ru.job4j.todo.models.Task;
import ru.job4j.todo.models.User;

import java.util.Collection;
import java.util.List;
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

/*    @Override
    public Collection<Task> findAllTasks(User user) {
        return this.tx(
                session -> session.createQuery("from Task where user.id=:user_id")
                        .setParameter("user_id", user.getId())
                        .list()
        );
    }*/
    @Override
    public Collection<Task> findAllTasks(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery(
                "select distinct t from Task t  "
                        + "join fetch t.categories", Task.class
        ).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /*@Override
    public Task createTask(Task task, String[] ids) {
        return this.tx(session -> {
            for (String id : ids) {
                Category category = session.find(Category.class, Integer.parseInt(id));
                task.addCategory(category);
            }
            session.save(task);
            return task;
        });
    }*/

    @Override
    public Task createTask(Task task, String[] ids) {
        Session session = sf.openSession();
        session.beginTransaction();
        for (String id : ids) {
            Category category = session.find(Category.class, Integer.parseInt(id));
            task.addCategory(category);
        }
        session.save(task);
        session.getTransaction().commit();
        session.close();
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

    /*@Override
    public User findByEmail(String email) {
        return this.tx(
                session -> session.get(User.class, email));
    }*/

    @Override
    public User findByEmail(String value) {
        return (User) this.tx(
                session -> session.createQuery("From User u where u.email=:email")
                        .setParameter("email", value)
                        .uniqueResult()
        );
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
    public User createUser(User user) {
        this.tx(session -> session.save(user));
        return user;
    }

    @Override
    public List<Category> findAllCategories() {
        return this.tx(
                session -> session.createQuery("from Category", Category.class)
                .list()
        );
    }

    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}

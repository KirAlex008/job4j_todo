package ru.job4j.todo.stores;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.models.Task;

import java.sql.Timestamp;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            //Session session = sf.openSession();
            //session.beginTransaction();
            //Task task1 = create(Task.of("Learn Hibernate", new Timestamp(1459510232000L), false), sf);
            Task task2 = create(Task.of("Learn Hibernate", new Timestamp(1459510232000L), false), sf);
            System.out.println(task2);
            task2.setDescription("Learn JQuery");
            update(task2, sf);
            System.out.println(task2);
            Task rsl = findById(task2.getId(), sf);
            System.out.println(rsl);
            //delete(rsl.getId(), sf);
            List<Task> list = findAll(sf);
            for (Task it : list) {
                System.out.println(it);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static Task create(Task item, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public static void update(Task item, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task item = new Task();
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
    }

    public static List<Task> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todo.models.Task").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static Task findById(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task result = session.get(Task.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}

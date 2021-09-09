package ru.job4j.todo.stores;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.models.Category;
import ru.job4j.todo.models.Task;
import ru.job4j.todo.models.User;

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
            User user = findById(1, sf);
            //User user = createUser(User.of("User_1", "a", "s"), sf);
            //System.out.println(user.toString());
            /*Category category1 = createCategory(Category.of("Minsk"), sf);
            Category category2 = createCategory(Category.of("Moscow"), sf);
            Category category3 = createCategory(Category.of("London"), sf);
            Category category4 = createCategory(Category.of("Capetown"), sf);
            Category category5 = createCategory(Category.of("Ottawa"), sf);*/


            String[] categories = new String[2];
            categories[0] = "1";
            categories[1] = "2";
            /*Task task1 = createTask(Task.of("Learn Hibernate", new Timestamp(1459510232000L), false, user), categories, sf);
            Task task2 = createTask(Task.of("Learn SQL", new Timestamp(1459510232000L), false, user), categories, sf);
            System.out.println(task1.toString());
            System.out.println(task2.toString());*/
            //task1.addCategory(categories);
            //task2.addCategory(category);

            //task2.setDescription("Learn JQuery");
            //update(task2, sf);
            //System.out.println(task2);
            //Task rsl = findById(task2.getId(), sf);
            //System.out.println(rsl);
            //delete(rsl.getId(), sf);
            //String name = "categories";
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

/*    public static Task create(Task item, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }*/

    public static User createUser(User user, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public static Category createCategory(Category category, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(category);
        session.getTransaction().commit();
        session.close();
        return category;
    }

    public static Task createTask(Task task, String[] ids, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        for (String id : ids) {
            System.out.println(id + "ID");
            Category category = session.find(Category.class, Integer.parseInt(id));
            task.addCategory(category);
            System.out.println(task.toString());
        }
        //session.persist(task);
        session.save(task);
        session.getTransaction().commit();
        session.close();
        return task;
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
        //List result = session.createQuery("from ru.job4j.todo.models.Task").list();
        List result = session.createQuery(
                "select distinct t from Task t  "
                        + "join fetch t.categories", Task.class
        ).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static User findById(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        User result = session.get(User.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

}

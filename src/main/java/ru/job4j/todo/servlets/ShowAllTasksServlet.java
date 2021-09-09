package ru.job4j.todo.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.models.Task;
import ru.job4j.todo.models.User;
import ru.job4j.todo.stores.HbmStoreWrapper;
import ru.job4j.todo.stores.Store;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class ShowAllTasksServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        Store store = HbmStoreWrapper.getInstance();
        User user = (User) req.getSession(false).getAttribute("user");
        Collection<Task> tasks = store.findAllTasks(user);
        for (var el: tasks) {
            System.out.println(el.toString());
        }
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(tasks);
        System.out.println(json);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

}

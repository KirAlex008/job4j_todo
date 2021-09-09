package ru.job4j.todo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.job4j.todo.models.Category;
import ru.job4j.todo.models.Task;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import ru.job4j.todo.models.User;
import ru.job4j.todo.stores.HbmStoreWrapper;
import ru.job4j.todo.stores.Store;

public class TaskSaveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = HbmStoreWrapper.getInstance().findAllCategories();

        for (var el: categories) {
            System.out.println(el.toString());
        }
        ObjectMapper mapper = new ObjectMapper();
        String string = mapper.writeValueAsString(categories);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        resp.getWriter().write(string);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String description = req.getParameter("name");
        String[] category = req.getParameterValues("cIds");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        Store store = HbmStoreWrapper.getInstance();
        User user = (User) req.getSession(false).getAttribute("user");
        store.createTask(Task.of(description, new Timestamp(System.currentTimeMillis()), false, user), category);
        System.out.println("Sucsses");
        resp.sendRedirect(req.getContextPath() + "/index.do");
    }

}

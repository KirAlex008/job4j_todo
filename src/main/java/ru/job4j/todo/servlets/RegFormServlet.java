package ru.job4j.todo.servlets;

import ru.job4j.todo.models.User;
import ru.job4j.todo.stores.HbmStoreWrapper;
import ru.job4j.todo.stores.Store;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegFormServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Store store = HbmStoreWrapper.getInstance();
        User user = store.findByEmail(email);
        if (user == null) {
            store.createUser(User.of(name, email, password));
            System.out.println("GO");
            req.getRequestDispatcher("/index").forward(req, resp);
        } else {
            System.out.println("NOT GO");
            req.getRequestDispatcher("regForm.html").forward(req, resp);
        }
    }
}

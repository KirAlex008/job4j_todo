package ru.job4j.todo.servlets;

import ru.job4j.todo.models.User;
import ru.job4j.todo.stores.HbmStoreWrapper;
import ru.job4j.todo.stores.Store;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Store store = HbmStoreWrapper.getInstance();
        User user = store.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            System.out.println(user.toString());
            resp.sendRedirect(req.getContextPath() + "/index.do");
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("reg.html").forward(req, resp);
        }
    }
    }

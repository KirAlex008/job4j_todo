package ru.job4j.todo.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.job4j.todo.models.Task;
import ru.job4j.todo.stores.HbmStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import org.json.JSONObject;
//import ru.job4j.todo.stores.HbmStoreWrapper;


public class TaskSaveServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);
        String description = data.get("text").getAsString();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        HbmStore store = new HbmStore();
        //HbmStoreWrapper store = new HbmStoreWrapper();
        store.createTask(Task.of(description, new Timestamp(System.currentTimeMillis()), false));
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.flush();
    }
}

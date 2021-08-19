package ru.job4j.todo.servlets;

import com.google.gson.JsonObject;
import org.json.JSONObject;
import ru.job4j.todo.models.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.stores.HbmStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class TaskUpdateServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);
        String idVal = data.get("idVal").getAsString();
        Integer id = Integer.parseInt(idVal);
        String state = data.get("state").getAsString();
        boolean done = Boolean.parseBoolean(state);
        HbmStore store = new HbmStore();
        Task task = store.findById(id);
        task.setDone(done);
        String response = null;
        response = store.update(id, done) ? "success" : "fail";
        System.out.println(response);
        System.out.println(task.toString());
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(task);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}

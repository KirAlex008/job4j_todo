package ru.job4j.todo.servlets;

import com.google.gson.JsonObject;
import org.json.JSONObject;
import ru.job4j.todo.models.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.stores.HbmStore;
//import ru.job4j.todo.stores.HbmStoreWrapper;

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
        Integer id = Integer.parseInt(data.get("idVal").getAsString());
        HbmStore store = new HbmStore();
        //HbmStoreWrapper store = new HbmStoreWrapper();
        String response = store.update(id) ? "success" : "fail";
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        JSONObject outLineJson = new JSONObject();
        outLineJson.put("text", response);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println(outLineJson);
        writer.flush();
    }
}

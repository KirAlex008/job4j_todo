package ru.job4j.todo.servlets;

import com.google.gson.JsonObject;
import org.json.JSONObject;
import com.google.gson.Gson;
import ru.job4j.todo.stores.HbmStoreWrapper;
import ru.job4j.todo.stores.Store;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TaskUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);
        Integer id = Integer.parseInt(data.get("idVal").getAsString());
        Store store = HbmStoreWrapper.getInstance();
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

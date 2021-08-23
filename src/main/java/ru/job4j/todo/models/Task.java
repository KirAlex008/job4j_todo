package ru.job4j.todo.models;

import java.sql.Timestamp;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private Timestamp created;
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Task of(String description, Timestamp created, boolean done, User user) {
        Task task = new Task();
        task.description = description;
        task.created = created;
        task.done = done;
        task.user = user;
        return task;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return done == task.done
                && id.equals(task.id)
                && description.equals(task.description)
                && created.equals(task.created)
                && user.equals(task.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, done, user);
    }
}

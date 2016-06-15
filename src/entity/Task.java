package entity;

import enums.TaskProgressEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;

/**
 * Created by dona on 07.06.16.
 */
public class Task extends BaseEntity implements Serializable {
    private User user;
    private String title;
    private String body;
    private TaskProgressEnum progress;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Enumerated(EnumType.ORDINAL)
    public TaskProgressEnum getProgress() {
        return progress;
    }

    public void setProgress(TaskProgressEnum progress) {
        this.progress = progress;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task() {
    }

    public Task(User user, String title, String body, TaskProgressEnum prog) {
        this.user = user;
        this.title = title;
        this.body = body;
        this.progress = prog;
    }

}

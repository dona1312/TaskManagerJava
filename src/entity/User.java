package entity;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by dona on 07.06.16.
 */
public class User extends BaseEntity implements Serializable{
    private String username;
    private String password;
    private Boolean isAdmin;
    private Set<Task> tasks;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public User(){}
    public User(String username,String pass,boolean isAdmin){
        this.username=username;
        this.password=pass;
        this.isAdmin=isAdmin;
    }

}

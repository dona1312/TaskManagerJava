package repositories;

import entity.Task;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dona on 07.06.16.
 */
public class UserRepository extends  BaseRepository<User>{

    public UserRepository(){
        super();
        this.tClass=User.class;
    }
    public User getByUsername(String username) {

        return new UserRepository().getAll().stream().filter(u -> u.getUsername().equals(username)).findAny().orElse(null);
    }
}

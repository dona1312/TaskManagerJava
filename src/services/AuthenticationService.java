package services;

import entity.Task;
import entity.User;
import repositories.TaskRepository;
import repositories.UserRepository;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dona on 07.06.16.
 */
public class AuthenticationService {
    public static User loggedUser;

    public static void authenticate(String username,String pass){
        UserRepository userRepo=new UserRepository();
        loggedUser=userRepo.getAll().stream().filter(u->u.getUsername().equals(username) && u.getPassword().equals(pass)).findFirst().orElse(null);

    }
}

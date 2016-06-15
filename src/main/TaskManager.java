package main;
import  entity.*;

/**
 * Created by dona on 07.06.16.
 */

import enums.TaskProgressEnum;
import org.hibernate.bytecode.buildtime.spi.Logger;
import org.hibernate.id.AbstractUUIDGenerator;
import repositories.*;
import services.AuthenticationService;
import sun.net.www.protocol.http.AuthenticationHeader;

import java.util.*;
import java.util.logging.Level;


public class TaskManager {

    public static void main(String[] args) {
        System.out.println("Welcome.");
        System.out.println("For login press 1.");
        System.out.println("For registration press 2.");

        Scanner sc = new Scanner(System.in);
        int number = 0;
        try {
            number = sc.nextInt();
        } catch (Exception e) {

        }

        sc.nextLine();

        switch (number) {
            case 1:
                System.out.println("Log in");
                login();
                break;
            case 2:
                System.out.println("Register");
                register();
                break;
            default:
                System.out.println("Try again.");
                break;
        }
    }

    public static void register() {
        System.out.println("Enter your username.");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();
        System.out.println("Enter your password.");
        String pass = sc.nextLine();

        User user = new User(username,pass,false);

        UserRepository repo=new UserRepository();
        repo.save(user);
        login();
    }

    public static void login() {
        System.out.println("Enter your username.");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();
        System.out.println("Enter your password.");
        String pass = sc.nextLine();

        AuthenticationService.authenticate(username, pass);

        for (int i = 0; i < 16; i++) {
            System.out.println("");
        }
        System.out.println("Hello," + AuthenticationService.loggedUser.getUsername());

        if (AuthenticationService.loggedUser.getIsAdmin()) {
            loadAdminPannel();
        } else {
            loadUserPannel();
        }
    }

    public static void loadAdminPannel() {
        System.out.println("2-See your tasks");
        System.out.println("3-See all users");
        System.out.println("4-Add user");
        System.out.println("5-Edit user");
        System.out.println("6-Delete user");
        System.out.println("7-Exit");

        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();

        switch (number) {
            case 2:
                getTasks();
                loadAdminPannel();
                break;
            case 3:
                getAllUsers();
                loadAdminPannel();
                break;
            case 4:
                addUser();
                break;
            case 5:
                editUser();
                break;
            case 6:
                deleteUser();
                break;
            case 7:
                System.exit(0);
            default:
                loadAdminPannel();
                break;
        }
    }

    private static void getTasks() {
        TaskRepository repo = new TaskRepository();
      ArrayList<Task> tasks = repo.getTasksByUserID(AuthenticationService.loggedUser.getID());
        if (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(
                        tasks.get(i).getID() + "." + tasks.get(i).getTitle() + " -> " + tasks.get(i).getBody()+"- "+tasks.get(i).getProgress());
            }
            System.out.println("--------------");
        } else {
            System.out.println("No tasks motherfucker.");
        }

    }

    private static void getAllUsers() {
        UserRepository repo = new UserRepository();
        List<User> users = repo.getAll();
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).getUsername());
        }

        System.out.println("Press 1 to see the main menu");
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        switch (number) {
            case 1:
                if (AuthenticationService.loggedUser.getIsAdmin()) {
                    loadAdminPannel();
                } else {
                    loadUserPannel();
                }
                break;

            default:
                ;
                break;
        }
    }

    public static void loadUserPannel() {
        System.out.println("1-See your tasks");
        System.out.println("2-Add task");
        System.out.println("3-Delete task");
        System.out.println("4-Update task");
        System.out.println("5-Exit");

        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();

        switch (number) {
            case 1:
                getTasks();
                loadUserPannel();
                break;
            case 2:
                addTask();
                break;
            case 3:
                deleteTask();
                break;
            case 4:
                updateTask();
                break;
            case 5:
               System.exit(0);
                break;
            default:
                loadUserPannel();
                break;
        }
    }

    private static void updateTask() {
        getTasks();
        System.out.println("Choose task number to update.");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        sc.nextLine();
        TaskRepository repo = new TaskRepository();
        Task t = repo.getByID(num);

        if (t != null) {
            System.out.println("Data for " + t.getTitle() + ". Press number from 1 to 3 to change data.");
            System.out.println("1-Body:" + t.getBody());
            System.out.println("2-Title:" + t.getTitle());
            System.out.println("3-Progress:" + t.getProgress());

            int number = sc.nextInt();
            sc.nextLine();

            System.out.println("Enter new value: ");
            switch (number) {
                case 1:
                    String uNew = sc.nextLine();
                    t.setBody(uNew);
                    repo.save(t);
                    loadUserPannel();
                    break;
                case 2:
                    String pNew = sc.nextLine();
                    t.setTitle(pNew);
                    repo.save(t);
                    loadAdminPannel();

                    break;
                case 3:
                    System.out.println("Choose between Unfinished,Finished or In progress ");
                    String aNew = sc.nextLine();
                    if (aNew.toLowerCase().contains("in progress") || aNew.contains("loading") || aNew.toLowerCase().contains("inprogress")) {
                        t.setProgress(TaskProgressEnum.InProgress);
                    } else if (aNew.toLowerCase().contains("not finished") || aNew.toLowerCase().contains("unfinished") ) {
                        t.setProgress(TaskProgressEnum.Unfinished);
                    } else if (aNew.toLowerCase().contains("finished") || aNew.toLowerCase().contains("done")) {
                        t.setProgress(TaskProgressEnum.Finished);
                    } else {
                        t.setProgress(TaskProgressEnum.Unfinished);
                    }

                    repo.save(t);
                    loadAdminPannel();
                    break;
                default:
                    editUser();
                    break;
            }
        }
    }

    private static void addTask() {
        Scanner sc = new Scanner(System.in);
        Task t = new Task();
        System.out.println("Enter title of task:");
        String title = sc.nextLine();
        t.setTitle(title);

        System.out.println("Enter body of task:");
        String body = sc.nextLine();
        t.setBody(body);

        System.out.println("Enter status of task:");
        String status = sc.nextLine();

        if (status.toLowerCase().contains("in progress") || status.contains("loading") || status.toLowerCase().contains("inprogress")) {
            t.setProgress(TaskProgressEnum.InProgress);
        } else if (status.toLowerCase().contains("not finished") || status.toLowerCase().contains("unfinished") ) {
            t.setProgress(TaskProgressEnum.Unfinished);
        } else if (status.toLowerCase().contains("finished") || status.toLowerCase().contains("done")) {
            t.setProgress(TaskProgressEnum.Finished);
        } else {
            t.setProgress(TaskProgressEnum.Unfinished);
        }

        t.setUser(AuthenticationService.loggedUser);

        TaskRepository repo = new TaskRepository();
        repo.save(t);

        loadUserPannel();
    }

    private static void deleteTask() {
        getTasks();
        System.out.println("Choose task number to delete.");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        sc.nextLine();
        TaskRepository repo = new TaskRepository();
        repo.delete(repo.getByID(num));
        loadUserPannel();
    }

    private static void deleteUser() {
        System.out.println("Insert name of user,you want to delete.");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();

        UserRepository repo = new UserRepository();
        if (repo.getByUsername(username) != null) {
            repo.delete(repo.getByUsername(username));
            System.out.println("User deleted.Òî return press 1. For exit-2.");
            int number = sc.nextInt();
            switch (number) {
                case 1:
                    loadAdminPannel();
                    break;
                case 2:
                    System.exit(0);
                default:
                    System.exit(0);
                    break;
            }
        }

    }

    private static void editUser() {
        System.out.println("Insert name of user,you want to edit.");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();

        UserRepository repo = new UserRepository();
        User user = repo.getByUsername(username);

        if (user != null) {
            System.out.println("Data for " + user.getUsername() + ". Press number from 1 to 3 to change data.");
            System.out.println("1-Username: " + user.getUsername());
            System.out.println("2-Password: " + user.getPassword());
            System.out.println("3-Is admin: " + user.getIsAdmin());
            int num = sc.nextInt();
            sc.nextLine();

            System.out.println("Enter new value: ");
            switch (num) {
                case 1:
                    String uNew = sc.nextLine();
                    user.setUsername(uNew);
                    repo.save(user);
                    loadAdminPannel();
                    break;
                case 2:
                    String pNew = sc.nextLine();
                    user.setPassword(pNew);
                    repo.save(user);
                    loadAdminPannel();

                    break;
                case 3:
                    String aNew = sc.nextLine();
                    user.setIsAdmin(Boolean.parseBoolean(aNew));
                    repo.save(user);
                    loadAdminPannel();
                    break;
                default:
                    editUser();
                    break;
            }

        }
    }

    private static void addUser() {
        System.out.println("Insert data for new user.");
        Scanner sc = new Scanner(System.in);

        UserRepository repo = new UserRepository();
        User user = new User();

        System.out.println("1-Username: ");
        user.setUsername(sc.nextLine());
        System.out.println("2-Password: ");
        user.setPassword(sc.nextLine());
        System.out.println("3-Is admin: ");
        user.setIsAdmin(Boolean.parseBoolean(sc.nextLine()));

        repo.save(user);
        loadAdminPannel();
    }
}
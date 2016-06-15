package repositories;

import entity.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dona on 07.06.16.
 */
public class TaskRepository extends  BaseRepository<Task>{

    public TaskRepository(){
        super();
        this.tClass = Task.class;
    }


    public  ArrayList<Task> getTasksByUserID(int userID){
        List<Task> tasks=this.getAll();
        ArrayList<Task> selected=new ArrayList<>();
        for (int i=0;i<tasks.size();i++){
            if (tasks.get(i).getUser().getID()==userID){
                selected.add(tasks.get(i));
            }
        }
        return  selected;
    }

}

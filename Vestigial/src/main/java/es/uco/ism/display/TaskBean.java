package es.uco.ism.display;

import java.io.Serializable;

import es.uco.ism.business.task.TaskDTO;

public class TaskBean implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private String id = "";
    private TaskDTO task = null;


    public String getID(){
        return id;
    }
    
    public void setID (String id){
        this.id = id;
    }

    public TaskDTO getTask(){
        return task;
    }

    public void setTask (TaskDTO task){
        this.task = task;
    }


}

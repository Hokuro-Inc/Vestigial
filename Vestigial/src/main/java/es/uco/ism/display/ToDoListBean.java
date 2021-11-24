package es.uco.ism.display;

import java.io.Serializable;
import java.util.ArrayList;

import es.uco.ism.business.task.TaskDTO;

public class ToDoListBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private ArrayList <TaskDTO> listatareas = null;
	private ArrayList <String> labelListTask = null;
    public ArrayList <TaskDTO> getListTask(){
        return listatareas;
    }
    
    public void setListTask (ArrayList <TaskDTO> listatareas){
        this.listatareas = listatareas;
    }

    public TaskDTO get(int index){
        return this.listatareas.get(index);
    }

    public void set (int index, TaskDTO task){
        this.listatareas.set(index, task);
    }
    
	public void setToDoList(ArrayList <String> listTask) {
		this.labelListTask = listTask;
	}

	public ArrayList <String> getToDoList() {
		return labelListTask;
	}
}

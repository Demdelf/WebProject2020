package model;

import java.util.List;

public class Goal {
    private String text;
    private List<Task> tasks;
    private List<Goal> childGoals;
    private int parentGoalId;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Goal> getChildGoals() {
        return childGoals;
    }

    public void setChildGoals(List<Goal> childGoals) {
        this.childGoals = childGoals;
    }

    public int getParentGoal() {
        return parentGoalId;
    }

    public void setParentGoal(int parentGoalId) {
        this.parentGoalId = parentGoalId;
    }
}

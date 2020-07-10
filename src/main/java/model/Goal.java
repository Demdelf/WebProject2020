package model;

public class Goal {
    private String text;
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

    public int getParentGoal() {
        return parentGoalId;
    }

    public void setParentGoal(int parentGoalId) {
        this.parentGoalId = parentGoalId;
    }
}

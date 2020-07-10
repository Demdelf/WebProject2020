package model;

import java.time.LocalDate;

public class Task {
    private String text;
    private String description;
    private LocalDate timeToBeCompleted;
    private Status status;
    private int parentGoalId;
    private int id;

    public Task(String text, String description) {
        this.text = text;
        this.description = description;
    }

    public Task() {
    }
    public int getParentGoalId() {
        return parentGoalId;
    }

    public void setParentGoalId(int parentGoalId) {
        this.parentGoalId = parentGoalId;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTimeToBeCompleted() {
        return timeToBeCompleted;
    }

    public void setTimeToBeCompleted(LocalDate timeToBeCompleted) {
        this.timeToBeCompleted = timeToBeCompleted;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "text='" + text + '\'' +
                ", description='" + description + '\'' +
                ", timeToBeCompleted=" + timeToBeCompleted +
                ", status=" + status;
    }
}

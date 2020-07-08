package model;

import java.util.List;

public class User {
    private int userId;
    private String name;
    private String password;
    private String email;
    private List<Goal> goals;
    private List<Task> tasks;
    private List<User> friends;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addFriend(User user){
        friends.add(user);
    }

    public void transferTask(User user, Task task){
        if (this.tasks.contains(task)){
            user.addTask(task);
            this.deleteTask(task);
        }
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void deleteTask(Task task){
        if(!tasks.remove(task)) System.out.println(this.name + " don't have this task. So it's impossible to delete it.");
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", targets=" + goals +
                ", tasks=" + tasks +
                ", friends=" + friends +
                '}';
    }
}

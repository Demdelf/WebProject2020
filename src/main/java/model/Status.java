package model;

public enum Status {
    NEW("new"), IN_PROGRESS("in progress"), COMPLETED("complited");
    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

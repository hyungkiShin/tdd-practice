package com.example.javatestdriven;

public class Study {

    private StudyStatus status; // = StudyStatus.DRAFT;

    private int limit;

    private String name;

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit은 0보다 커야 한다.");
        }
        this.limit = limit;
    }

    public Study(final int limit, final String name) {
        this.limit = limit;
        this.name = name;
    }

    public StudyStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }
}

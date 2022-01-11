package com.epam.sax_dom.domain;

import java.util.Objects;

public class Task {
    private long id;
    private String name;
    private int duration;

    public Task() {
    }

    public Task(long id, String name, int duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getId() == task.getId() &&
                getDuration() == task.getDuration() &&
                Objects.equals(getName(), task.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDuration());
    }

}

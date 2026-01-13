package com.roaster.db;

import jakarta.persistence.*;

@Entity
public class RoastCount {
    @Id
    private String username;
    private int count;

    public RoastCount() {}

    public RoastCount(String username) {
        this.username = username;
        this.count = 0;
    }

    public void inc() { count++; }

    public String getUsername() { return username; }
    public int getCount() { return count; }
}

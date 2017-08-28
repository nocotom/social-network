package com.hsbc.sn.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long postId;

    @NotNull
    @Column(nullable = false, length = 140)
    @Size(min = 1, max = 140)
    private String message;

    @ManyToOne
    private User user;

    public long getPostId() {
        return postId;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

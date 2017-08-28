package com.hsbc.sn.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
public class User {

    @Id
    @Column(nullable = false)
    private long id;

    @OneToMany
    private Collection<Post> posts = new LinkedList<>();

    @OneToMany
    private Collection<User> friends = new LinkedList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Collection<Post> getPosts() {
        return posts;
    }

    public Collection<User> getFriends() {
        return friends;
    }
}

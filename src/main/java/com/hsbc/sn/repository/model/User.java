package com.hsbc.sn.repository.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
@Data
public class User {

    @Id
    @Column(nullable = false)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Post> posts = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<User> friends = new LinkedList<>();
}

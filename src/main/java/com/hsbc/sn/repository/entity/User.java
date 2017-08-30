package com.hsbc.sn.repository.entity;

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

    @OneToMany(mappedBy = "owner")
    private Collection<Post> posts = new LinkedList<>();
}

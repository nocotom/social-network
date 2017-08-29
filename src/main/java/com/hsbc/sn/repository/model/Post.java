package com.hsbc.sn.repository.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @NotNull
    @Column(nullable = false, length = 140)
    @Size(min = 1, max = 140)
    private String message;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}

package com.hsbc.sn.repository.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false, length = 140)
    @Size(min = 1, max = 140)
    private String message;

    @Column(nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn
    private User owner;

    @PrePersist
    void createdAt() {
        createdAt = new Date();
    }
}

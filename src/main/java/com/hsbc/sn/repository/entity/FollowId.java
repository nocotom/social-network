package com.hsbc.sn.repository.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@Data
public class FollowId implements Serializable{

    @OneToOne
    private User follower;

    @OneToOne
    private User following;
}

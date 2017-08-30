package com.hsbc.sn.repository.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
public class Follow implements Serializable {

    @EmbeddedId
    private FollowId id;

}

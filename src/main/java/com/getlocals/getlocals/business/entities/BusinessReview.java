package com.getlocals.getlocals.business.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessReview implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Float rating;

    private String comment;

    private String email;

    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Business business;
}

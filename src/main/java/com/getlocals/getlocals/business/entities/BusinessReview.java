package com.getlocals.getlocals.business.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.sql.Date;


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

    private Long phone;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = false)
    private Business business;

    @CreatedDate
    private Date createdAt;

    @OneToOne
    private BusinessImage image;
}

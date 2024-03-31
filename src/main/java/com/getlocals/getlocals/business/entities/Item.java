package com.getlocals.getlocals.business.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private float price;
    private String currency;
    private String ingredients;
    private String description;

    @OneToOne(orphanRemoval = true)
    private BusinessImage image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private ItemCategory category;

}

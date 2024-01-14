package com.getlocals.getlocals.business.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getlocals.getlocals.business.Business;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_id")
    @JsonIgnore
    private Business business;

}

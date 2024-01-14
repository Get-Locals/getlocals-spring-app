package com.getlocals.getlocals.business.item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

}

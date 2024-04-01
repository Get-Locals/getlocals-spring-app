package com.getlocals.getlocals.business.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.generator.Generator;

import java.io.Serializable;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String val;

    @Column(unique = true)
    private String label;
}

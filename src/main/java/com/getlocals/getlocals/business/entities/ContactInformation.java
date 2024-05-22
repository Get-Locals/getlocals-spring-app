package com.getlocals.getlocals.business.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String email;
    private String phone1;
    private String phone2;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Business business;
}

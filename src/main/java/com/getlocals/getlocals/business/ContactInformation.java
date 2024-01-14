package com.getlocals.getlocals.business;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}

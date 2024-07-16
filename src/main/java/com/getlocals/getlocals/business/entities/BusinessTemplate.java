package com.getlocals.getlocals.business.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BusinessTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String templateId;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Business business;

}

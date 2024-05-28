package com.getlocals.getlocals.business.entities;

import com.getlocals.getlocals.utils.CustomEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Blob;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String extension;

    @Enumerated(EnumType.STRING)
    private CustomEnums.BusinessImageTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Business business;

    @Lob
    @Column(length = 1048576)
    private Blob imageData;
}

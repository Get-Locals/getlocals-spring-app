package com.getlocals.getlocals.business.entities;

import com.getlocals.getlocals.utils.CustomEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;
    private String description;
    private String email;
    private Long phoneNo;

    @Enumerated(EnumType.STRING)
    private CustomEnums.BusinessEmployeeTypeEnum position;

    @OneToOne(cascade = CascadeType.ALL)
    private BusinessImage businessImage;
}

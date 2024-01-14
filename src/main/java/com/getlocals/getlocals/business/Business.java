package com.getlocals.getlocals.business;


import com.getlocals.getlocals.business.item.Item;
import com.getlocals.getlocals.user.User;
import com.getlocals.getlocals.utils.CustomEnums;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Business implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String location;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User owner;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User manager;

    @Enumerated(value = EnumType.STRING)
    private CustomEnums.BusinessServicesEnum serviceType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Item> items;

    private String logo;

}

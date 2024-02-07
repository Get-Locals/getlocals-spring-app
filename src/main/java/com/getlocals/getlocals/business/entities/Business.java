package com.getlocals.getlocals.business.entities;


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

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private Collection<Item> items;

    private String logo;

    private String uberUrl;
    private String skipUrl;
    private String doorDashUrl;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BusinessTimings businessTimings;

    public String getOwnerName() {
        return this.owner.getName();
    }

    public String getManagerName() {
        if (this.manager != null)
            return this.manager.getName();
        else
            return "No Manager Assigned";
    }

}

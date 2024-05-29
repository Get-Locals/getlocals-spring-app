package com.getlocals.getlocals.business.entities;


import com.getlocals.getlocals.user.User;
import com.getlocals.getlocals.utils.CustomEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private User owner;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private User manager;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private BusinessType serviceType;

    @Column(columnDefinition = "TEXT")
    private String aboutUs;

    private String uberUrl;
    private String skipUrl;
    private String doorDashUrl;


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

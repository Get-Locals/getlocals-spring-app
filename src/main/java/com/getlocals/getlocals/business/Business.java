package com.getlocals.getlocals.business;


import com.getlocals.getlocals.user.User;
import jakarta.persistence.*;
import lombok.Data;`
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String location;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User owner;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User manager;
    

}

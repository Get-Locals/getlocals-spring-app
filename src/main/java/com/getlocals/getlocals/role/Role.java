package com.getlocals.getlocals.role;

import com.getlocals.getlocals.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}

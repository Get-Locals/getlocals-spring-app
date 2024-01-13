package com.getlocals.getlocals.user;

import com.getlocals.getlocals.role.Role;
import com.getlocals.getlocals.utils.DTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String email;
    private String password;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

    private Boolean isActive;
    private Boolean isAdmin;

    public Collection<String> getRolesString() {
        var res = new ArrayList<String>();

        for (Role role : roles) {
            res.add(role.getRole());
        }

        return res;
    }

    public DTO.UserDTO getUserDTO() {
        return DTO.UserDTO.builder()
                .isActive(isActive)
                .email(email)
                .name(name)
                .roles(getRolesString())
                .build();
    }
}

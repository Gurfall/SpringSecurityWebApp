package org.example.securitywebapp.Model;

import jakarta.persistence.*;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;


@Entity

@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column
    private ERole name;

    public Role() {
    }


    public Role(ERole name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRole(ERole role) {
        this.name = role;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }

    public String getRole() {
        return name.toString();
    }
}

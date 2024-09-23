package org.rossijr.bebsystem.models;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_role")
public class Role implements Serializable, GrantedAuthority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserRole> userRoles = new HashSet<>();

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Calendar createdAt;

    @Override
    public String getAuthority() {
        return name;
    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
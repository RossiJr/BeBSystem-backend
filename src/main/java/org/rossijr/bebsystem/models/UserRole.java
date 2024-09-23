package org.rossijr.bebsystem.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "tb_mm_user_role")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "assigned_at", columnDefinition = "TIMESTAMP")
    private Calendar assignedAt;

    public UserRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Calendar getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Calendar assignedAt) {
        this.assignedAt = assignedAt;
    }
}
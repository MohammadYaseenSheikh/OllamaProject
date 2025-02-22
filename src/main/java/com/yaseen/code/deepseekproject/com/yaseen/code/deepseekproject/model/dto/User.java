package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;  // 👈 Add email field to match your form

    @Column(nullable = false)
    private String password;

    private String role = "USER";

    @PrePersist
    @PreUpdate
    public void normalizeUsername() {
        this.email = this.email.toLowerCase();
        this.username = this.username.toLowerCase();
    }

}

package com.c2.sisteminformasitugas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "custom_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "npm", updatable = false, nullable = false, length = 10)
    private String npm;

    @Column(name = "email", unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "matkul",
            joinColumns = @JoinColumn(name = "npm"),
            inverseJoinColumns = @JoinColumn(name = "kodeMatkul")
    )
    private List<Matkul> matkulList;
}
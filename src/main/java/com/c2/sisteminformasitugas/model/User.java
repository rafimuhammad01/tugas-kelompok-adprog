package com.c2.sisteminformasitugas.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "custom_user")
@Data
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "matkul_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "kodeMatkul")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Matkul> matkulList;

    public User(String npm, String email, String password) {
        this.npm = npm;
        this.email = email;
        this.password = password;
    }
}

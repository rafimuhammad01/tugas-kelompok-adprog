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
    @JoinTable(name = "matkul_id")
    private List<Matkul> matkulList;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<ToDoList> toDoLists;

    public User(String npm, String email, String password, boolean isAdmin) {
        this.npm = npm;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}

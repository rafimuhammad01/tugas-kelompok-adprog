package com.c2.sisteminformasitugas.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "matkul")
@Data
@NoArgsConstructor
public class Matkul {
    @Id
    @Column(name = "kodeMatkul", updatable = false,  nullable = false)
    private String kodeMatkul;

    @Column(name = "nama")
    private String nama;

    //Relationships One to Many (Tugas)
    @OneToMany(mappedBy = "matkul", cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Tugas> tugas;

    //Relationships One to Many (Subscriber)
    @ManyToMany(mappedBy = "matkulList", cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "npm")
    @JsonIdentityReference(alwaysAsId = true)
    private List<User> subscribers;
}
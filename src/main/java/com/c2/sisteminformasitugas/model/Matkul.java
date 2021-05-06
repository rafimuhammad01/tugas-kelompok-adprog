package com.c2.sisteminformasitugas.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "matkul")
@Data
@NoArgsConstructor
public class Matkul {
    @Id
    @Column(name = "kodeMatkul", updatable = false)
    private String kodeMatkul;

    @Column(name = "nama")
    private String nama;

    //Relationships One to Many (Tugas)
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Tugas> tugas;

    //Relationships One to Many (Subscriber)
    @OneToMany(mappedBy = "npm")
    private List<User> subscriber;

    public Matkul(String kodeMatkul, String nama) {
        this.kodeMatkul = kodeMatkul;
        this.nama = nama;
    }
}

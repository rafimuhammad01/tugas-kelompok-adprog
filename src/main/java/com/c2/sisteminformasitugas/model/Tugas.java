package com.c2.sisteminformasitugas.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "tugas")
@Data
@NoArgsConstructor
public class Tugas {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "judul")
    private String judul;

    @Column(name = "deskripsi")
    private String deskripsi;

    @Column(name = "link")
    private String link;

    @Column(name = "deadline", nullable = false)
    private Timestamp deadline;


    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "kodeMatkul")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "kodeMatkul")
    private Matkul matkul;


    //Relationships One to Many (Komentar), Many to One (Matkul)
    @OneToMany(mappedBy = "tugas", cascade = CascadeType.ALL)
    private List<Komentar> komentar;
}

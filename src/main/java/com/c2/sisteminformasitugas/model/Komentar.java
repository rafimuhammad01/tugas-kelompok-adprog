package com.c2.sisteminformasitugas.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name="komentar")
@Data
@NoArgsConstructor
public class Komentar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name="comment")
    private String comment;


    /*
    //TODO : Relation with User (Many to One) and Tugas (Many to One)

    private User user;
    private Tugas tugas;
     */
}

package com.c2.sisteminformasitugas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Entity
@Table(name="komentar")
@Data
@AllArgsConstructor
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
    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private User author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tugas_id", nullable = false)
    private Tugas tugas;
}

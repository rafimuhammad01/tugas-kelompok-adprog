package com.c2.sisteminformasitugas.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

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

    //OneToMany matkul-tugas

    public Matkul(String kodeMatkul, String nama) {
        this.kodeMatkul = kodeMatkul;
        this.nama = nama;
    }
}

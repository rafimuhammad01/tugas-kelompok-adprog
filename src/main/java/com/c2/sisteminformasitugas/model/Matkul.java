package com.c2.sisteminformasitugas.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mata_kuliah")
@Data
@NoArgsConstructor
public class Matkul {
    @Id
    @Column(name = "kode_matkul", updatable = false)
    private String kodeMatkul;

    @Column(name = "nama_matkul")
    private String nama;

   //one to may tugas

    public Matkul(String kodeMatkul, String nama) {
        this.kodeMatkul = kodeMatkul;
        this.nama = nama;
    }
}

package com.c2.sisteminformasitugas.model;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;


@Entity
@Table(name = "todolist", schema = "")
@Data
@NoArgsConstructor
public class ToDoList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "judul")
    private String judul;

    @Column(name = "deadline", nullable = false)
    private Timestamp deadline;
}
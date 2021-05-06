package com.c2.sisteminformasitugas.model;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;


@Entity
@Table(name = "todolist")
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

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idTugas")
    private Tugas tugas;
    
}
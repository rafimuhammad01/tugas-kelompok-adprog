package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.ToDoList;

public interface ToDoListService {
    Iterable<ToDoList> getListToDoList();

    ToDoList createToDoList(ToDoList todolist);

    ToDoList getToDoList(int id);

    ToDoList updateToDoList(int id, ToDoList todolist);
}
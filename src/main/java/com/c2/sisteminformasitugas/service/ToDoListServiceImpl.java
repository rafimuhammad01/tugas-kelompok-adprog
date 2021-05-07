package com.c2.sisteminformasitugas.service;


import com.c2.sisteminformasitugas.model.ToDoList;
import com.c2.sisteminformasitugas.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToDoListServiceImpl implements ToDoListService{
    @Autowired
    private ToDoListRepository todolistRepository;

    @Override
    public Iterable<ToDoList> getListToDoList() {
        return todolistRepository.findAll();
    }

    @Override
    public ToDoList createToDoList(ToDoList todolist){
        todolistRepository.save(todolist);
        return todolist;
    }

    @Override
    public ToDoList getToDoList(int id){
        return todolistRepository.findById(id);
    }

    @Override
    public ToDoList updateToDoList(int id, ToDoList todolist){
        ToDoList currentToDoList = this.getToDoList(id);
        todolist.setJudul(currentToDoList.getJudul());
        todolist.setDeadline(currentToDoList.getDeadline());
        todolistRepository.save(todolist);
        return todolist;
    }

}
package com.c2.sisteminformasitugas.service;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.ToDoList;
import com.c2.sisteminformasitugas.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToDoListServiceImpl implements ToDoListService{
    @Autowired
    private ToDoListRepository todolistRepository;
    @Autowired
    private TugasService tugasService;

    @Override
    public Iterable<ToDoList> getListToDoList() {
        return todolistRepository.findAll();
    }

    @Override
    public ToDoList createToDoList(Tugas tugas, User user){
        ToDoList todolist = new ToDoList();
        todolist.setDeadline(tugas.getDeadline());
        todolist.setJudul(tugas.getJudul());
        todolist.setTugas(tugas);
        todolistRepository.save(todolist);
        return todolist;
    }

    @Override
    public ToDoList getToDoList(int id){
        return todolistRepository.findById(id);
    }

    @Override
    public ToDoList updateToDoList(int id, ToDoList todolist){
        todolist.setId(id);
        todolistRepository.save(todolist);
        return todolist;
    }
}
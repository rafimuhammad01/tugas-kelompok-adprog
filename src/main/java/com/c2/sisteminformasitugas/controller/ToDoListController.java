package com.c2.sisteminformasitugas.controller;

import com.c2.sisteminformasitugas.model.ToDoList;
import com.c2.sisteminformasitugas.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path= "/todolist")
public class ToDoListController {

    @Autowired
    private ToDoListService todolistService;

    @PostMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity postToDoList(@RequestBody ToDoList todolist) {
        return ResponseEntity.ok(todolistService.createToDoList(todolist));
    }

    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Iterable<ToDoList>> getListToDoList() {
        return ResponseEntity.ok(todolistService.getListToDoList());
    }

    @GetMapping(path = "/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getToDoList(@PathVariable(value = "id") int id) {
        ToDoList todolist = todolistService.getToDoList(id);
        if (todolist == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(todolist);
    }

    @PutMapping(path = "/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity updateToDoList(@PathVariable(value = "id") int id, @RequestBody ToDoList todolist) {
        return ResponseEntity.ok(todolistService.updateToDoList(id, todolist));
    }

}

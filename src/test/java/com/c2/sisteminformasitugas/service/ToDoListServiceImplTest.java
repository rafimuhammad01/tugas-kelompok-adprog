package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.ToDoList;
import com.c2.sisteminformasitugas.repository.ToDoListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.mockito.Mockito.lenient;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ToDoListServiceImplTest {
    @Mock
    private ToDoListRepository todolistRepository;

    @InjectMocks
    private ToDoListServiceImpl todolistService;

    private ToDoList todolist;

    @BeforeEach
    public void setUp() {
        todolist = new ToDoList();
        todolist.setId(20);
        todolist.setJudul("Tutorial6");
        todolist.setDeadline(Timestamp.valueOf("2021-04-26 09:00:00"));
    }


    @Test
    void TestGetListtoDolist() {
        Iterable<ToDoList> listToDoList = todolistRepository.findAll();
        lenient().when(todolistService.getListToDoList()).thenReturn(listToDoList);
        Iterable<ToDoList> listToDoListResult = todolistService.getListToDoList();
        Assertions.assertIterableEquals(listToDoList, listToDoListResult);
    }

    @Test
    void TestCreateToDoList() {
        lenient().when(todolistService.createToDoList(todolist)).thenReturn(todolist);
        ToDoList resultTugas = todolistService.createToDoList(todolist);
        Assertions.assertEquals(todolist.getId(), resultTugas.getId());
    }

    @Test
    void TestGetToDoList() {
        lenient().when(todolistService.getToDoList(todolist.getId())).thenReturn(todolist);
        ToDoList resultTugas = todolistService.getToDoList(todolist.getId());
        Assertions.assertEquals(todolist.getId(), resultTugas.getId());
    }

    @Test
    void TestUpdateToDoList() {
        todolistService.createToDoList(todolist);
        String judul = "tutorial8";
        Timestamp deadline = Timestamp.valueOf("2021-04-26 21:00:00");
        String deskripsi = "DESC";
        String link = "link.com";

        todolist.setJudul(judul);
        todolist.setDeadline(deadline);

        ToDoList expectedTugas = todolist;
        expectedTugas.setJudul(judul);
        expectedTugas.setDeadline(deadline);


        ToDoList resultTugas = todolistService.updateToDoList(todolist.getId(), todolist);
        Assertions.assertEquals(expectedTugas.getJudul(), resultTugas.getJudul());
        Assertions.assertEquals(expectedTugas.getDeadline(), resultTugas.getDeadline());
    }

}

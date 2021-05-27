package com.c2.sisteminformasitugas.service;


import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import com.c2.sisteminformasitugas.repository.TugasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TugasServiceImpl implements TugasService{
    @Autowired
    private TugasRepository tugasRepository;
    @Autowired
    private MatkulRepository matkulRepository;

    @Override
    public Iterable<Tugas> getListTugas(String kodeMatkul) {
        var matkul = matkulRepository.findByKodeMatkul(kodeMatkul);
        return tugasRepository.findByMatkul(matkul);
    }

    @Override
    public Tugas createTugas(Tugas tugas) throws IOException, InterruptedException {
        int length = tugas.getMatkul().getSubscribers().size();
        var listOfEmail = new String[length];
        for (var i = 0; i < length; i++) {
            listOfEmail[i] = tugas.getMatkul().getSubscribers().get(i).getEmail();
        }


        //body
        Map<Object, Object> data = new HashMap<>();
        data.put("toEmail", listOfEmail);
        data.put("body", "Ada tugas baru '" + tugas.getJudul() + "' untuk mata kuliah " + tugas.getMatkul().getNama());
        data.put("subject", "Ada Tugas Baru");

        var objectMapper = new ObjectMapper();
        var requestBody = objectMapper
                .writeValueAsString(data);

        var client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://situgas-email-service.herokuapp.com/email"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)) // add request header
                .header("Content-Type", "application/json")
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());

        tugasRepository.save(tugas);
        return tugas;
    }

    @Override
    public Tugas getTugas(int id){
        return tugasRepository.findById(id);
    }

    @Override
    public Tugas updateTugas(int id, Tugas tugas){
        var tugasFound = tugasRepository.findById(id);
        tugas.setId(id);
        tugas.setKomentar(tugasFound.getKomentar());
        tugas.setMatkul(tugasFound.getMatkul());
        tugas.setKomentar(new ArrayList<>());
        tugasRepository.save(tugas);
        return tugas;
    }

    @Override
    public void deleteTugas(int id) {
        var tugas = this.getTugas(id);
        tugasRepository.delete(tugas);
    }

    @Override
    public List<Tugas> getAllTugasByUser(User user) {
        List<Tugas> listTugas = new ArrayList<>();
        for (Matkul matkul:user.getMatkulList()) {
            listTugas.addAll(matkul.getTugas());
        }
        return listTugas;
    }
}

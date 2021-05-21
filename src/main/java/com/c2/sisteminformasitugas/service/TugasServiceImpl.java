package com.c2.sisteminformasitugas.service;


import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import com.c2.sisteminformasitugas.repository.TugasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class TugasServiceImpl implements TugasService{
    @Autowired
    private TugasRepository tugasRepository;
    @Autowired
    private MatkulRepository matkulRepository;

    @Override
    public Iterable<Tugas> getListTugas(String kodeMatkul) {
        Matkul matkul = matkulRepository.findByKodeMatkul(kodeMatkul);
        return tugasRepository.findByMatkul(matkul);
    }

    @Override
    public Tugas createTugas(Tugas tugas) throws IOException, InterruptedException {
        System.out.println(tugas);
        int length = tugas.getMatkul().getSubscribers().size();
        String[] listOfEmail = new String[length];
        for (int i = 0; i < length; i++) {
            listOfEmail[i] = tugas.getMatkul().getSubscribers().get(i).getEmail();
        }


        //body
        Map<Object, Object> data = new HashMap<>();
        data.put("toEmail", listOfEmail);
        data.put("body", "Ada tugas baru '" + tugas.getJudul() + "' untuk mata kuliah " + tugas.getMatkul().getNama());
        data.put("subject", "Ada Tugas Baru");

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(data);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://situgas-email-service.herokuapp.com/email"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)) // add request header
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        tugasRepository.save(tugas);
        return tugas;
    }

    @Override
    public Tugas getTugas(int id){
        return tugasRepository.findById(id);
    }

    @Override
    public Tugas updateTugas(int id, Tugas tugas){
        Tugas tugasFound = tugasRepository.findById(id);
        tugas.setId(id);
        tugas.setKomentar(tugasFound.getKomentar());
        tugas.setMatkul(tugasFound.getMatkul());
        tugasRepository.save(tugas);
        return tugas;
    }

    @Override
    public void deleteTugas(int id) {
        Tugas tugas = this.getTugas(id);
        tugasRepository.delete(tugas);
    }
}

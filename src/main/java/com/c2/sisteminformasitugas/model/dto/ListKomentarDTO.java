package com.c2.sisteminformasitugas.model.dto;

import com.c2.sisteminformasitugas.model.Komentar;

public class ListKomentarDTO {
    private int status;
    private String message;
    private Iterable<Komentar> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Iterable<Komentar> getData() {
        return data;
    }

    public void setData(Iterable<Komentar> data) {
        this.data = data;
    }
}

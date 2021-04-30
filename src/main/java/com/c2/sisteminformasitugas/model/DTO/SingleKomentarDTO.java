package com.c2.sisteminformasitugas.model.DTO;

import com.c2.sisteminformasitugas.model.Komentar;

public class SingleKomentarDTO {
    private int status;
    private String message;
    private Komentar data;

    public SingleKomentarDTO(){}

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

    public Komentar getData() {
        return data;
    }

    public void setData(Komentar data) {
        this.data = data;
    }
}

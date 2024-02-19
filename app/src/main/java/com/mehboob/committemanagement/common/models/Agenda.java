package com.mehboob.committemanagement.common.models;

public class Agenda {

    private String agendaDesc;
    private String image;

    public Agenda() {
    }

    public Agenda(String agendaDesc, String image) {
        this.agendaDesc = agendaDesc;
        this.image = image;
    }


    public String getAgendaDesc() {
        return agendaDesc;
    }

    public void setAgendaDesc(String agendaDesc) {
        this.agendaDesc = agendaDesc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

package com.mehboob.committemanagement.common.models;

import java.util.List;

public class Committee {

    private String committeeName;
    private String committeeMembersCount;

    private String committeeSupervisor;

    private List<String> committeeMembers;

    private Agenda agenda;

    public Committee() {
    }


    public Committee(String committeeName, String committeeMembersCount, String committeeSupervisor, List<String> committeeMembers,Agenda agenda) {
        this.committeeName = committeeName;
        this.committeeMembersCount = committeeMembersCount;
        this.committeeSupervisor = committeeSupervisor;
        this.committeeMembers = committeeMembers;
        this.agenda= agenda;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public String getCommitteeMembersCount() {
        return committeeMembersCount;
    }

    public void setCommitteeMembersCount(String committeeMembersCount) {
        this.committeeMembersCount = committeeMembersCount;
    }

    public String getCommitteeSupervisor() {
        return committeeSupervisor;
    }

    public void setCommitteeSupervisor(String committeeSupervisor) {
        this.committeeSupervisor = committeeSupervisor;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public List<String> getCommitteeMembers() {
        return committeeMembers;
    }

    public void setCommitteeMembers(List<String> committeeMembers) {
        this.committeeMembers = committeeMembers;
    }

    @Override
    public String toString() {
        return "Committee{" +
                "committeeName='" + committeeName + '\'' +
                ", committeeMembersCount='" + committeeMembersCount + '\'' +
                ", committeeSupervisor='" + committeeSupervisor + '\'' +
                ", committeeMembers=" + committeeMembers +
                '}';
    }
}

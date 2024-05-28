package com.example.splashactivity;

public class MainModel {



    String eventName;
    String eventDetails;
    String fromDate;
    String name;
    String emailOfInvitees;
    String emailOfHost;
    String uid;

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    String participantName;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String eventNameInsideTheHostNeJinkoInviteKraHaiUnkaData;



    public String getEventNameInsideTheHostNeJinkoInviteKraHaiUnkaData() {
        return eventNameInsideTheHostNeJinkoInviteKraHaiUnkaData;
    }


    public void setEventNameInsideTheHostNeJinkoInviteKraHaiUnkaData(String eventNameInsideTheHostNeJinkoInviteKraHaiUnkaData) {
        this.eventNameInsideTheHostNeJinkoInviteKraHaiUnkaData = eventNameInsideTheHostNeJinkoInviteKraHaiUnkaData;
    }

    public String getEmailOfInvitees() {
        return emailOfInvitees;
    }

    public void setEmailOfInvitees(String emailOfInvitees) {
        this.emailOfInvitees = emailOfInvitees;
    }

    public String getEmailOfHost() {
        return emailOfHost;
    }

    public void setEmailOfHost(String emailOfHost) {
        this.emailOfHost = emailOfHost;
    }

    public MainModel(String eventName, String eventDetails, String fromDate , String name, String emailOfInvitees , String emailOfHost, String eventNameInsideTheHostNeJinkoInviteKraHaiUnkaData , String uid ,String participantName ) {
        this.eventNameInsideTheHostNeJinkoInviteKraHaiUnkaData = eventNameInsideTheHostNeJinkoInviteKraHaiUnkaData;
        this.eventName = eventName;
        this.eventDetails = eventDetails;
        this.fromDate = fromDate;
        this.name = name;
        this.emailOfInvitees = emailOfInvitees;
        this.emailOfHost = emailOfHost;
        this.uid = uid;
        this.participantName = participantName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public MainModel() {
    }
}

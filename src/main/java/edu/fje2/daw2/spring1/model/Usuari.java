package edu.fje2.daw2.spring1.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Usuari {
    @Id
    private String oauthID;
    private List<String> ciutats;

    public Usuari(String oauthID, List<String> ciutats) {
        this.oauthID = oauthID;
        this.ciutats = ciutats;
    }

    public String getOauthID() {
        return oauthID;
    }

    public void setOauthID(String oauthID) {
        this.oauthID = oauthID;
    }

    public List<String> getCiutats() {
        return ciutats;
    }

    public void setCiutats(List<String> ciutats) {
        this.ciutats = ciutats;
    }

}


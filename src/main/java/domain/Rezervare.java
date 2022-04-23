package domain;

import java.io.Serializable;

public class Rezervare extends Entity<Long> implements Serializable {
    private Long excursieID;
    private String nume;
    private String telefon;
    private int nrBilete;

    public Rezervare(Long id, Long e, String nume, String telefon, int nrBilete) {
        super.setId(id);
        this.excursieID = e;
        this.nume = nume;
        this.telefon = telefon;
        this.nrBilete = nrBilete;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public int getNrBilete() {
        return nrBilete;
    }

    public void setNrBilete(int nrBilete) {
        this.nrBilete = nrBilete;
    }


    public Long getExcursieID() {
        return excursieID;
    }

    public void setExcursieID(Long excursieID) {
        this.excursieID = excursieID;
    }
}

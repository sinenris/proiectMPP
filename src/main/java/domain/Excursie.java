package domain;

import java.io.Serializable;

public class Excursie extends Entity<Long> implements Serializable {
    private String obiectivTuristic;
    private String numeFirmaTransport;
    private int oraPlecarii;
    private Double pret;
    private Long locuriDisponibile;

    public Excursie(Long id, String obiectivTuristic, String numeFirmaTransport, int oraPlecarii, Double pret, Long locuriDisponibile) {
        super.setId(id);
        this.obiectivTuristic = obiectivTuristic;
        this.numeFirmaTransport = numeFirmaTransport;
        this.oraPlecarii = oraPlecarii;
        this.pret = pret;
        this.locuriDisponibile = locuriDisponibile;
    }

    public String getObiectivTuristic() {
        return obiectivTuristic;
    }

    public void setObiectivTuristic(String obiectivTuristic) {
        this.obiectivTuristic = obiectivTuristic;
    }

    public String getNumeFirmaTransport() {
        return numeFirmaTransport;
    }

    public void setNumeFirmaTransport(String numeFirmaTransport) {
        this.numeFirmaTransport = numeFirmaTransport;
    }

    public int getOraPlecarii() {
        return oraPlecarii;
    }

    public void setOraPlecarii(int oraPlecarii) {
        this.oraPlecarii = oraPlecarii;
    }

    public Double getPret() {
        return pret;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    public Long getLocuriDisponibile() {
        return locuriDisponibile;
    }

    public void setLocuriDisponibile(Long locuriDisponibile) {
        this.locuriDisponibile = locuriDisponibile;
    }

    @Override
    public String toString(){
        return "Id=" + getId() + " " + obiectivTuristic + " " + numeFirmaTransport + " " + oraPlecarii + " "+pret + " "+ locuriDisponibile;
    }
}

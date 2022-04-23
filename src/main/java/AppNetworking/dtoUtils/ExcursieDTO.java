package AppNetworking.dtoUtils;

import java.io.Serializable;

public class ExcursieDTO implements Serializable {
    private Long eid;
    private String obiectivTuristic;
    private String numeFirmaTransport;
    private int oraPlecarii;
    private Double pret;
    private Long locuriDisponibile;

    public ExcursieDTO(Long eid, String obiectivTuristic, String numeFirmaTransport, int oraPlecarii, Double pret, Long locuriDisponibile) {
        this.eid = eid;
        this.obiectivTuristic = obiectivTuristic;
        this.numeFirmaTransport = numeFirmaTransport;
        this.oraPlecarii = oraPlecarii;
        this.pret = pret;
        this.locuriDisponibile = locuriDisponibile;
    }

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
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
}

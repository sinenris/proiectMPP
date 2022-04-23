package AppNetworking.dtoUtils;

import domain.Excursie;
import java.util.*;
public class DTOUtils {
    public static Excursie getFromDTO(ExcursieDTO excursieDTO){
        Long eid = excursieDTO.getEid();
        String obiectivTuristic = excursieDTO.getObiectivTuristic();
        String numeFirmaTransport = excursieDTO.getNumeFirmaTransport();
        int oraPlecarii = excursieDTO.getOraPlecarii();
        Double pret = excursieDTO.getPret();
        Long locuriDisponibile= excursieDTO.getLocuriDisponibile();
        return new Excursie(eid, obiectivTuristic,numeFirmaTransport,oraPlecarii,pret,locuriDisponibile);
    }
    public static ExcursieDTO getDTO(Excursie excursie){
        Long eid = excursie.getId();
        String obiectivTuristic = excursie.getObiectivTuristic();
        String numeFirmaTransport = excursie.getNumeFirmaTransport();
        int oraPlecarii = excursie.getOraPlecarii();
        Double pret = excursie.getPret();
        Long locuriDisponibile= excursie.getLocuriDisponibile();
        return new ExcursieDTO(eid, obiectivTuristic,numeFirmaTransport,oraPlecarii,pret,locuriDisponibile);
    }
    public static Iterable<ExcursieDTO> getDTO(Iterable<Excursie> excursii){
        List<ExcursieDTO> excursieDTOS = new ArrayList<>();
        excursii.forEach(x->excursieDTOS.add(getDTO(x)));
        return excursieDTOS;
    }
    public static Iterable<Excursie> getFromDTO(Iterable<ExcursieDTO> excursieDTOS){
        List<Excursie> excursie = new ArrayList<>();
        excursieDTOS.forEach(x->excursie.add(getFromDTO(x)));
        return excursie;
    }
}

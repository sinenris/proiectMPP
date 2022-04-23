import domain.Excursie;
import domain.Rezervare;
import domain.Utilizator;
import repository.ExcursieRepository;
import repository.RezervareRepository;
import repository.UtilizatorRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainDB {
    public static void main(String[] args){
        Properties props = new Properties();
        try{
            props.load(new FileReader("bd.config"));

        } catch (FileNotFoundException e) {
            System.out.println("Nu am gasit");
        } catch (IOException e) {
            System.out.println("Cannot find " + e );
        }
        ExcursieRepository excursieRepo = new ExcursieRepository(props);
        RezervareRepository rezervareRepository = new RezervareRepository(props);
 //       rezervareRepository.save(new Rezervare((long)1,(long)2,"Matei","012",13 ));
//        for(Excursie e : excursieRepo.findAll()){
//            System.out.println(e);
//        }
        System.out.println(excursieRepo.findOne((long)1));
        excursieRepo.update((long)1,new Excursie((long)1,"Bucuresti","Rina",1200,12.0,(long)34));
//        for(Excursie e : excursieRepo.findByNameHour("Bucuresti",1200,1400)){
//            System.out.println(e);
//        }
        UtilizatorRepository utilizatorRepository = new UtilizatorRepository(props);
        System.out.println(utilizatorRepository.findByNamePwd("abc","def").getId());

    }
}
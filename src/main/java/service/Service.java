package service;

import domain.Excursie;
import domain.Rezervare;
import domain.Utilizator;
import repository.IRepository;
import repository.IRezervareRepository;
import repository.IUtilizatorRepository;
import repository.iExcursieRepository;
import utils.ExcursieEvent;
import utils.Observable;
import utils.Observer;
import utils.STEType;

import java.util.*;

public class Service implements Observable<ExcursieEvent> {
    IUtilizatorRepository repoUtilizator;
    iExcursieRepository repoExcursie;
    IRezervareRepository repoRezervare;

    public Service(IUtilizatorRepository repoUtilizator, iExcursieRepository repoExcursie, IRezervareRepository repoRezervare) {
        this.repoUtilizator = repoUtilizator;
        this.repoExcursie = repoExcursie;
        this.repoRezervare = repoRezervare;
    }


    public Iterable<Excursie> getAllExcursie(){
        return repoExcursie.findAll();
    }

    public Iterable<Excursie> getSomeExcursie(String obiectiv, int min, int max){
        return repoExcursie.findByNameHour(obiectiv, min, max);
    }
    public boolean executaRezervare(Rezervare r){
        //gaseste o excursie
        Excursie e = repoExcursie.findOne(r.getExcursieID());
        if(e.getLocuriDisponibile()-r.getNrBilete() >= 0) {
            e.setLocuriDisponibile(e.getLocuriDisponibile() - r.getNrBilete());
            repoExcursie.update(r.getExcursieID(), e);
            repoRezervare.save(r);
            notifyObservers(new ExcursieEvent(STEType.UPDATE,e,repoExcursie.findOne(r.getExcursieID())));
            return true;
        }
        return false;

    }

    public Utilizator findByNamePw(String nume, String pw){
        return repoUtilizator.findByNamePwd(nume,pw);
    }


    private List<Observer<ExcursieEvent>> observers = new ArrayList<>();
    @Override
    public void addObserver(Observer<ExcursieEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<ExcursieEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(ExcursieEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}

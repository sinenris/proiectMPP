package service;

import domain.Excursie;
import domain.Rezervare;
import domain.Utilizator;

public interface IService {
    void logout(Utilizator u, IObserver obs) throws ServiceException;
    Iterable<Excursie> getAllExcursie() throws ServiceException;
    Iterable<Excursie> getSomeExcursie(String obiectiv, int min, int max) throws ServiceException;
    void executaRezervare(Rezervare r) throws ServiceException;
    public Utilizator findByNamePw(String nume, String pw, IObserver client) throws ServiceException;
}

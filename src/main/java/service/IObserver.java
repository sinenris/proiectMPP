package service;

import domain.Excursie;

public interface IObserver {
    void executaRezervare(Iterable<Excursie> e) throws ServiceException;
}

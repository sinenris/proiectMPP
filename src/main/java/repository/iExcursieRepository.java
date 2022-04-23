package repository;

import domain.Excursie;

import java.util.List;

public interface iExcursieRepository extends IRepository<Long, Excursie> {
    //Cauta si returneaza o lista cu toate Excursiile efectuate intr-un anumit loc
    // (obiectivTuristic) si intr-un anumit interval de timp(min, max);
    //input: obiectivTuristic - String
    //      min - int, minimum hour.
    //      max - int, maximum hour.
    List<Excursie> findByNameHour(String obiectivTuristic, int min, int max );

}

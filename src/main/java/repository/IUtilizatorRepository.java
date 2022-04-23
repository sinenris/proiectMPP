package repository;

import domain.Utilizator;

public interface IUtilizatorRepository extends IRepository<Long, Utilizator> {
    //Find an Utilizator by name & password.
    Utilizator findByNamePwd(String username, String pw);
}

package repository;

import domain.Rezervare;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.sql.*;
public class RezervareRepository implements IRezervareRepository {

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public RezervareRepository(Properties properties) {
        dbUtils = new JdbcUtils(properties);
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Rezervare entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement(
                "insert into Rezervare (telefon, nume, nrBilete, excursieID) values (?,?,?,?)"
        )){
            preparedStatement.setString(1, entity.getTelefon());
            preparedStatement.setString(2, entity.getNume());
            preparedStatement.setInt(3,entity.getNrBilete());
            preparedStatement.setLong(4, entity.getExcursieID());
            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances",result);

        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("Error DB" + throwables);
        }
        logger.traceExit();

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(Long aLong, Rezervare entity) {

    }

    @Override
    public Rezervare findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Rezervare> findAll() {
        return null;
    }
}

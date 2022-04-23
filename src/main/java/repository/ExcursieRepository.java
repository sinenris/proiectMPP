package repository;

import domain.Excursie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ExcursieRepository implements iExcursieRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ExcursieRepository(Properties props) {
        logger.info("Initializing repo w properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Excursie entity) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(Long aLong, Excursie entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("UPDATE Excursie SET locuriDisponibile=? WHERE eid=?")){
            preStmt.setInt(1,entity.getLocuriDisponibile().intValue());
            preStmt.setInt(2,aLong.intValue());
            preStmt.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("error db" + throwables);
        }
    }

    @Override
    public Excursie findOne(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Excursie where eid=?")){
            preStmt.setInt(1,aLong.intValue());
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    long id = result.getInt("eid");
                    String obiectivTuristicc = result.getString("obiectivTuristic");
                    String numeFirmaTransport = result.getString("numeFirmaTransport");
                    int oraPlecatii = result.getInt("oraPlecarii");
                    Double pret = result.getDouble("pret");
                    long locuriDisponibile = result.getInt("locuriDisponibile");
                    Excursie excursie = new Excursie(id,obiectivTuristicc,numeFirmaTransport,oraPlecatii,pret,locuriDisponibile);
                    return excursie;
                }
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("error db" + throwables);
        }
        return null;
    }

    @Override
    public Iterable<Excursie> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Excursie> excursii= new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Excursie")){
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    long id = result.getInt("eid");
                    String obiectivTuristic = result.getString("obiectivTuristic");
                    String numeFirmaTransport = result.getString("numeFirmaTransport");
                    int oraPlecarii = result.getInt("oraPlecarii");
                    Double pret = result.getDouble("pret");
                    long locuriDisponibile = result.getInt("locuriDisponibile");
                    Excursie excursie = new Excursie(id,obiectivTuristic,numeFirmaTransport,oraPlecarii,pret,locuriDisponibile);
                    excursii.add(excursie);
                }
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.out.println("DB error" + throwables);
        }
        return excursii;
    }

    //Cauta si returneaza o lista cu toate Excursiile efectuate intr-un anumit loc
    // (obiectivTuristic) si intr-un anumit interval de timp(min, max);
    //input: obiectivTuristic - String
    //      min - int, minimum hour.
    //      max - int, maximum hour.
    @Override
    public List<Excursie> findByNameHour(String obiectivTuristic, int min, int max) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Excursie> excursii = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Excursie where obiectivTuristic=? and oraPlecarii between ? and ?")){
            preStmt.setString(1,obiectivTuristic);
            preStmt.setInt(2,min);
            preStmt.setInt(3,max);
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    long id = result.getInt("eid");
                    String obiectivTuristicc = result.getString("obiectivTuristic");
                    String numeFirmaTransport = result.getString("numeFirmaTransport");
                    int oraPlecatii = result.getInt("oraPlecarii");
                    Double pret = result.getDouble("pret");
                    long locuriDisponibile = result.getInt("locuriDisponibile");
                    Excursie excursie = new Excursie(id,obiectivTuristicc,numeFirmaTransport,oraPlecatii,pret,locuriDisponibile);
                    excursii.add(excursie);
                }
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("error db" + throwables);
        }
        return excursii;
    }
}

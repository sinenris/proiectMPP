package repository;

import domain.Utilizator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UtilizatorRepository implements IUtilizatorRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public UtilizatorRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Utilizator entity) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(Long aLong, Utilizator entity) {

    }

    @Override
    public Utilizator findOne(Long aLong) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from main.Utilizator where uid=?")){
            preStmt.setInt(1,aLong.intValue());
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    long id = result.getInt("uid");
                    String usernamee = result.getString("username");
                    String pswd = result.getString("password");
                    Utilizator u = new Utilizator(id,usernamee,pswd);
                    return u;
                }
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("error db" + throwables);
        }
        return null;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        return null;
    }

    @Override
    public Utilizator findByNamePwd(String username, String pw) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from main.Utilizator where username=? and password=?")){
            preStmt.setString(1,username);
            preStmt.setString(2,pw);

            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    long id = result.getInt("uid");
                    String usernamee = result.getString("username");
                    String pswd = result.getString("password");
                    Utilizator u = new Utilizator(id,usernamee,pswd);
                    return u;
                }
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
            System.err.println("error db" + throwables);
        }
        return null;
    }
}

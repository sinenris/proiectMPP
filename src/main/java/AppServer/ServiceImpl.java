package AppServer;

import domain.Excursie;
import domain.Rezervare;
import domain.Utilizator;
import repository.IRezervareRepository;
import repository.IUtilizatorRepository;
import repository.iExcursieRepository;
import service.IObserver;
import service.IService;
import service.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceImpl implements IService {

    private final int defaultThreadsNo=5;
    private IUtilizatorRepository repoUtilizator;
    private iExcursieRepository repoExcursie;
    private IRezervareRepository repoRezervare;
    private Map<Long, IObserver> totiUtilizatorii; //toti utilizatorii.

    public ServiceImpl(IUtilizatorRepository repoUtilizator, iExcursieRepository repoExcursie, IRezervareRepository repoRezervare) {
        this.repoUtilizator = repoUtilizator;
        this.repoExcursie = repoExcursie;
        this.repoRezervare = repoRezervare;
        totiUtilizatorii = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void logout(Utilizator u, IObserver obs) throws ServiceException {
        IObserver localClient = totiUtilizatorii.remove(repoUtilizator.findByNamePwd(u.getUsername(),u.getPassword()).getId());
        if(localClient == null){
            throw new ServiceException("Not logged in");
        }
    }

    @Override
    public synchronized Iterable<Excursie> getAllExcursie() throws ServiceException {

        List<Excursie> excursieList = new ArrayList<>();
        System.out.println("Toate excursiile:");
        for (Excursie e : repoExcursie.findAll()){
            excursieList.add(e);
        }
        return excursieList;
    }

    @Override
    public synchronized Iterable<Excursie> getSomeExcursie(String obiectiv, int min, int max) throws ServiceException {
        List<Excursie> excursieList = new ArrayList<>();
        System.out.println("Unele excursii:");
        for (Excursie e : repoExcursie.findByNameHour(obiectiv, min, max)){
            excursieList.add(e);
        }
        System.out.println("Size: " + excursieList.size());

        return excursieList;
    }

    @Override
    public synchronized void executaRezervare(Rezervare r) throws ServiceException {
        System.out.println("Reservare + "  + r.getExcursieID());
        Excursie e = repoExcursie.findOne(r.getExcursieID());
        if(e!=null && e.getLocuriDisponibile()-r.getNrBilete() >= 0){
            e.setLocuriDisponibile(e.getLocuriDisponibile() - r.getNrBilete());
            repoExcursie.update(r.getExcursieID(), e);
            System.out.println("Updated.");
            repoRezervare.save(r);
            System.out.println("Reserved");
            notifyAllUsers(e);
        }
        else
            throw new ServiceException("Invalid");
    }

    private synchronized void notifyAllUsers(Excursie e) {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(Long id : totiUtilizatorii.keySet()){
            IObserver obs = totiUtilizatorii.get(id);
            executor.execute(()->{
                try{
                    obs.executaRezervare(repoExcursie.findAll());
                } catch (ServiceException ee ){
                    System.err.println("Error notifying" + ee);
                }
            });
        }
        executor.shutdown();
    }

    @Override
    public synchronized Utilizator findByNamePw(String nume, String pw, IObserver client) throws ServiceException {
        Utilizator u = repoUtilizator.findByNamePwd(nume,pw);
        if(u!=null){
            if(totiUtilizatorii.get(u.getId())!=null){
                throw new ServiceException("Deja logat!!");
            }
            totiUtilizatorii.put(u.getId(),client);
            return repoUtilizator.findByNamePwd(nume,pw);
        }
        else{
            throw new ServiceException("inexistent");
        }
    }


}

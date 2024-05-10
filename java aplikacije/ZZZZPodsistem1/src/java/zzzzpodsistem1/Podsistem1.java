/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzzzpodsistem1;

/**
 *
 * @author Korisnik
 */
import entiteti.Korisnik;
import entiteti.Mesto;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import requests.*;
import requests.RequestForSubsystem.TypeOfSubsystem;
import requests.RequestForSubsystem.TypeOfRequest;
import responses.SubsystemResponse;
/**
 *
 * @author Korisnik
 */
public class Podsistem1 {
    
    

    @Resource(lookup="queue1")
    private static Queue queue1;
    
    @Resource(lookup="queue2")
    private static Queue queue2;
    
    @Resource(lookup="queue3")
    private static Queue queue3;
     
    @Resource(lookup="queueServer")
    private static Queue queueServer;
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    private static JMSProducer producer;
    private static JMSContext context;
    private static String persistenceUnitSub1 = "pu1";
    
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue1);
        producer = context.createProducer();
        Message msg;
        while ((msg = consumer.receiveNoWait()) !=null ){}
        
        while (true){
            try {
                msg = consumer.receive();
                
                System.out.println("Received request");
                
                if(!(msg instanceof ObjectMessage) ){
                    System.out.println("Not an object message"); continue;
                }
                ObjectMessage objMsg = (ObjectMessage)msg;
                Serializable object = objMsg.getObject();
                
                if(!(object instanceof RequestForSubsystem )){
                    System.err.println("Not a request for subsystem1"); continue;
                }
                
                TypeOfRequest request = ((RequestForSubsystem)object).typeOfRequest();
                SubsystemResponse response = null;
                if(request == TypeOfRequest.KREIRANJE_MESTO){
                    response = kreiranjeGrada((KreiranjeMesta)object);
                }
                else if (request == TypeOfRequest.KREIRANJE_KORISNIK){
                    response = kreiranjeKorisnika((KreiranjeKorisnika)object);
                }
                else if (request == TypeOfRequest.PROMENA_EMAIL_KORISNIK){
                    response = promenaEmailaKorisniku((PromenaEmaila)object);
                }
                else if (request == TypeOfRequest.PROMENA_MESTO_KORISNIK){
                    response = promenaMestaKorisnika((PromenaMesta)object);
                }
                else if (request == TypeOfRequest.DOHVATANJE_KORISNIKA){
                    response = dohvatiSveKorisnike((DohvatiKorisnike)object);
                }
                else if (request == TypeOfRequest.DOHVATANJE_MESTA){
                    response = dohvatiSvaMesta((DohvatiMesta)object);
                }
                ObjectMessage responseMsg = context.createObjectMessage(response);
                int property = ((RequestForSubsystem)object).myCode;
                responseMsg.setStringProperty("Code", ((Integer)property).toString() );
                producer.send(queueServer, responseMsg);
                
            } catch (JMSException ex) {
                Logger.getLogger(Podsistem1.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        
    }

    private static SubsystemResponse kreiranjeGrada(KreiranjeMesta kreiranjeMesta) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub1);
        EntityManager em = emf.createEntityManager();
        String nazivMesto = kreiranjeMesta.grad;
        
        List<Mesto> mesta = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", nazivMesto).getResultList();
        if(!mesta.isEmpty()){
            return new SubsystemResponse("Grad sa prosledjenim nazivom vec postoji");
        }
        
        Mesto mesto = new Mesto();
        mesto.setNaziv(nazivMesto);
        em.getTransaction().begin();
        em.persist(mesto);
        //em.flush();
        em.getTransaction().commit();
        em.clear();
        return new SubsystemResponse("Grad " + nazivMesto + " je uspesno kreiran");
    }

    private static SubsystemResponse kreiranjeKorisnika(KreiranjeKorisnika korisnik) {
        String ime = korisnik.ime;
        String mesto = korisnik.idMes;
        String email = korisnik.email;
        Character pol = korisnik.pol;
        String godiste = ((Integer)korisnik.godiste).toString();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub1);
        EntityManager em = emf.createEntityManager();
        
        List<Mesto> mesta = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", mesto).getResultList();
        if(mesta.isEmpty()){
            Mesto mestoNew = new Mesto();
            mestoNew.setNaziv(mesto);
            em.getTransaction().begin();
            em.persist(mestoNew);
            em.getTransaction().commit();
        }
        List<Mesto> mestoKorisnika = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", mesto).getResultList();
        Mesto idMes = mestoKorisnika.get(0);
        
        Korisnik kreiranjeKorisnika = new Korisnik();
        kreiranjeKorisnika.setEmail(email);
        kreiranjeKorisnika.setGodiste(godiste);
        kreiranjeKorisnika.setIdMes(idMes);
        kreiranjeKorisnika.setIme(ime);
        kreiranjeKorisnika.setPol(pol);
        em.getTransaction().begin();
        em.persist(kreiranjeKorisnika);
        //em.flush();
        em.getTransaction().commit();
        
        
        Korisnik kr = em.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", email).getResultList().get(0);
        UbacenNoviKorisnik u = new UbacenNoviKorisnik(kr.getIdKor(), kr.getIme(), kr.getEmail(), kr.getGodiste(), kr.getPol(),kr.getIdMes().getIdMes());
        
        ObjectMessage responseMsg = context.createObjectMessage(u);
        
        producer.send(queue2, responseMsg);
        producer.send(queue3, responseMsg);
        em.clear();
        return new SubsystemResponse("Korisnik: " + kreiranjeKorisnika + " je uspesno kreiran");
        
    }

    private static SubsystemResponse promenaEmailaKorisniku(PromenaEmaila promenaEmaila) {
        int idKor = promenaEmaila.idKor;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub1);
        EntityManager em = emf.createEntityManager();
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor", idKor).getResultList();
        if(korisnici.isEmpty()){
            return new SubsystemResponse("Korisnik nije kreiran");
        }
        Korisnik korisnik = korisnici.get(0);
        korisnik.setEmail(promenaEmaila.email);
        em.getTransaction().begin();
        em.persist(korisnik);
        //em.flush();
        em.getTransaction().commit();
        
        Korisnik kr = em.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", promenaEmaila.email).getResultList().get(0);
        UAzurirajEmailKorisniku u = new UAzurirajEmailKorisniku(kr.getIdKor(), kr.getEmail());
        
        ObjectMessage responseMsg = context.createObjectMessage(u);
        
        producer.send(queue2, responseMsg);
        producer.send(queue3, responseMsg);
        em.clear();
        
        return new SubsystemResponse("Korisniku " + promenaEmaila.idKor + " uspesno promenjen email na " + promenaEmaila.email);
    }

    private static SubsystemResponse promenaMestaKorisnika(PromenaMesta promenaMesta) {
        int idKor = promenaMesta.idKor;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub1);
        EntityManager em = emf.createEntityManager();
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor", idKor).getResultList();
        if(korisnici.isEmpty()){
            return new SubsystemResponse("Korisnik nije kreiran");
        }
        Korisnik korisnik = korisnici.get(0);
        String mesto = promenaMesta.mesto;
        List<Mesto> mesta = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", mesto).getResultList();
        if(mesta.isEmpty()){
            Mesto mestoNew = new Mesto();
            mestoNew.setNaziv(mesto);
            em.getTransaction().begin();
            em.persist(mestoNew);
            em.getTransaction().commit();
        }
        List<Mesto> mestoKorisnika = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", mesto).getResultList();
        Mesto idMes = mestoKorisnika.get(0);  
        korisnik.setIdMes(idMes);
        em.getTransaction().begin();
        em.persist(korisnik);
        //em.flush();
        em.getTransaction().commit();
        
        
        Korisnik kr = em.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", korisnik.getEmail()).getResultList().get(0);
        UAzurirajMestoKorisniku u = new UAzurirajMestoKorisniku(kr.getIdKor(), kr.getIdMes().getIdMes());
        
        ObjectMessage responseMsg = context.createObjectMessage(u);
        
        producer.send(queue2, responseMsg);
        producer.send(queue3, responseMsg);
        em.clear();
        
        return new SubsystemResponse("Korisniku " + promenaMesta.idKor + " uspesno promenjeno mesto na " + promenaMesta.mesto);
    }

    private static SubsystemResponse dohvatiSveKorisnike(DohvatiKorisnike dohvatiKorisnike) {
         EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub1);
        EntityManager em = emf.createEntityManager();
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findAll", Korisnik.class).getResultList();
        StringBuilder allUsers = new StringBuilder();
        for(Korisnik k : korisnici){
            allUsers.append(k);
            allUsers.append("#");
        }
        em.clear();
        return new SubsystemResponse("Korisnici#" + allUsers);
    }

    private static SubsystemResponse dohvatiSvaMesta(DohvatiMesta dohvatiMesta) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub1);
        EntityManager em = emf.createEntityManager();
        List<Mesto> mesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
        StringBuilder allUsers = new StringBuilder();
        for(Mesto m : mesta){
            allUsers.append(m);
            allUsers.append("#");
        }
        em.clear();
        return new SubsystemResponse("Mesta#" + allUsers);
    }
    
}

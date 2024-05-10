/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzzzpodsistem2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Korisnik
 */
import entiteti.Kategorija;
import entiteti.Korisnik;
import entiteti.Snimak;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import java.util.Date;
/**
 *
 * @author Korisnik
 */
public class Podsistem2 {
    
    
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
    private static String persistenceUnitSub2 = "pu2";
    
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue2);
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
                    System.err.println("Not a request for subsystem2"); continue;
                }
                
                TypeOfRequest request = ((RequestForSubsystem)object).typeOfRequest();
                SubsystemResponse response = null;
                if(request == TypeOfRequest.KREIRANJE_KATEGORIJA){
                    response = kreiranjeKategorije((KreiranjeKategorije)object);
                }
                else if (request == TypeOfRequest.KREIRANJE_SNIMAK){
                    response = kreiranjeSnimka((KreiranjeSnimka)object);
                }
                else if (request == TypeOfRequest.PROMEA_NAZIV_SNIMAK){
                    response = promenaNazivaSnimka((PromenaNazivaSnimka)object);
                }
                else if (request == TypeOfRequest.DODAVANJE_KATEGORIJA_SNIMAK){
                    response = dodavanjeKategorijeSnimku((DodajKategorijuSnimku)object);
                }
                else if (request == TypeOfRequest.BRISANJE_SNIMAK){
                    response = brisanjeSnimka((ObrisiSnimak)object);
                }
                else if (request == TypeOfRequest.DOHVATANJE_KATEGORIJA){
                    response = dohvatiSveKategorije((DohvatiKategorije)object);
                }
                else if (request == TypeOfRequest.DOHVATANJE_KATEGORIJE_SNIMAK){
                    response = dohvatiKategorijeZaSnimak((DohvatiKategorijeZaSnimak)object);
                }
                else if (request == TypeOfRequest.UBACEN_NOVI_KORISNIK){
                    dodajKorisnika((UbacenNoviKorisnik)object);
                    continue;
                }
                else if (request == TypeOfRequest.KORISNIKU_PROMENJENO_MESTO){
                    azurirajMestoKorisnik((UAzurirajMestoKorisniku)object);
                    continue;
                }
                else if (request == TypeOfRequest.KORISNIKU_PROMENJEN_EMAIL){
                    azurirajEmailKorisnik((UAzurirajEmailKorisniku)object);
                    continue;
                }
                else if (request == TypeOfRequest.DOHVATANJE_SNIMAKA){
                    response = dohvatiSveSnimke((DohvatiSnimke)object);
                }
                ObjectMessage responseMsg = context.createObjectMessage(response);
                int property = ((RequestForSubsystem)object).myCode;
                responseMsg.setStringProperty("Code", ((Integer)property).toString() );
                producer.send(queueServer, responseMsg);
                
            } catch (JMSException ex) {
                Logger.getLogger(Podsistem2.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        
    }

    private static SubsystemResponse kreiranjeKategorije(KreiranjeKategorije kreiranjeKategorije) {
        String naziv = kreiranjeKategorije.naziv;
         EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub2);
        EntityManager em = emf.createEntityManager();
        List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).setParameter("naziv", naziv).getResultList();
        if(!kategorije.isEmpty()){
            return new SubsystemResponse("Kategorija " + naziv + " vec postoji");
        }
        
        Kategorija kategorija = new Kategorija();
        kategorija.setNaziv(naziv);
        em.getTransaction().begin();
        em.persist(kategorija);
        em.getTransaction().commit();
        em.clear();
        
        return new SubsystemResponse("Kategorija " + naziv + " je uspesno kreirana");
    }

    private static SubsystemResponse kreiranjeSnimka(KreiranjeSnimka kreiranjeSnimka) {
        try {
            int idKor = kreiranjeSnimka.idKor;
            String trajanje = kreiranjeSnimka.trajanje;
            String naziv = kreiranjeSnimka.naziv;
            String datum = kreiranjeSnimka.datum;
            String vreme = kreiranjeSnimka.vreme;
            vreme+=":00";
            DateFormat format = new SimpleDateFormat("hh:mm:ss");
            Date vremeDate = (Date)format.parse(vreme);
            
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Date datumDate = (Date)format2.parse(datum);
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub2);
            EntityManager em = emf.createEntityManager();
            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor", idKor).getResultList();
            if(korisnici.isEmpty()){
                return new SubsystemResponse("Korisnik nije kreiran");
            }
            Korisnik vlasnik = korisnici.get(0);
            Snimak snimak = new Snimak();
            snimak.setDatumPostavljanja(datumDate);
            snimak.setVremePostavljanja(vremeDate);
            snimak.setIdKor(vlasnik);
            snimak.setNaziv(naziv);
            snimak.setTrajanje(Integer.parseInt(trajanje));
            
            em.getTransaction().begin();
            em.persist(snimak);
            em.getTransaction().commit();
            
            List<Snimak> snimci = em.createNamedQuery("Snimak.findByNaziv", Snimak.class).setParameter("naziv", naziv).getResultList();
            Snimak dodat = snimci.get(0);
            
            UbacenNoviSnimak u = new UbacenNoviSnimak(dodat.getIdSni(), dodat.getIdKor().getIdKor(), dodat.getNaziv(),
            dodat.getTrajanje(), dodat.getDatumPostavljanja(), dodat.getVremePostavljanja());
            
            ObjectMessage responseMsg = context.createObjectMessage(u);
            producer.send(queue3, responseMsg);
            
            
            em.clear();
            
            return new SubsystemResponse("Snimak " + dodat + " je uspesno kreiran");
            
            
            
        } catch (ParseException ex) {
            Logger.getLogger(Podsistem2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new SubsystemResponse("Fatal error");
    }

    private static SubsystemResponse promenaNazivaSnimka(PromenaNazivaSnimka promenaNazivaSnimka) {
        int id = promenaNazivaSnimka.idSni;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub2);
            EntityManager em = emf.createEntityManager();
        List<Snimak> snimci = em.createNamedQuery("Snimak.findByIdSni", Snimak.class).setParameter("idSni", id).getResultList();
        if(snimci.isEmpty()){
            return new SubsystemResponse("Snimak sa id=" + id + " nije kreiran");
        }
        Snimak snimak = snimci.get(0);
        snimak.setNaziv(promenaNazivaSnimka.naziv);
        em.getTransaction().begin();
        em.persist(snimak);
        em.getTransaction().commit();
        
        UAzurirajNazivSnimka u = new UAzurirajNazivSnimka(promenaNazivaSnimka.idSni, promenaNazivaSnimka.naziv);
        
        ObjectMessage responseMsg = context.createObjectMessage(u);
        producer.send(queue3, responseMsg);
        
        em.clear();
        return new SubsystemResponse("Snimku " + snimak + " je uspesno promenjen naziv u " + promenaNazivaSnimka.naziv);
    }

    private static SubsystemResponse brisanjeSnimka(ObrisiSnimak obrisiSnimak) {
        int idKor = obrisiSnimak.idKor;
        
        String naziv = obrisiSnimak.naziv;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub2);
        EntityManager em = emf.createEntityManager();
        
        List<Snimak> snimci = em.createNamedQuery("Snimak.findByNaziv", Snimak.class).setParameter("naziv", naziv).getResultList();
        if(snimci.isEmpty()){
            return new SubsystemResponse("Pokusali ste da obrisete snimak koji ne postoji");
        }
        Snimak snimak = snimci.get(0);
        int idVla = snimak.getIdKor().getIdKor();
        if(idVla!=idKor){
            return new SubsystemResponse("Nije vam dozvoljeno brisanje snimka jer niste njegov vlasnik");
        }
        //List<Kategorija> kategorijeSnimka = snimak.get
        int idSni = snimak.getIdSni();
        em.getTransaction().begin();
        em.remove(snimak);
        em.getTransaction().commit();
        
        UObrisanSnimak u = new UObrisanSnimak(idSni);
        ObjectMessage responseMsg = context.createObjectMessage(u);
        producer.send(queue3, responseMsg);
        
        em.clear();
        
        return new SubsystemResponse("Uspesno ste uklonili snimak");
        
    }

    private static SubsystemResponse dodavanjeKategorijeSnimku(DodajKategorijuSnimku dodajKategorijuSnimku) {
        int idSni = dodajKategorijuSnimku.idSni;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub2);
        EntityManager em = emf.createEntityManager();
        
        List<Snimak>snimci = em.createNamedQuery("Snimak.findByIdSni", Snimak.class).setParameter("idSni", idSni).getResultList();
        if(snimci.isEmpty()){
            return new SubsystemResponse("Snimak nije kreiran");
        }
        
        Snimak snimak = snimci.get(0);
        List<Kategorija> kategorije  = em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).setParameter("naziv", dodajKategorijuSnimku.naziv).getResultList();
        if(kategorije.isEmpty()){
            Kategorija kategorija = new Kategorija();
            kategorija.setNaziv(dodajKategorijuSnimku.naziv);
            em.getTransaction().begin();
            em.persist(kategorija);
            em.getTransaction().commit();
        }
        List<Kategorija> kategorije2  = em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).setParameter("naziv", dodajKategorijuSnimku.naziv).getResultList();
        Kategorija dodeljena = kategorije2.get(0);
        
        int found = 0;
        for(Kategorija k: snimak.getKategorijaList()){
            if(k.getNaziv().equals(dodeljena.getNaziv())){
                found = 1; break;
            }
        }
        
        if(found == 1){
            return new SubsystemResponse("Snimku " + snimak + " je vec dodeljena kategorija " + dodeljena);
        }
        
        List<Snimak> dodeljenaSnimcima = dodeljena.getSnimakList();
        if(dodeljenaSnimcima==null){
            dodeljenaSnimcima = new ArrayList<>();
        }
        
        dodeljenaSnimcima.add(snimak);
        dodeljena.setSnimakList(dodeljenaSnimcima);
        
        List<Kategorija> kategorijeSnimka = snimak.getKategorijaList();
        if(kategorijeSnimka ==null){
            kategorijeSnimka = new ArrayList<>();
        }
        kategorijeSnimka.add(dodeljena);
        snimak.setKategorijaList(kategorijeSnimka);
        em.getTransaction().begin();
        em.persist(dodeljena);
        //em.persist(snimak);
        em.getTransaction().commit();
        em.clear();
        
        return new SubsystemResponse("Snimku " + snimak + " je uspesno dodata kategorija " + dodeljena);
        
    }

    private static SubsystemResponse dohvatiSveKategorije(DohvatiKategorije dohvatiKategorije) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub2);
        EntityManager em = emf.createEntityManager();
        List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findAll", Kategorija.class).getResultList();
        StringBuilder all = new StringBuilder();
        for(Kategorija k : kategorije){
            all.append(k);
            all.append("#");
        }
        em.clear();
        return new SubsystemResponse("Kategorije#" + all);
    }

    private static SubsystemResponse dohvatiSveSnimke(DohvatiSnimke dohvatiSnimke) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub2);
        EntityManager em = emf.createEntityManager();
        List<Snimak> snimci = em.createNamedQuery("Snimak.findAll", Snimak.class).getResultList();
        StringBuilder all = new StringBuilder();
        for(Snimak k : snimci){
            all.append(k);
            all.append("#");
        }
        em.clear();
        return new SubsystemResponse("Snimci#" + all);
    }

    private static SubsystemResponse dohvatiKategorijeZaSnimak(DohvatiKategorijeZaSnimak dohvatiKategorijeZaSnimak) {
        int idSni = dohvatiKategorijeZaSnimak.idSni;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub2);
        EntityManager em = emf.createEntityManager();
        List<Snimak> snimci = em.createNamedQuery("Snimak.findByIdSni", Snimak.class).setParameter("idSni", idSni).getResultList();
        if(snimci.isEmpty()){
            return new SubsystemResponse("Snimak sa prosledjenim ID ne postoji");
        }
        StringBuilder all = new StringBuilder();
        Snimak snimak = snimci.get(0);
        for(Kategorija k : snimak.getKategorijaList()){
            all.append(k);
            all.append("#");
        }
        em.clear();
        return new SubsystemResponse("Kategorije snimka: " + snimak + "#" + all);
    }

    private static void dodajKorisnika(UbacenNoviKorisnik ubacenNoviKorisnik) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub2);
        EntityManager em = emf.createEntityManager();
        String ime = ubacenNoviKorisnik.ime;
        int idKor = ubacenNoviKorisnik.idKor;
        int idMes = ubacenNoviKorisnik.idMes;
        Character pol = ubacenNoviKorisnik.pol;
        String godiste = ubacenNoviKorisnik.godiste;
        String email = ubacenNoviKorisnik.email;
        Korisnik korisnik = new Korisnik();
        korisnik.setIdKor(idKor);
        korisnik.setEmail(email);
        korisnik.setIdMes(idMes);
        korisnik.setGodiste(godiste);
        korisnik.setIme(ime);
        korisnik.setPol(pol);
        em.getTransaction().begin();
        em.persist(korisnik);
        em.getTransaction().commit();
        em.clear();
    }

    private static void azurirajMestoKorisnik(UAzurirajMestoKorisniku uAzurirajMestoKorisniku) {
        int idKor = uAzurirajMestoKorisniku.idKor;
        int idMes = uAzurirajMestoKorisniku.idMes;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub2);
        EntityManager em = emf.createEntityManager();
        List<Korisnik>korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor", idKor).getResultList();
        Korisnik korisnik = korisnici.get(0);
        korisnik.setIdMes(idMes);
        em.getTransaction().begin();
        em.persist(korisnik);
        em.getTransaction().commit();
        em.clear();
    }

    private static void azurirajEmailKorisnik(UAzurirajEmailKorisniku uAzurirajEmailKorisniku) {   
        int idKor = uAzurirajEmailKorisniku.idKor;
        String email = uAzurirajEmailKorisniku.email;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub2);
        EntityManager em = emf.createEntityManager();
        List<Korisnik>korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor", idKor).getResultList();
        Korisnik korisnik = korisnici.get(0);
        korisnik.setEmail(email);
        em.getTransaction().begin();
        em.persist(korisnik);
        em.getTransaction().commit();
        em.clear();
    }

    
    
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzzzpodsistem3;

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
import entiteti.*;
import java.time.LocalDate;
import java.time.ZoneId;
/**
 *
 * @author Korisnik
 */
public class Podsistem3 {
    
    
    @Resource(lookup="queue3")
    private static Queue queue3;
     
    @Resource(lookup="queueServer")
    private static Queue queueServer;
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    private static JMSProducer producer;
    private static JMSContext context;
    private static String persistenceUnitSub3 = "pu3";
    
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue3);
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
                if(request == TypeOfRequest.KREIRAJ_PAKET){
                    response = kreiranjePaketa((KreiranjePaketa)object);
                }
                else if (request == TypeOfRequest.PROMENA_CENA_PAKET){
                    response = promenaCenePaketa((PromenaMesecneCenePaketa)object);
                }
                else if (request == TypeOfRequest.KREIRANJE_PRETPLATA_KORISNIK_PAKET){
                    response = kreirajPretplatuKorisnikaNaPaket((KreiranjePretplateKorisnika)object);
                }
                else if (request == TypeOfRequest.KREIRANJE_GLEDANJE_SNIMAK_KORISNIK){
                    response = kreirajGledanjeSnimkaKorisnika((KreiranjeGledanjaSnimka)object);
                }
                else if (request == TypeOfRequest.KREIRANJE_OCENA_SNIMAK_KORISNIK){
                    response = dodeliOcenuSnimku((KreiranjeOceneSnimka)object);
                }
                else if (request == TypeOfRequest.PROMENA_OCENA_SNIMAK_KORISNIK){
                    response = promeniOcenuSnimkuOdKorisnika((MenjanjeOcenSnimkaOdKorisnika)object);
                }
                else if (request == TypeOfRequest.BRISANJE_OCENA_KORISNIK_SNIMAK){
                    response = brisanjeOceneSnimkaOdKorisnika((BrisanjeOceneKorisnikaZaSnimak)object);
                }
                else if (request == TypeOfRequest.DOHVATANJE_PAKETI){
                    response = dohvatiSvePakete((DohvatiPakete)object);
                }
                else if (request == TypeOfRequest.DOHVATANJE_PRETPLATA_KORISNIK){
                    response = dohvatiPretplateZaKorisnika((DohvatiPretplateZaKorisnika)object);
                }
                else if (request == TypeOfRequest.DOHVATANJE_GLEDANJE_SNIMAK){
                    response = dohvatiGledanjaZaSnimak((DohvatiGledanjaZaVideoSnimak)object);
                }
                else if (request == TypeOfRequest.DOHVATANJE_OCENE_SNIMAK){
                    response = dohvatiOceneZaSnimak((DohvatiOceneZaSnimak)object);
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
                else if (request == TypeOfRequest.UBACEN_NOVI_SNIMAK){
                    dodajSnimak((UbacenNoviSnimak)object);
                    continue;
                }
                else if (request == TypeOfRequest.SNIMAK_JE_OBRISAN){
                    ukloniSnimak((UObrisanSnimak)object);
                    continue;
                }
                else if (request == TypeOfRequest.SNIMKU_PROMENJEN_NAZIV){
                    azurirajNazivSnimka((UAzurirajNazivSnimka)object);
                    continue;
                }
                
                ObjectMessage responseMsg = context.createObjectMessage(response);
                int property = ((RequestForSubsystem)object).myCode;
                responseMsg.setStringProperty("Code", ((Integer)property).toString() );
                producer.send(queueServer, responseMsg);
                
            } catch (JMSException ex) {
                Logger.getLogger(Podsistem3.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        
    }

   

    private static void dodajKorisnika(UbacenNoviKorisnik ubacenNoviKorisnik) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        List<Korisnik>korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor", idKor).getResultList();
        Korisnik korisnik = korisnici.get(0);
        korisnik.setEmail(email);
        em.getTransaction().begin();
        em.persist(korisnik);
        em.getTransaction().commit();
        em.clear();
    }

    private static SubsystemResponse kreiranjePaketa(KreiranjePaketa kreiranjePaketa) {
         EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        
        String nazivPaketa = kreiranjePaketa.naziv;
        List<Paket> paketi = em.createNamedQuery("Paket.findByNaziv", Paket.class).setParameter("naziv",nazivPaketa).getResultList();
        if(!paketi.isEmpty()){
            return new SubsystemResponse("Paket sa prosledjenim nazivom je vec kreira");
        }
        
        Paket paket = new Paket();
        paket.setCenaNaMesNivou(kreiranjePaketa.mesecniIznos);
        paket.setNaziv(nazivPaketa);
        em.getTransaction().begin();
        em.persist(paket);
        em.getTransaction().commit();
        em.clear();
        
        return new SubsystemResponse("Paket " + paket + " je uspesno kreiran");
    }

    private static SubsystemResponse promenaCenePaketa(PromenaMesecneCenePaketa promenaMesecneCenePaketa) {
        int novaCena = promenaMesecneCenePaketa.mesCena;
        String nazivPaketa = promenaMesecneCenePaketa.naziv;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        List<Paket> paketi = em.createNamedQuery("Paket.findByNaziv", Paket.class).setParameter("naziv",nazivPaketa).getResultList();
        if(paketi.isEmpty()){
            return new SubsystemResponse("Pokusavate da promeni cenu paketu koji ne postoji");
        }
        Paket paket = paketi.get(0);
        paket.setCenaNaMesNivou(novaCena);
        em.getTransaction().begin();
        em.persist(paket);
        em.getTransaction().commit();
        em.clear();
        
        return new SubsystemResponse("Paketu " + paket + " je uspesno promenjen mesecni iznos");
    }

    private static SubsystemResponse kreirajPretplatuKorisnikaNaPaket(KreiranjePretplateKorisnika kreiranjePretplateKorisnika) {
        int idKor = kreiranjePretplateKorisnika.idKor;
        String nazivPaketa = kreiranjePretplateKorisnika.nazivPaketa;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor",idKor).getResultList();
        if(korisnici.isEmpty()){
            return new SubsystemResponse("Korisnik nije kreiran");
        }
        Korisnik korisnik = korisnici.get(0);
        List<Paket> paketi = em.createNamedQuery("Paket.findByNaziv", Paket.class).setParameter("naziv",nazivPaketa).getResultList();
        if(paketi.isEmpty()){
            return new SubsystemResponse("Paket ne postoji");
        }
        Paket paket = paketi.get(0);
        Date currentDate = new Date();
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd"); Date date = null;
        
        String formatted = format.format(currentDate);
        
        try {
             date = (Date)format.parse(formatted);
        } catch (ParseException ex) {
            Logger.getLogger(Podsistem3.class.getName()).log(Level.SEVERE, null, ex);
        }
        format = new SimpleDateFormat("hh:mm:ss"); Date time = null;
        formatted = format.format(currentDate);
        try {
             time = (Date)format.parse(formatted);
        } catch (ParseException ex) {
            Logger.getLogger(Podsistem3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int found = 0;
        for (Pretplata p:korisnik.getPretplataList()){
            Pretplata pret = p;
            Date datumPoc = pret.getDatumPocetka();
            LocalDate localDate1 = datumPoc.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDate2 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            LocalDate localDate1PlusOneMonth = localDate1.plusMonths(1);
            if (localDate1PlusOneMonth.isAfter(localDate2)) {
                found = 1; break;
            }
        }
        if(found==1){
            return new SubsystemResponse("Korisnik: " + korisnik + " ima vec aktivnu pretplatu");
        }
        Pretplata pretplata = new Pretplata();
        pretplata.setCena(paket.getCenaNaMesNivou());
        pretplata.setDatumPocetka(date);
        pretplata.setVremePocetka(time);
        pretplata.setIdKor(korisnik);
        pretplata.setIdPak(paket);
        em.getTransaction().begin();
        em.persist(pretplata);
        em.getTransaction().commit();
        em.clear();
        
        return new SubsystemResponse("Pretplata " + pretplata + " je uspesno kreirana");
        
    }

    private static SubsystemResponse kreirajGledanjeSnimkaKorisnika(KreiranjeGledanjaSnimka kreiranjeGledanjaSnimka) {
       int idKor = kreiranjeGledanjaSnimka.IdKor;
       int idSni = kreiranjeGledanjaSnimka.IdSni;
 
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor",idKor).getResultList();
        if(korisnici.isEmpty()){
            return new SubsystemResponse("Korisnik nije kreiran");
        }
        Korisnik korisnik = korisnici.get(0);
        List<Snimak> snimci = em.createNamedQuery("Snimak.findByIdSni", Snimak.class).setParameter("idSni",idSni).getResultList();
        if(snimci.isEmpty()){
            return new SubsystemResponse("Snimak nije kreiran");
        }
        Snimak snimak = snimci.get(0);
        Date currentDate = new Date();
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd"); Date date = null;
        
        String formatted = format.format(currentDate);
        
        try {
             date = (Date)format.parse(formatted);
        } catch (ParseException ex) {
            Logger.getLogger(Podsistem3.class.getName()).log(Level.SEVERE, null, ex);
        }
        format = new SimpleDateFormat("hh:mm:ss"); Date time = null;
        formatted = format.format(currentDate);
        try {
             time = (Date)format.parse(formatted);
        } catch (ParseException ex) {
            Logger.getLogger(Podsistem3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Gledanje gledanje = new Gledanje();
        gledanje.setDatumPocetka(date);
        gledanje.setIdKor(korisnik);
        gledanje.setIdSni(snimak);
        gledanje.setSekundOdPocetka(Integer.parseInt(kreiranjeGledanjaSnimka.sekundiOdPocetka));
        gledanje.setSekundiOdgledano(Integer.parseInt(kreiranjeGledanjaSnimka.sekundiOdgledano));
        gledanje.setVremePocetka(time);
         em.getTransaction().begin();
        em.persist(gledanje);
        em.getTransaction().commit();
        em.clear();
        return new SubsystemResponse("Gledanje " + gledanje + " je uspesno kreirano");
    }

    private static SubsystemResponse dodeliOcenuSnimku(KreiranjeOceneSnimka kreiranjeOceneSnimka) {
        int idKor = kreiranjeOceneSnimka.idKor;
       int idSni = kreiranjeOceneSnimka.idSni;
 
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor",idKor).getResultList();
        if(korisnici.isEmpty()){
            return new SubsystemResponse("Korisnik nije kreiran");
        }
        Korisnik korisnik = korisnici.get(0);
        List<Snimak> snimci = em.createNamedQuery("Snimak.findByIdSni", Snimak.class).setParameter("idSni",idSni).getResultList();
        if(snimci.isEmpty()){
            return new SubsystemResponse("Snimak nije kreiran");
        }
        Snimak snimak = snimci.get(0);
        Date currentDate = new Date();
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd"); Date date = null;
        
        String formatted = format.format(currentDate);
        
        try {
             date = (Date)format.parse(formatted);
        } catch (ParseException ex) {
            Logger.getLogger(Podsistem3.class.getName()).log(Level.SEVERE, null, ex);
        }
        format = new SimpleDateFormat("hh:mm:ss"); Date time = null;
        formatted = format.format(currentDate);
        try {
             time = (Date)format.parse(formatted);
        } catch (ParseException ex) {
            Logger.getLogger(Podsistem3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Ocena ocena = new Ocena();
        ocena.setDatum(date);
        ocena.setIdKor(korisnik);
        ocena.setIdSni(snimak);
        ocena.setOcena(Integer.parseInt(kreiranjeOceneSnimka.ocena));
        ocena.setVreme(time);
         em.getTransaction().begin();
        em.persist(ocena);
        em.getTransaction().commit();
        em.clear();
        return new SubsystemResponse("Ocena " + ocena + " je uspesno kreirana");
    }

    private static SubsystemResponse promeniOcenuSnimkuOdKorisnika(MenjanjeOcenSnimkaOdKorisnika menjanjeOcenSnimkaOdKorisnika) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        int idKor = menjanjeOcenSnimkaOdKorisnika.idKor;
        int idSni = menjanjeOcenSnimkaOdKorisnika.idSni;
        
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor",idKor).getResultList();
        if(korisnici.isEmpty()){
            return new SubsystemResponse("Korisnik nije kreiran");
        }
        Korisnik korisnik = korisnici.get(0);
        List<Snimak> snimci = em.createNamedQuery("Snimak.findByIdSni", Snimak.class).setParameter("idSni",idSni).getResultList();
        if(snimci.isEmpty()){
            return new SubsystemResponse("Snimak nije kreiran");
        }
        Snimak snimak = snimci.get(0);
        Ocena ocena = null;
        for (Ocena o: snimak.getOcenaList()){
            if((o.getIdKor().getIdKor()) == (korisnik.getIdKor())){
                ocena = o; break;
            }
        }
        if(ocena==null){
            return new SubsystemResponse("Korisnik nije ranije ocenjivao film");
        }
        ocena.setOcena(Integer.parseInt(menjanjeOcenSnimkaOdKorisnika.novaOcena));
        em.getTransaction().begin();
        em.persist(ocena);
        em.getTransaction().commit();
        em.clear();
        return new SubsystemResponse("Uspesno je promenjena ocena " + ocena);
    }

    private static SubsystemResponse brisanjeOceneSnimkaOdKorisnika(BrisanjeOceneKorisnikaZaSnimak brisanjeOceneKorisnikaZaSnimak) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        int idKor = brisanjeOceneKorisnikaZaSnimak.idKor;
        int idSni = brisanjeOceneKorisnikaZaSnimak.idSni;
        
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor",idKor).getResultList();
        if(korisnici.isEmpty()){
            return new SubsystemResponse("Korisnik nije kreiran");
        }
        Korisnik korisnik = korisnici.get(0);
        List<Snimak> snimci = em.createNamedQuery("Snimak.findByIdSni", Snimak.class).setParameter("idSni",idSni).getResultList();
        if(snimci.isEmpty()){
            return new SubsystemResponse("Snimak nije kreiran");
        }
        Snimak snimak = snimci.get(0);
        Ocena ocena = null;
        for (Ocena o: snimak.getOcenaList()){
            if((o.getIdKor().getIdKor()) == (korisnik.getIdKor())){
                ocena = o; break;
            }
        }
        if(ocena==null){
            return new SubsystemResponse("Korisnik nije ranije ocenjivao film");
        }
        List<Ocena> ocene = snimak.getOcenaList();
        ocene.remove(ocena);
        snimak.setOcenaList(ocene);
        em.getTransaction().begin();
        em.remove(ocena);
        em.persist(snimak);
        em.getTransaction().commit();
        em.clear();
        return new SubsystemResponse("Uspesno izbrisana ocena");
    }

    private static SubsystemResponse dohvatiSvePakete(DohvatiPakete dohvatiPakete) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        List<Paket> paketi = em.createNamedQuery("Paket.findAll", Paket.class).getResultList();
        StringBuilder allPack = new StringBuilder();
        for(Paket p: paketi){
            allPack.append(p).append("#");
        }
        em.clear();
        return new SubsystemResponse("Paketi:#" + allPack);
    }

    private static SubsystemResponse dohvatiPretplateZaKorisnika(DohvatiPretplateZaKorisnika dohvatiPretplateZaKorisnika) {
        int idKor = dohvatiPretplateZaKorisnika.idKor;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor", idKor).getResultList();
        if(korisnici.isEmpty()) return new SubsystemResponse("Korisnik nije kreiran");
        Korisnik korisnik = korisnici.get(0);
        List<Pretplata> pretplate = korisnik.getPretplataList();
        StringBuilder allPack = new StringBuilder();
        for(Pretplata p: pretplate){
            allPack.append(p).append("#");
        }
        em.clear();
        return new SubsystemResponse("Pretplate korisnika:#" + allPack);
    
    }

    private static SubsystemResponse dohvatiGledanjaZaSnimak(DohvatiGledanjaZaVideoSnimak dohvatiGledanjaZaVideoSnimak) {
        int idSni = dohvatiGledanjaZaVideoSnimak.idSni;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        List<Snimak> snimci = em.createNamedQuery("Snimak.findByIdSni", Snimak.class).setParameter("idSni", idSni).getResultList();
        if(snimci.isEmpty()){ return new SubsystemResponse("Snimak sa ovim ID ne postoji");}
        Snimak snimak = snimci.get(0);
        List<Gledanje> gledanja = snimak.getGledanjeList();
        StringBuilder allPack = new StringBuilder();
        for(Gledanje g: gledanja){
            allPack.append(g).append("#");
        }
        em.clear();
        return new SubsystemResponse("Gledanja snimka:#" + allPack);
    }

    private static SubsystemResponse dohvatiOceneZaSnimak(DohvatiOceneZaSnimak dohvatiOceneZaSnimak) {
         int idSni = dohvatiOceneZaSnimak.idSni;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        List<Snimak> snimci = em.createNamedQuery("Snimak.findByIdSni", Snimak.class).setParameter("idSni", idSni).getResultList();
        if(snimci.isEmpty()){ return new SubsystemResponse("Snimak sa ovim ID ne postoji");}
        Snimak snimak = snimci.get(0);
        List<Ocena> ocene = snimak.getOcenaList();
        StringBuilder allPack = new StringBuilder();
        for(Ocena g: ocene){
            allPack.append(g).append("#");
        }
        em.clear();
        return new SubsystemResponse("Ocene snimka:#" + allPack);
    }

    private static void dodajSnimak(UbacenNoviSnimak ubacenNoviSnimak) {
        Snimak snimak = new Snimak();
        snimak.setDatumPostavljanja(ubacenNoviSnimak.datumPostavljanja);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdKor", Korisnik.class).setParameter("idKor", ubacenNoviSnimak.idKor).getResultList();
        if(korisnici.isEmpty()) return;
        Korisnik korisnik = korisnici.get(0);
        
        snimak.setIdKor(korisnik);
        snimak.setNaziv(ubacenNoviSnimak.naziv);
        snimak.setTrajanje(ubacenNoviSnimak.trajanje);
        snimak.setVremePostavljanja(ubacenNoviSnimak.vremePostavljanja);
        
        em.getTransaction().begin();
        em.persist(snimak);
        em.getTransaction().commit();
        em.clear();
        
    }

    private static void ukloniSnimak(UObrisanSnimak uObrisanSnimak) {
        
        int idSni = uObrisanSnimak.idSni;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        List<Snimak> snimci = em.createNamedQuery("Snimak.findByIdSni", Snimak.class).setParameter("idSni", idSni).getResultList();
        if(snimci.isEmpty()) return;
        Snimak snimak = snimci.get(0);

        em.getTransaction().begin();
        em.remove(snimak);
        em.getTransaction().commit();
        em.clear();
    }

    private static void azurirajNazivSnimka(UAzurirajNazivSnimka uAzurirajNazivSnimka) {
       int idSni = uAzurirajNazivSnimka.idSni;
       String noviNaziv = uAzurirajNazivSnimka.naziv;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitSub3);
        EntityManager em = emf.createEntityManager();
        List<Snimak> snimci = em.createNamedQuery("Snimak.findByIdSni", Snimak.class).setParameter("idSni", idSni).getResultList();
        if(snimci.isEmpty()) return;
        Snimak snimak = snimci.get(0);
        snimak.setNaziv(noviNaziv);
        em.getTransaction().begin();
        em.persist(snimak);
        em.getTransaction().commit();
        em.clear();
    }

    
    
}

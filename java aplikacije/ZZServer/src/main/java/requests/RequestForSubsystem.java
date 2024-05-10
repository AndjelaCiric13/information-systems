/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import java.io.Serializable;

/**
 *
 * @author Korisnik
 */
public class RequestForSubsystem implements Serializable {
    public enum TypeOfSubsystem {S1, S2, S3, S123, S23};
    
    public enum TypeOfRequest{
        KREIRANJE_MESTO,
        KREIRANJE_KORISNIK,
        PROMENA_EMAIL_KORISNIK,
        PROMENA_MESTO_KORISNIK,
        KREIRANJE_KATEGORIJA,
        KREIRANJE_SNIMAK,
        PROMEA_NAZIV_SNIMAK,
        DODAVANJE_KATEGORIJA_SNIMAK,
        KREIRAJ_PAKET,
        PROMENA_CENA_PAKET,
        KREIRANJE_PRETPLATA_KORISNIK_PAKET,
        KREIRANJE_GLEDANJE_SNIMAK_KORISNIK,
        KREIRANJE_OCENA_SNIMAK_KORISNIK,
        PROMENA_OCENA_SNIMAK_KORISNIK,
        BRISANJE_OCENA_KORISNIK_SNIMAK,
        BRISANJE_SNIMAK,
        DOHVATANJE_MESTA,
        DOHVATANJE_KORISNIKA,
        DOHVATANJE_KATEGORIJA,
        DOHVATANJE_SNIMAKA,
        DOHVATANJE_KATEGORIJE_SNIMAK,
        DOHVATANJE_PAKETI,
        DOHVATANJE_PRETPLATA_KORISNIK,
        DOHVATANJE_GLEDANJE_SNIMAK,
        DOHVATANJE_OCENE_SNIMAK,
        UBACEN_NOVI_KORISNIK,
        KORISNIKU_PROMENJEN_EMAIL,
        KORISNIKU_PROMENJENO_MESTO
    };
    
    public static int code = 0;
    public TypeOfSubsystem subsystem;
    public TypeOfRequest request;
    
    public int myCode = ++code;
    
    public RequestForSubsystem(){}
    
    public TypeOfSubsystem typeOfSubsystem(){
        return this.subsystem;
    }
    
    public TypeOfRequest typeOfRequest(){
        return this.request;
    }
}

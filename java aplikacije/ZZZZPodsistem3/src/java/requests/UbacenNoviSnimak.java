/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import java.util.Date;

/**
 *
 * @author Korisnik
 */
public class UbacenNoviSnimak extends RequestForSubsystem {
    public int idSni;
    public int idKor;
    public String naziv;
    public int trajanje;
    public Date datumPostavljanja;
    public Date vremePostavljanja;

    public UbacenNoviSnimak(int idSni, int idKor, String naziv, int trajanje, Date datumPostavljanja, Date vremePostavljanja) {
        this.request = TypeOfRequest.UBACEN_NOVI_SNIMAK;
        this.subsystem = TypeOfSubsystem.S3;
        this.idSni = idSni;
        this.idKor = idKor;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datumPostavljanja = datumPostavljanja;
        this.vremePostavljanja = vremePostavljanja;
    }
    
    
}

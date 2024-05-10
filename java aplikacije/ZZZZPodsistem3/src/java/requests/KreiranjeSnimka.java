/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

/**
 *
 * @author Korisnik
 */
public class KreiranjeSnimka extends RequestForSubsystem{
    public int idKor;
    public String naziv;
    public String trajanje;
    public String datum;
    public String vreme;

    public KreiranjeSnimka(int idKor, String naziv, String trajanje, String datum, String vreme) {
        this.request = TypeOfRequest.KREIRANJE_SNIMAK;
        this.subsystem = TypeOfSubsystem.S23;
        this.idKor = idKor;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datum = datum;
        this.vreme = vreme;
    }
    
    
}

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
public class KreiranjeKategorije extends RequestForSubsystem {
    
    public String naziv;
    
    public KreiranjeKategorije(String naziv) {
        this.subsystem = TypeOfSubsystem.S2;
        this.request = TypeOfRequest.KREIRANJE_KATEGORIJA;
        this.naziv = naziv;
    }
    
}

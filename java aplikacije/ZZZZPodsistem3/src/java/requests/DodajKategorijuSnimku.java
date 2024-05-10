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
public class DodajKategorijuSnimku extends RequestForSubsystem {
    public int idSni;
    public String naziv;

    public DodajKategorijuSnimku(int idSni, String naziv) {
        this.request = TypeOfRequest.DODAVANJE_KATEGORIJA_SNIMAK;
        this.subsystem = TypeOfSubsystem.S2;
        this.idSni = idSni;
        this.naziv = naziv;
    }
    
    
    
}

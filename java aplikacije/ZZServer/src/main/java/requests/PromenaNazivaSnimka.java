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
public class PromenaNazivaSnimka extends RequestForSubsystem {
    
    public int idSni;
    public String naziv;

    public PromenaNazivaSnimka(int idSni, String naziv) {
        this.request = TypeOfRequest.PROMEA_NAZIV_SNIMAK;
        this.subsystem = TypeOfSubsystem.S23;
        this.idSni = idSni;
        this.naziv = naziv;
    }
    
    
    
}

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
public class DohvatiKategorijeZaSnimak extends RequestForSubsystem {
    public int idSni;
    
    public DohvatiKategorijeZaSnimak(int idSni){
        this.idSni = idSni;
        this.subsystem = TypeOfSubsystem.S2;
        this.request = TypeOfRequest.DOHVATANJE_KATEGORIJE_SNIMAK;
    }
    
    
    
}

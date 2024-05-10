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
public class ObrisiSnimak extends RequestForSubsystem {
    public int idKor;
    public String naziv;

    public ObrisiSnimak(int idKor, String naziv) {
        this.subsystem = TypeOfSubsystem.S23;
        this.request = TypeOfRequest.BRISANJE_SNIMAK;
        this.idKor = idKor;
        this.naziv = naziv;
    }
    
    
}

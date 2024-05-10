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
public class KreiranjePaketa extends RequestForSubsystem {
    
    public String naziv;
    public int mesecniIznos;

    public KreiranjePaketa(String naziv, int mesecniIznos) {
        this.naziv = naziv;
        this.mesecniIznos = mesecniIznos;
        this.subsystem = TypeOfSubsystem.S3;
        this.request = TypeOfRequest.KREIRAJ_PAKET;
    }
    
    
    
}

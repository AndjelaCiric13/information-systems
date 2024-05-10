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
public class DohvatiGledanjaZaVideoSnimak extends RequestForSubsystem {
    
    public int idSni;
    
    public DohvatiGledanjaZaVideoSnimak(int idSni){
        this.idSni = idSni;
        this.subsystem = TypeOfSubsystem.S3;
        this.request = TypeOfRequest.DOHVATANJE_GLEDANJE_SNIMAK;
    }
}

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
public class KreiranjePretplateKorisnika extends RequestForSubsystem {
    
    public int idKor;
    public String nazivPaketa;

    public KreiranjePretplateKorisnika(int idKor, String nazivPaketa) {
        this.idKor = idKor;
        this.nazivPaketa = nazivPaketa;
        this.subsystem = TypeOfSubsystem.S3;
        this.request = TypeOfRequest.KREIRANJE_PRETPLATA_KORISNIK_PAKET;
    }
    
}

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
public class BrisanjeOceneKorisnikaZaSnimak extends RequestForSubsystem {
    public int idKor;
    public int idSni;

    public BrisanjeOceneKorisnikaZaSnimak(int idKor, int idSni) {
        this.idKor = idKor;
        this.idSni = idSni;
        this.subsystem = TypeOfSubsystem.S3;
        this.request = TypeOfRequest.BRISANJE_OCENA_KORISNIK_SNIMAK;
    }
    
    
}

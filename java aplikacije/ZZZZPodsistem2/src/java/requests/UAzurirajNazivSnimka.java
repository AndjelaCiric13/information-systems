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
public class UAzurirajNazivSnimka extends RequestForSubsystem {
    public int idSni;
    public String naziv;

    public UAzurirajNazivSnimka(int idSni, String naziv) {
        this.idSni = idSni;
        this.naziv = naziv;
        this.subsystem = TypeOfSubsystem.S3;
        this.request = TypeOfRequest.SNIMKU_PROMENJEN_NAZIV;
    }
    
    
}

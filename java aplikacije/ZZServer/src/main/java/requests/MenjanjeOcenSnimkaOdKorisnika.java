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
public class MenjanjeOcenSnimkaOdKorisnika extends RequestForSubsystem {
    
    public int idKor;
    public int idSni;
    public String novaOcena;

    public MenjanjeOcenSnimkaOdKorisnika(int idKor, int idSni, String novaOcena) {
        this.idKor = idKor;
        this.idSni = idSni;
        this.novaOcena = novaOcena;
        this.subsystem = TypeOfSubsystem.S3;
        this.request = TypeOfRequest.PROMENA_OCENA_SNIMAK_KORISNIK;
    }
    
    
    
}

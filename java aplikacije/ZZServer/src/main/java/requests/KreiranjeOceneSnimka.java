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
public class KreiranjeOceneSnimka extends RequestForSubsystem {
    
    public int idKor;
    public int idSni;
    public String ocena;
    public String datumOcene;
    public String vremeOcene;

    public KreiranjeOceneSnimka(int idKor, int idSni, String ocena, String datumOcene, String vremeOcene) {
        this.idKor = idKor;
        this.idSni = idSni;
        this.ocena = ocena;
        this.datumOcene = datumOcene;
        this.vremeOcene = vremeOcene;
        this.subsystem = TypeOfSubsystem.S3;
        this.request = TypeOfRequest.KREIRANJE_OCENA_SNIMAK_KORISNIK;
    }
    
    
    
}

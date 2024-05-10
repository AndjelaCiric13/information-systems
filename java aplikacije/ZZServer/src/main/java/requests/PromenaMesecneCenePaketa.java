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
public class PromenaMesecneCenePaketa extends RequestForSubsystem {
    
    public int mesCena;
    public String naziv;

    public PromenaMesecneCenePaketa(int idPak, String naziv) {
        this.mesCena = idPak;
        this.naziv = naziv;
        this.subsystem = TypeOfSubsystem.S3;
        this.request = TypeOfRequest.PROMENA_CENA_PAKET;
    }
    
    
}

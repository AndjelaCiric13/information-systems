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
public class KreiranjeKorisnika extends RequestForSubsystem {
    public String ime;
    public String email;
    public int godiste;
    public char pol;
    public String idMes;
    
    public KreiranjeKorisnika(String ime, String email, int godiste, char pol, String idMes) {
        this.subsystem = RequestForSubsystem.TypeOfSubsystem.S123;
        this.request = RequestForSubsystem.TypeOfRequest.KREIRANJE_KORISNIK;
        this.ime = ime;
        this.email = email;
        this.godiste = godiste;
        this.pol = pol;
        this.idMes = idMes;
    }
}

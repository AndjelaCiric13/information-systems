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
public class KreiranjeGledanjaSnimka extends RequestForSubsystem {
    
    public int IdKor;
    public int IdSni;
    public String datumPocetka;
    public String vremePocetka;
    public String sekundiOdPocetka;
    public String sekundiOdgledano;

    public KreiranjeGledanjaSnimka(int IdKor, int IdSni, String datumPocetka, String vremePocetka, String sekundiOdPocetka, String sekundiOdgledano) {
        this.IdKor = IdKor;
        this.IdSni = IdSni;
        this.datumPocetka = datumPocetka;
        this.vremePocetka = vremePocetka;
        this.sekundiOdPocetka = sekundiOdPocetka;
        this.sekundiOdgledano = sekundiOdgledano;
        this.subsystem = TypeOfSubsystem.S3;
        this.request = TypeOfRequest.KREIRANJE_GLEDANJE_SNIMAK_KORISNIK;
    }
    
    
    
}

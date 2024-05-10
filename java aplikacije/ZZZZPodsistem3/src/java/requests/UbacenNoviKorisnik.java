/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import entiteti.Korisnik;

/**
 *
 * @author Korisnik
 */
public class UbacenNoviKorisnik extends RequestForSubsystem {
    
    public int idKor;
    public String ime;
    public String email;
    public String godiste;
    public Character pol;
    public int idMes;
    
    public UbacenNoviKorisnik( int idKor, String ime, String email, String godiste, Character pol, int idMes){
    this.request = TypeOfRequest.UBACEN_NOVI_KORISNIK;
    this.idKor = idKor;
    this.ime = ime;
    this.email = email;
    this.godiste = godiste;
    this.pol = pol;
    this.idMes = idMes;
    }
    
}

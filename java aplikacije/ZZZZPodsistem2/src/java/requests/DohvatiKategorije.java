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
public class DohvatiKategorije extends RequestForSubsystem {
    
    public DohvatiKategorije(){
        this.subsystem = TypeOfSubsystem.S2;
        this.request = TypeOfRequest.DOHVATANJE_KATEGORIJA;
    }
    
}

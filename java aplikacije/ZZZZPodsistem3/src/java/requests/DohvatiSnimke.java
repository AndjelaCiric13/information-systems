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
public class DohvatiSnimke extends RequestForSubsystem {
    
    public DohvatiSnimke(){
        this.subsystem = TypeOfSubsystem.S2;
        this.request = TypeOfRequest.DOHVATANJE_SNIMAKA;
    }
}

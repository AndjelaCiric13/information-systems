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
public class DohvatiPakete extends RequestForSubsystem {
    
    public DohvatiPakete(){
        this.subsystem = TypeOfSubsystem.S3;
        this.request = TypeOfRequest.DOHVATANJE_PAKETI;
    }
    
}

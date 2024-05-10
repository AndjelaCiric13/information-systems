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
public class KreiranjeMesta extends RequestForSubsystem {
    
    public String grad;
    
    public KreiranjeMesta(String grad) {
        this.subsystem = TypeOfSubsystem.S1;
        this.request = TypeOfRequest.KREIRANJE_MESTO;
        this.grad = grad;
    }
    
}

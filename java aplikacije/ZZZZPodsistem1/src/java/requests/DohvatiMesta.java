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
public class DohvatiMesta extends RequestForSubsystem {
    
    public DohvatiMesta() {
        this.subsystem = RequestForSubsystem.TypeOfSubsystem.S1;
        this.request = RequestForSubsystem.TypeOfRequest.DOHVATANJE_MESTA;
    }
}

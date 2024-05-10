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
public class PromenaEmaila extends RequestForSubsystem {
    
    public int idKor;
    public String email;
    
    public PromenaEmaila(int idKor, String email) {
        this.subsystem = RequestForSubsystem.TypeOfSubsystem.S1;
        this.request = RequestForSubsystem.TypeOfRequest.PROMENA_EMAIL_KORISNIK;
        this.idKor = idKor;
        this.email = email;
    }
    
}

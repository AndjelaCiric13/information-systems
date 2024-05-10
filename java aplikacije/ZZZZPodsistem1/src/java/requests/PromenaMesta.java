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
public class PromenaMesta extends RequestForSubsystem {
    
    public int idKor;
    public String mesto;
    
    public PromenaMesta(int idKor, String mesto) {
        this.subsystem = RequestForSubsystem.TypeOfSubsystem.S1;
        this.request = RequestForSubsystem.TypeOfRequest.PROMENA_MESTO_KORISNIK;
        this.idKor = idKor;
        this.mesto = mesto;
    }
    
}

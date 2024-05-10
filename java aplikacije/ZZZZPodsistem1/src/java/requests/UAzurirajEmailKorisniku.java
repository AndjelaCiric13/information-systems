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
public class UAzurirajEmailKorisniku extends RequestForSubsystem {
    public int idKor;
    public String email;
    
    public UAzurirajEmailKorisniku (int idKor, String email){
        this.request = TypeOfRequest.KORISNIKU_PROMENJEN_EMAIL;
        this.idKor = idKor;
        this.email = email;
    }
}

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
public class UAzurirajMestoKorisniku extends RequestForSubsystem{
    
    public int idKor;
    public int idMes;
    
    public UAzurirajMestoKorisniku(int idKor, int idMes){
        this.request = TypeOfRequest.KORISNIKU_PROMENJENO_MESTO;
        this.idKor = idKor;
        this.idMes = idMes;
    }
    
}

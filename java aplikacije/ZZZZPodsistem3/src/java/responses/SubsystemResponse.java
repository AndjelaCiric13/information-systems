/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package responses;

import java.io.Serializable;

/**
 *
 * @author Korisnik
 */
public class SubsystemResponse implements Serializable {
    public String message;
    
    public SubsystemResponse(String mes){
        this.message = mes;
    }

    @Override
    public String toString() {
        return message;
    }
    
    
}

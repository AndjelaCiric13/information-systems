/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzklijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.Order;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Korisnik
 */
public class ZZKlijent {

    /**
     * @param args the command line arguments
     */
    
    public static int ID=1;
    public static void main(String[] args) {
        while(true){
            try {
                String method = "GET";
                String forURL = "http://localhost:8080/ZZServer/Server/";
                Scanner scanner = new Scanner(System.in);
                System.out.println( "\n\n\nIzaberite opciju:\n" +"1. Kreiranje grada\n" +
                        "2. Kreiranje korisnika\n" +
                        "3. Promena email adrese za korisnika\n" +
                        "4. Promena mesta za korisnika\n" +
                        "5. Kreiranje kategorije\n" +
                        "6. Kreiranje video snimka\n" +
                        "7. Promena naziva video snimka\n" +
                        "8. Dodavanje kategorije video snimku\n" +
                        "9. Kreiranje paketa\n" +
                        "10. Promena mesečne cene za paket\n" +
                        "11. Kreiranje pretplate korisnika na paket\n" +
                        "12. Kreiranje gledanja video snimka od strane korisnika\n" +
                        "13. Kreiranje ocene korisnika za video snimak\n" +
                        "14. Menjanje ocene korisnika za video snimak\n" +
                        "15. Brisanje ocene korisnika za video snimak\n" +
                        "16. Brisanje video snimka od strane korisnika koji ga je kreirao\n" +
                        "17. Dohvatanje svih mesta\n" +
                        "18. Dohvatanje svih korisnika\n" +
                        "19. Dohvatanje svih kategorija\n" +
                        "20. Dohvatanje svih video snimaka\n" +
                        "21. Dohvatanje kategorija za određeni video snimak\n" +
                        "22. Dohvatanje svih paketa\n" +
                        "23. Dohvatanje svih pretplata za korisnika\n" +
                        "24. Dohvatanje svih gledanja za video snimak\n" +
                        "25. Dohvatanje svih ocena za video snimak");
                
                String choice = scanner.nextLine();
                //System.out.println(choice);
                int choosen = Integer.parseInt(choice);
                switch(choosen){
                
                    case 1: {
                        System.out.println("Unesite ime mesta:");
                        String mesto = scanner.nextLine();
                        forURL += "mesto/" + mesto;
                        method = "POST";
                        break;
                    }
                    
                    case 2: {
                        System.out.println("Ime korisnika:");
                        String imeKor = scanner.nextLine();
                        System.out.println("Email:");
                        String email = scanner.nextLine();
                        System.out.println("Godiste:");
                        String godiste = scanner.nextLine();
                        System.out.println("Pol:");
                        String pol = scanner.nextLine();
                        System.out.println("Mesto:");
                        String mesto = scanner.nextLine();
                        
                        forURL+= "korisnik/" + imeKor + "/" + email + "/" + godiste +"/" + pol + "/" + mesto;
                        method = "POST";
                        break;
                    }
                    
                    case 3: {
                        System.out.println("Vas novi email:");
                        String email = scanner.nextLine();
                        forURL+="korisnik/" + ID + "/" + email;
                        method = "PUT";
                        break;
                    }
                    
                    case 4: {
                        System.out.println("Vase novo mesto:");
                        String nazivMesta = scanner.nextLine();
                        forURL+="mesto/" + ID + "/" + nazivMesta;
                        method = "PUT";
                        break;
                    }
                    
                    case 5: {
                        System.out.println("Naziv nove kategorije:");
                        String ime = scanner.nextLine();
                        forURL+="kategorija/" + ime;
                        method = "POST";
                        break;
                    }
                    
                    case 6: {
                        System.out.println("Naziv snimka:");
                        String naziv = scanner.nextLine();
                        System.out.println("Trajanje snimka:");
                        String trajanje = scanner.nextLine();
                        System.out.println("Datum postavljanja:");
                        String datum = scanner.nextLine();
                        System.out.println("Vreme postavljanja:");
                        String vreme = scanner.nextLine();
                        forURL+="snimak/" + ID + "/" + naziv + "/" + trajanje + "/" + datum + "/" + vreme;
                        method = "POST";
                        break;
                    }
                    
                    case 7: {
                        System.out.println("ID snimka:");
                        String idSni = scanner.nextLine();
                        System.out.println("Novi naziv snimka:");
                        String naziv = scanner.nextLine();
                        
                        forURL+="snimak/" + idSni + "/" + naziv;
                        method = "PUT";
                        break;
                    }
                    
                    case 8: {
                        System.out.println("ID snimka:");
                        String idSni = scanner.nextLine();
                        System.out.println("Nazv kategorije:");
                        String kategorija = scanner.nextLine();
                        forURL+="snimak/" + idSni + "/" + kategorija;
                        method="POST";
                        break;
                    }
                    
                    case 9: {
                        System.out.println("Naziv paketa:");
                        String paket = scanner.nextLine();
                        System.out.println("Mesecna cena:");
                        String mes = scanner.nextLine();
                        forURL+="paket/" + paket + "/" + mes;
                        method="POST";
                        break;
                    }
                    
                    case 10: {
                        System.out.println("Naziv paketa:");
                        String paket = scanner.nextLine();
                        System.out.println("Nova mesecna cena:");
                        String mes = scanner.nextLine();
                        forURL+="paket/" + paket + "/" + mes;
                        method="PUT";
                        break;
                    }
                    
                    case 11: {
                        System.out.println("ID korisnika:");
                        String idKor = scanner.nextLine();
                        System.out.println("Naziv paketa:");
                        String paket = scanner.nextLine();
                        forURL+="pretplata/" + idKor + "/" + paket;
                        method="POST";
                        break;
                    }
                    
                    case 12: {
                        System.out.println("ID korisnika:");
                        String idKor = scanner.nextLine();
                        System.out.println("ID snimka:");
                        String idSni = scanner.nextLine();
                        System.out.println("Sekunda od pocetka:");
                        String sekOdPoc = scanner.nextLine();
                        System.out.println("Sekundi gledano:");
                        String gledano = scanner.nextLine();
                        
                        forURL+="gledanje/" + idKor + "/" + idSni + "/2022-11-11/22:22/" + sekOdPoc + "/" + gledano;
                        method="POST";
                        break;
                    }
                    
                    case 13: {
                        System.out.println("ID korisnika:");
                        String idKor = scanner.nextLine();
                        System.out.println("ID snimka:");
                        String idSni = scanner.nextLine();
                        System.out.println("Ocena za snimak:");
                        String ocena = scanner.nextLine();
                        forURL+="ocena/" + idKor + "/" + idSni + "/" + ocena + "/2022-11-11/22:22" ;
                        method="POST";
                        break;
                    }
                    
                    case 14: {
                        System.out.println("ID korisnika:");
                        String idKor = scanner.nextLine();
                        System.out.println("ID snimka:");
                        String idSni = scanner.nextLine();
                        System.out.println("Nova ocena za snimak:");
                        String ocena = scanner.nextLine();
                        forURL+="ocena/" + idKor + "/" + idSni + "/" + ocena;
                        method="PUT";
                        break;
                    }
                    
                    case 15: {
                        System.out.println("ID korisnika:");
                        String idKor = scanner.nextLine();
                        System.out.println("ID snimka:");
                        String idSni = scanner.nextLine();
                   
                        forURL+="ocena/" + idKor + "/" + idSni;
                        method="DELETE";
                        break;
                    }
                    
                    case 16: {
                        System.out.println("Naziv snimka:");
                        String naziv = scanner.nextLine();
                        forURL += "snimak/" + ID + "/" + naziv;
                        method="DELETE";
                        break;
                    }
                    
                    case 17: {
                        forURL += "mesto";
                        method="GET";
                        break;
                    }
                    
                    case 18: {
                        forURL += "korisnik";
                        method="GET";
                        break;
                    }
                    
                    case 19: {
                        forURL += "kategorija";
                        method="GET";
                        break;
                    }
                    
                    case 20: {
                        forURL += "snimak";
                        method="GET";
                        break;
                    }
                    
                    case 21: {
                        System.out.println("ID snimka:");
                        String idSni = scanner.nextLine();
                        forURL+="snimak/" + idSni;
                        method = "GET";
                        break;
                    }
                    
                    case 22: {
                        forURL += "paket";
                        method="GET";
                        break;
                    }
                    
                    case 23: {
                        System.out.println("ID korisnika:");
                        String idKor = scanner.nextLine();
                        forURL+="pretplata/" + idKor;
                        method = "GET";
                        break;
                    }
                    
                    case 24: {
                        System.out.println("ID snimka:");
                        String idSni = scanner.nextLine();
                        forURL+="gledanje/" + idSni;
                        method = "GET";
                        break;
                    }
                    
                    case 25: {
                        System.out.println("ID snimka:");
                        String idSni = scanner.nextLine();
                        forURL+="ocena/" + idSni;
                        method = "GET";
                        break;
                    }
                    
                    case 0: {
                        scanner.close();
                        return;
                    }
                    
                }
                    
                
                URL url = new URL(forURL);
                
                
                HttpURLConnection connection =  (HttpURLConnection)url.openConnection();
                connection.setRequestMethod(method);
                
                System.out.println("Poslat zahtev");
               
                int responseCode =  connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
    
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                         response.append(inputLine);
                    }

                    in.close();
        
                String responseStr = response.toString();
                String r = responseStr.substring(12, responseStr.length()-2);
                String [] str = r.split("#");
                for(String s:str)
                    System.out.println(s);
                
            } 
                
                connection.disconnect();
                
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ZZKlijent.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (MalformedURLException ex) {
                Logger.getLogger(ZZKlijent.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ZZKlijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}

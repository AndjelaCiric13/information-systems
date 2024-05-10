/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import requests.DohvatiGledanjaZaVideoSnimak;
import requests.DohvatiPretplateZaKorisnika;
import requests.KreiranjeGledanjaSnimka;
import requests.KreiranjeSnimka;
import responses.SubsystemResponse;
import server.Main;

/**
 *
 * @author Korisnik
 */
@Path("gledanje")
public class GledanjeResource {
    @Inject
    Main main;
    
     @POST
     @Path("{idKor}/{idSni}/{datum}/{vreme}/{sekundaOdPocetka}/{sekundiOdgledano}")
     public Response kreirajSnimak(@PathParam("idKor") int idKor, @PathParam("idSni") int idSni , @PathParam("datum") String datum, @PathParam("vreme") String vreme,
             @PathParam("sekundaOdPocetka") String sek1, @PathParam("sekundiOdgledano") String sek2){
         SubsystemResponse response = main.getResponseFromSubsystem(new KreiranjeGledanjaSnimka(idKor, idSni, datum, vreme, sek2, sek2));
        return Response.status(Response.Status.OK).entity(response).build();
     }

    @GET
    @Path("{idSni}")
    public Response dohvatiGledanjaSnimka(@PathParam("idSni") int idSni){
        SubsystemResponse response = main.getResponseFromSubsystem(new DohvatiGledanjaZaVideoSnimak(idSni));
        return Response.status(Response.Status.OK).entity(response).build();   
    }

}

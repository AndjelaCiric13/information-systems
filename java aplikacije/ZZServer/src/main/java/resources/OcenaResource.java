/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import requests.BrisanjeOceneKorisnikaZaSnimak;
import requests.DohvatiGledanjaZaVideoSnimak;
import requests.DohvatiOceneZaSnimak;
import requests.KreiranjeGledanjaSnimka;
import requests.KreiranjeOceneSnimka;
import requests.MenjanjeOcenSnimkaOdKorisnika;
import responses.SubsystemResponse;
import server.Main;

/**
 *
 * @author Korisnik
 */
@Path("ocena")
public class OcenaResource {
    @Inject
    Main main;
    
     @POST
     @Path("{idKor}/{idSni}/{ocena}/{datum}/{vreme}")
     public Response oceniSnimak(@PathParam("idKor") int idKor, @PathParam("idSni") int idSni , @PathParam("ocena") String ocena,
             @PathParam("datum") String datum, @PathParam("vreme") String vreme){
         SubsystemResponse response = main.getResponseFromSubsystem(new KreiranjeOceneSnimka(idKor, idSni, ocena, datum, vreme));
        return Response.status(Response.Status.OK).entity(response).build();
     }
     
     @PUT
     @Path("{idKor}/{idSni}/{novaOcena}")
     public Response promeniOcenu(@PathParam("idKor") int idKor, @PathParam("idSni") int idSni , @PathParam("novaOcena") String ocena){
          SubsystemResponse response = main.getResponseFromSubsystem(new MenjanjeOcenSnimkaOdKorisnika(idKor, idSni, ocena));
        return Response.status(Response.Status.OK).entity(response).build();
     }
     
     @DELETE
     @Path("{idKor}/{idSni}")
     public Response brisiiOcenu(@PathParam("idKor") int idKor, @PathParam("idSni") int idSni){
          SubsystemResponse response = main.getResponseFromSubsystem(new BrisanjeOceneKorisnikaZaSnimak(idKor, idSni));
        return Response.status(Response.Status.OK).entity(response).build();
     }
     
     
     @GET
    @Path("{idSni}")
    public Response dohvatiOceneSnimka(@PathParam("idSni") int idSni){
        SubsystemResponse response = main.getResponseFromSubsystem(new DohvatiOceneZaSnimak(idSni));
        return Response.status(Response.Status.OK).entity(response).build();   
    }
}

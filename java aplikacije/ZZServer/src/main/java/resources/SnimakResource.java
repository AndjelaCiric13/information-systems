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
import requests.DodajKategorijuSnimku;
import requests.DohvatiKategorijeZaSnimak;
import requests.DohvatiSnimke;
import requests.KreiranjeKategorije;
import requests.KreiranjeSnimka;
import requests.ObrisiSnimak;
import requests.PromenaNazivaSnimka;
import responses.SubsystemResponse;
import server.Main;

/**
 *
 * @author Korisnik
 */
@Path("snimak")
public class SnimakResource {
     @Inject
    Main main;
     
     @POST
     @Path("{idKor}/{naziv}/{trajanje}/{datum}/{vreme}")
     public Response kreirajSnimak(@PathParam("idKor") int idKor, @PathParam("naziv") String naziv, @PathParam("trajanje") String trajanje
     , @PathParam("datum") String datum, @PathParam("vreme") String vreme){
         SubsystemResponse response = main.getResponseFromSubsystem(new KreiranjeSnimka(idKor, naziv, trajanje, datum, vreme));
        return Response.status(Response.Status.OK).entity(response).build();
     }
     
     @PUT
     @Path("{idSni}/{naziv}")
     public Response promeniNazivSnimka(@PathParam("idSni") int idSni, @PathParam("naziv") String naziv){
         SubsystemResponse response = main.getResponseFromSubsystem(new PromenaNazivaSnimka(idSni, naziv));
        return Response.status(Response.Status.OK).entity(response).build();
     }
     
    @POST
    @Path("{idSni}/{nazivKat}")
    public Response dodajKategorijuSnimku(@PathParam("idSni") int idSni, @PathParam("nazivKat") String naziv){
         SubsystemResponse response = main.getResponseFromSubsystem(new DodajKategorijuSnimku(idSni, naziv));
        return Response.status(Response.Status.OK).entity(response).build();
     }
    
    @DELETE
    @Path("{idKor}/{naziv}")
    public Response obrisiSnimak(@PathParam("idKor") int idKor, @PathParam("naziv") String naziv){
         SubsystemResponse response = main.getResponseFromSubsystem(new ObrisiSnimak(idKor, naziv));
        return Response.status(Response.Status.OK).entity(response).build();
     }
    
    
    @GET
    public Response dohvatiSveSnimke(){
        SubsystemResponse response = main.getResponseFromSubsystem(new DohvatiSnimke());
        return Response.status(Response.Status.OK).entity(response).build();
    }
     
    @GET
    @Path("{idSni}")
    public Response dohvatiKategorijeZaSnimak(@PathParam("idSni") int idSni){
        SubsystemResponse response = main.getResponseFromSubsystem(new DohvatiKategorijeZaSnimak(idSni));
        return Response.status(Response.Status.OK).entity(response).build();
    }
    
}

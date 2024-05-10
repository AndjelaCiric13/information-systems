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
import requests.DohvatiPretplateZaKorisnika;
import requests.KreiranjePaketa;
import requests.KreiranjePretplateKorisnika;
import responses.SubsystemResponse;
import server.Main;

/**
 *
 * @author Korisnik
 */
@Path("pretplata")
public class PretplataResource {
    @Inject
    Main main;
    
    @POST
    @Path("{idKor}/{nazivPak}")
    public Response kreirajPretplatuKorisnika(@PathParam("idKor") int idKor, @PathParam("nazivPak") String naziv){
        SubsystemResponse response = main.getResponseFromSubsystem(new KreiranjePretplateKorisnika(idKor, naziv));
        return Response.status(Response.Status.OK).entity(response).build();   
    }
    
    @GET
    @Path("{idKor}")
    public Response dohvatiPretplateZaKorisnika(@PathParam("idKor") int idKor){
        SubsystemResponse response = main.getResponseFromSubsystem(new DohvatiPretplateZaKorisnika(idKor));
        return Response.status(Response.Status.OK).entity(response).build();   
    }
}

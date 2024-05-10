/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import server.Main;
import requests.*;
import responses.SubsystemResponse;

/**
 *
 * @author Korisnik
 */
@Path("korisnik")
public class KorisnikResource {
    
     @Inject
    Main main;
    
    @POST
    @Path("{ime}/{email}/{godiste}/{pol}/{nazivMesta}")
    public Response kreirajGrad(@PathParam("ime") String ime, @PathParam("email") String email,
            @PathParam("godiste") int godiste, @PathParam("pol") char pol, @PathParam("nazivMesta") String idMes){
        
       SubsystemResponse response = main.getResponseFromSubsystem(new KreiranjeKorisnika(ime, email,godiste, pol, idMes));
        return Response.status(Response.Status.OK).entity(response).build();
    }
    
    
    @PUT
    @Path("{idKor}/{email}")
    public Response promeniEmail(@PathParam("idKor") int idKor, @PathParam("email") String email){
        SubsystemResponse response = main.getResponseFromSubsystem(new PromenaEmaila(idKor, email));
        return Response.status(Response.Status.OK).entity(response).build();
    }
    
    
    
    
    
    @GET
    public Response dohvatiKorisnike(){
        SubsystemResponse response = main.getResponseFromSubsystem(new DohvatiKorisnike());
        return Response.status(Response.Status.OK).entity(response).build();
    }
    
    
}

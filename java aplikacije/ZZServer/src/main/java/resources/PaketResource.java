/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import requests.DohvatiPakete;
import requests.KreiranjePaketa;
import requests.PromenaMesecneCenePaketa;
import requests.PromenaMesta;
import responses.SubsystemResponse;
import server.Main;

/**
 *
 * @author Korisnik
 */
@Path("paket")
public class PaketResource {
    @Inject
    Main main;
    
    @POST
    @Path("{naziv}/{mesCena}")
    public Response kreirajPaket(@PathParam("naziv") String naziv, @PathParam("mesCena") int mesCena){
        SubsystemResponse response = main.getResponseFromSubsystem(new KreiranjePaketa(naziv, mesCena));
        return Response.status(Response.Status.OK).entity(response).build();   
    }
    
    @PUT
    @Path("{nazivPak}/{mesCenaPak}")
    public Response promenaCenePaket(@PathParam("nazivPak") String naziv, @PathParam("mesCenaPak") int mesCena){
        SubsystemResponse response = main.getResponseFromSubsystem(new PromenaMesecneCenePaketa(mesCena, naziv));
        return Response.status(Response.Status.OK).entity(response).build();   
    }
    
    @GET
    public Response dohvatiPakete(){
        SubsystemResponse response = main.getResponseFromSubsystem(new DohvatiPakete());
        return Response.status(Response.Status.OK).entity(response).build(); 
    }
    
}

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
import requests.KreiranjeMesta;
import server.Main;
import requests.*;
import responses.SubsystemResponse;

/**
 *
 * @author Korisnik
 */
@Path("mesto")
public class MestoResource {
    
    @Inject
    Main main;
    
    @POST
    @Path("{naziv}")
    public Response kreirajGrad(@PathParam("naziv") String nazivGrada){
        System.out.println("Server:stigao zahtev");
        SubsystemResponse response = main.getResponseFromSubsystem(new KreiranjeMesta(nazivGrada));
        return Response.status(Response.Status.OK).entity(response).build();
    }
    
    @PUT
    @Path("{idKor}/{nazivMesta}")
    public Response promeniMesto(@PathParam("idKor") int idKor, @PathParam("nazivMesta") String mesto){
        SubsystemResponse response = main.getResponseFromSubsystem(new PromenaMesta(idKor, mesto));
        return Response.status(Response.Status.OK).entity(response).build();
    }
    
    @GET
    public Response dohvatiMesta(){
        SubsystemResponse response = main.getResponseFromSubsystem(new DohvatiMesta());
        return Response.status(Response.Status.OK).entity(response).build();
    }
}

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
import requests.DohvatiKategorije;
import requests.DohvatiSnimke;
import requests.KreiranjeKategorije;
import requests.KreiranjeKorisnika;
import responses.SubsystemResponse;
import server.Main;

/**
 *
 * @author Korisnik
 */
@Path("kategorija")
public class KategorijaResource {
     @Inject
    Main main;
     
     @POST
     @Path("{ime}")
     public Response kreirajKategoriju(@PathParam("ime") String ime){
         SubsystemResponse response = main.getResponseFromSubsystem(new KreiranjeKategorije(ime));
        return Response.status(Response.Status.OK).entity(response).build();
     }
     
    @GET
    public Response dohvatiSveKategorije(){
        SubsystemResponse response = main.getResponseFromSubsystem(new DohvatiKategorije());
        return Response.status(Response.Status.OK).entity(response).build();
    }
}

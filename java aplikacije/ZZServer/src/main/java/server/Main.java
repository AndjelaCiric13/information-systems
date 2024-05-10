/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Singleton;
//import javax.inject.Singleton;
import javax.jms.Queue;
    import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import requests.*;
import requests.RequestForSubsystem.TypeOfSubsystem;
import responses.SubsystemResponse;
//import javax.ws.rs.core.Request;
/**
 *
 * @author Korisnik
 */
@Singleton
public class Main {
    
    @Resource(lookup="queue3")
    private Queue queue3;
    
    @Resource(lookup="queue2")
    private Queue queue2;
    
    @Resource(lookup="queue1")
   private Queue queue1;
    
    @Resource(lookup="queueServer")
    private Queue queueServer;
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    
    public SubsystemResponse getResponseFromSubsystem(RequestForSubsystem req){
        try {
            JMSContext context = connectionFactory.createContext();
            Queue queue = null;
            TypeOfSubsystem sendTo = req.typeOfSubsystem();
            JMSConsumer consumer = null;
            JMSProducer producer = context.createProducer();
            if(sendTo == TypeOfSubsystem.S1 ||sendTo == TypeOfSubsystem.S123){
                queue = queue1;
            }
            else if(sendTo == TypeOfSubsystem.S2  || sendTo == TypeOfSubsystem.S23 ){
                queue = queue2;
            }
            else if(sendTo == TypeOfSubsystem.S3){
                queue = queue3;
            }
            int property = req.myCode;
            consumer = context.createConsumer(queueServer, "Code = '" + property + "'"  );
            ObjectMessage obj = context.createObjectMessage(req);
            if(queue !=null) producer.send(queue, obj);
            
            System.out.println("Cekam odgovor od podsistema");
            Message msg = (Message)consumer.receive();
            
            
            
            if (!(msg instanceof ObjectMessage)){
                return new SubsystemResponse("Greska!");
            }
            Serializable res = ((ObjectMessage)msg).getObject(); 
            System.out.println("Stigao odgovor" +  (SubsystemResponse)res );
            context.close();
            return ((SubsystemResponse)res);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new SubsystemResponse("Greska!");  
        
    }
}

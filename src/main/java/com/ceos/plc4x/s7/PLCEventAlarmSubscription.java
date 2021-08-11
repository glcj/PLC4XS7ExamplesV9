/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceos.plc4x.s7;

import java.util.Map;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.apache.plc4x.java.api.messages.PlcSubscriptionRequest;
import org.apache.plc4x.java.api.messages.PlcSubscriptionResponse;
import org.apache.plc4x.java.api.model.PlcConsumerRegistration;
import org.apache.plc4x.java.s7.events.S7AlarmEvent;
import org.apache.plc4x.java.s7.events.S7ModeEvent;
import org.apache.plc4x.java.s7.events.S7SysEvent;

/**
 *
 * @author cgarcia
 */
public class PLCEventAlarmSubscription {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        try (PlcConnection connection = new PlcDriverManager().getConnection("s7://192.168.1.51?remote-rack=0&remote-slot=3&controller-type=S7_400")) {
            final PlcSubscriptionRequest.Builder subscription = connection.subscriptionRequestBuilder();

            subscription.addEventField("myALM", "ALM");
            final PlcSubscriptionRequest sub = subscription.build();
            
            System.out.println("Query: " + sub.toString());

            final PlcSubscriptionResponse subresponse = sub.execute().get();
            
            //Si todo va bien con la subscripción puedo
            PlcConsumerRegistration registerAlm = 
                    subresponse
                    .getSubscriptionHandle("myALM")
                    .register(msg -> {
                        System.out.println("******** S7AlmEvent *********");
                        Map<String, Object> map = ((S7AlarmEvent) msg).getMap();
                        map.forEach((x, y) -> { 
                            System.out.println(x + " : " + y);
                        });
                        System.out.println("****************************");
                    });

            System.out.println("Waiting for the messages.");
            
            Thread.sleep(120000);
            
            connection.close();
            
            System.out.println("Ending the connection.");            
        }        
    }
    
}

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.ceos.plc4x.s7;

import java.util.Map;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.apache.plc4x.java.api.messages.PlcSubscriptionRequest;
import org.apache.plc4x.java.api.messages.PlcSubscriptionResponse;
import org.apache.plc4x.java.api.model.PlcConsumerRegistration;
import org.apache.plc4x.java.s7.events.S7ModeEvent;
import org.apache.plc4x.java.s7.events.S7SysEvent;
import org.apache.plc4x.java.s7.utils.S7DiagnosticEventId;

/**
 *
 * @author cgarcia
 */
public class PLCEventSysSubscription {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        try (PlcConnection connection = new PlcDriverManager().getConnection("s7://192.168.1.51?remote-rack=0&remote-slot=3&controller-type=S7_400")) {
            final PlcSubscriptionRequest.Builder subscription = connection.subscriptionRequestBuilder();

            subscription.addEventField("mySYS", "SYS");
            final PlcSubscriptionRequest sub = subscription.build();
            
            System.out.println("Query: " + sub.toString());

            final PlcSubscriptionResponse subresponse = sub.execute().get();
            
            //Si todo va bien con la subscripción puedo
            PlcConsumerRegistration registerSys = 
                    subresponse
                    .getSubscriptionHandle("mySYS")
                    .register(msg -> {
                        System.out.println("******** S7SysEvent ********");
                        Map<String, Object> map = ((S7SysEvent) msg).getMap();
                        map.forEach((x, y) -> { 
                            System.out.println(x + " : " + y);
                        });
                        
                        Integer eventid = (Integer) map.get(S7SysEvent.Fields.EVENT_ID.name());
                        
                        System.out.println("DIAGNOSTIC: " + S7DiagnosticEventId.
                                valueOf(eventid.shortValue()).getDescription());
                        System.out.println("****************************");
                    });   

            System.out.println("Waiting for the messages.");
            
            Thread.sleep(120000);
            
            connection.close();
            
            System.out.println("Ending the connection.");              
        }        
    }
    
}

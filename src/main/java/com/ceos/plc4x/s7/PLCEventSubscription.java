/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

package com.ceos.plc4x.s7;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcSubscriptionEvent;
import org.apache.plc4x.java.api.messages.PlcSubscriptionRequest;
import org.apache.plc4x.java.api.messages.PlcSubscriptionResponse;
import org.apache.plc4x.java.s7.events.S7AlarmEvent;
import org.apache.plc4x.java.s7.events.S7ModeEvent;
import org.apache.plc4x.java.s7.events.S7SysEvent;
import org.apache.plc4x.java.s7.events.S7UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example of subscription to PLC events (USR, SYS, MODE, ALM_8, ALM_S).
 * @author cgarcia
 */
public class PLCEventSubscription {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(PLCEventSubscription.class);
             
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try (PlcConnection plcConnection = new PlcDriverManager().getConnection("s7://192.168.1.51?remote-rack=0&remote-slot=3&controller-type=S7_400")){
            
            /*  
            * Individual constructors for each type of event.
            * USR:  User events.
            * SYS:  System events
            * MODE: PLC operating status.
            * ALM:  Alarm and events, from the PLC or from the user.
            */
            PlcSubscriptionRequest.Builder subsUsr = plcConnection.subscriptionRequestBuilder();
            PlcSubscriptionRequest.Builder subsSys = plcConnection.subscriptionRequestBuilder();
            PlcSubscriptionRequest.Builder subsMode = plcConnection.subscriptionRequestBuilder();
            PlcSubscriptionRequest.Builder subsAlm = plcConnection.subscriptionRequestBuilder();

            /*
            * A single constructor for all events.
            * USR:  User events.
            * SYS:  System events
            * MODE: PLC operating status.
            * ALM:  Alarm and events, from the PLC or from the user.
            */            
            PlcSubscriptionRequest.Builder subsAll = plcConnection.subscriptionRequestBuilder(); 
            
            /*
            * Specifying individual events. 
            */
            subsUsr.addEventField("USR-1", "USR");
            subsSys.addEventField("SYS-1", "SYS");
            subsMode.addEventField("MODE-1", "MODE");             
            subsAlm.addEventField("ALARM-1", "ALM");
            
            /*
            * Specifying multiple events for a single subscription.. 
            */            
            subsAll.addEventField("USR-1", "USR");
            subsAll.addEventField("SYS-1", "SYS");
            subsAll.addEventField("MODE-1", "MODE");  
            subsAll.addEventField("ALARM-1", "ALM");             

            /*
            * I already build the individual structures. 
            */               
            PlcSubscriptionRequest subsRequestUsr = subsUsr.build();
            PlcSubscriptionRequest subsRequestSys = subsSys.build();
            PlcSubscriptionRequest subsRequestMode = subsMode.build();
            PlcSubscriptionRequest subsRequestAlm = subsAlm.build();

            PlcSubscriptionRequest subsRequestAll = subsAll.build();            
            
            /*
            * Defining the way to consume events.
            */
            
            Consumer<PlcSubscriptionEvent> eventUsrConsumer = (event) -> {
                System.out.println("Consuming messages USR: " +  event.toString());
            };            
            
            Consumer<PlcSubscriptionEvent> eventSysConsumer = (event) -> {
                System.out.println("Consuming messages SYS: " + event.toString());                
            };               
            
            Consumer<PlcSubscriptionEvent> eventModeConsumer = (event) -> {
                System.out.println("Consuming messages MODE: " + event.toString());
            };             
            
            Consumer<PlcSubscriptionEvent> eventAlmConsumer = (event) -> {
                System.out.println("Consuming messages ALARM: " + event.toString());             
            }; 
            
            Consumer<PlcSubscriptionEvent> eventAllConsumer = (event) -> {
                System.out.println("Consuming messages USR,SYS,MODE & ALARM: " + event.toString());
            };              
            
            /*
            * Subscribes to the controller.
            */                        
            PlcSubscriptionResponse subsResponseUsr = subsRequestUsr.execute().get(2, TimeUnit.SECONDS);
            PlcSubscriptionResponse subsResponseSys = subsRequestSys.execute().get(2, TimeUnit.SECONDS);
            PlcSubscriptionResponse subsResponseMode = subsRequestMode.execute().get(2, TimeUnit.SECONDS);
            PlcSubscriptionResponse subsResponseAlm = subsRequestAlm.execute().get(2, TimeUnit.SECONDS);
            PlcSubscriptionResponse subsResponseAll = subsRequestAll.execute().get(2, TimeUnit.SECONDS);            
            
            
            /*
            * Check for response status.
            */              
            System.out.println("USR Subscription        : " + subsResponseUsr.getResponseCode("USR-1"));
            System.out.println("SYS Subscription        : " + subsResponseSys.getResponseCode("SYS-1"));
            System.out.println("MODE Subscription       : " + subsResponseMode.getResponseCode("MODE-1"));
            System.out.println("ALARM_X Subscription    : " + subsResponseAlm.getResponseCode("ALARM-1"));
            System.out.println("");

            
            /*
            * Register my consumers
            */            
            subsResponseUsr.getSubscriptionHandle("USR-1").register(eventUsrConsumer);
            subsResponseSys.getSubscriptionHandle("SYS-1").register(eventSysConsumer);
            subsResponseMode.getSubscriptionHandle("MODE-1").register(eventModeConsumer);
            subsResponseAlm.getSubscriptionHandle("ALARM-1").register(eventAlmConsumer);
            System.out.println("Waiting for events");
            
            /*                        
            PlcSubscriptionRequest.Builder queryBuilder = plcConnection.subscriptionRequestBuilder();
            queryBuilder.addEventField("theQuery", "QUERY:ALARM_8");
            PlcSubscriptionRequest queryEvents = queryBuilder.build();
            PlcSubscriptionResponse queryResponse = queryEvents.execute().get(2, TimeUnit.SECONDS);             
            
            System.out.println("QUERY ALARM_S: " + queryResponse.getResponseCode("theQuery"));   
            */
            
            Thread.sleep(190000);
            
            System.out.println("Bye...");
            
            plcConnection.close();
            
        }  catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
}

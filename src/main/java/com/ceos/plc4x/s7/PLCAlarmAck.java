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
import org.apache.plc4x.java.DefaultPlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcSubscriptionRequest;
import org.apache.plc4x.java.api.messages.PlcSubscriptionResponse;
import org.apache.plc4x.java.api.messages.PlcUnsubscriptionRequest;
import org.apache.plc4x.java.api.model.PlcConsumerRegistration;
import org.apache.plc4x.java.s7.events.S7AlarmEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author cgarcia
 */
public class PLCAlarmAck {

private static Logger LOGGER = LoggerFactory.getLogger(PLCAlarmAck.class);    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception  {
        // TODO code application logic here
        
        try (PlcConnection connection = new DefaultPlcDriverManager().getConnection("s7://192.168.1.51?remote-rack=0&remote-slot=3&controller-type=S7_400")) {  

            final PlcSubscriptionRequest.Builder subscription = connection.subscriptionRequestBuilder();
            final PlcSubscriptionRequest.Builder subscription2 = connection.subscriptionRequestBuilder();
            
            final PlcSubscriptionRequest.Builder alarmquery = connection.subscriptionRequestBuilder(); 
            final PlcUnsubscriptionRequest.Builder plcunsubscription = connection.unsubscriptionRequestBuilder();

            subscription.addEventTagAddress("myALM", "ALM");
            subscription.addEventTagAddress("myCYC1", "CYC(B1SEC:3):%MB2.0:BYTE[10],%MB200.0:BYTE[10]");  
            subscription.addEventTagAddress("myCYC2", "CYC(B1SEC:1):%MB30.0:BYTE[30]");   
            subscription.addEventTagAddress("myCYC3", "CYC(B1SEC:4):%DB80.DBB94[30]");              
            final PlcSubscriptionRequest sub = subscription.build();
                                     
            final PlcSubscriptionResponse subresponse = sub.execute().get();
            
            PlcConsumerRegistration registerAlm = 
                    subresponse
                    .getSubscriptionHandle("myALM")
                    .register(msg -> {
                       
                        Map<String, Object> map = ((S7AlarmEvent) msg).getMap();
                
                        LOGGER.info((Long) map.get(S7AlarmEvent.Fields.EVENT_ID.name()) + " <:> " + (String) map.get(S7AlarmEvent.Fields.TYPE.name()));
                        /*
                        map.forEach((x, y) -> { 
                            System.out.println(x + " : " + y);
                        });
                        */
                    });
/*
            PlcConsumerRegistration registerCyc = 
                    subresponse
                    .getSubscriptionHandle("myCYC1")
                    .register(msg -> {                        
                        Map<String, Object> map = ((S7CyclicEvent) msg).getMap();
                        PlcSubscriptionRequest request = (PlcSubscriptionRequest) msg.getObject("REQUEST");
                        if (request != null)
                        LOGGER.info("myCYC1: Field Names: " + request.getFieldNames());
                        
                        LOGGER.info("myCYC1: " + map.get("JOBID") + " : " + map.get("TYPE"));
                    }); 
            
            PlcConsumerRegistration registerCyc2 = 
                    subresponse
                    .getSubscriptionHandle("myCYC2")
                    .register(msg -> {                        
                        Map<String, Object> map = ((S7CyclicEvent) msg).getMap();
                        PlcSubscriptionRequest request = (PlcSubscriptionRequest) msg.getObject("REQUEST");
                        if (request != null)
                        LOGGER.info("myCYC2: Field Names: " + request.getFieldNames());                        
                        LOGGER.info("myCYC2: " + map.get("JOBID") + " : " + map.get("TYPE"));
                    });  
  */          
/*
            short[] jobids = new short[1];
            PlcConsumerRegistration registerCyc3 = 
                    subresponse
                    .getSubscriptionHandle("myCYC3")
                    .register(msg -> {      
                        S7CyclicValueEvent s7cycmsg = (S7CyclicValueEvent) msg;
                        Map<String, Object> map = s7cycmsg.getMap();
                        PlcSubscriptionRequest request = (PlcSubscriptionRequest) msg.getObject("REQUEST");
                        if (request != null)
                        LOGGER.info("myCYC: Field Names: " + request.getFieldNames());                        
                        LOGGER.info("myCYC: " + map.get("JOBID") + " : " + map.get("TYPE"));
                        LOGGER.info("myCYC: " + s7cycmsg.getAllBytes("DATA_0"));
                        jobids[0] =(short) map.get("JOBID");
                    });              
            */
            
/*
            
            System.out.println("Campos : " + subresponse.getRequest().getFieldNames());
                        
            alarmquery.addEventField("myQuery", "QUERY:ALARM_8");
            
            final PlcSubscriptionRequest queryrequest = alarmquery.build();
            
            final PlcSubscriptionResponse queryresponse = queryrequest.execute().get();            
            
            System.out.println("Query ALARM_8: " + queryresponse.getResponseCode("myQuery"));

            
            final PlcReadRequest.Builder builder = connection.readRequestBuilder();
            
            builder.addItem("myAck", "ACK:16#00000004;04");
         
            
            final PlcReadRequest readRequest = builder.build();

            final PlcReadResponse readResponse = readRequest.execute().get();
            
            if (readResponse.getResponseCode("myAck") == PlcResponseCode.OK) {
                System.out.println(readResponse.getByte("myAck"));
                List<Byte> buffer = (List<Byte>) readResponse.getAllBytes("myAck");
                System.out.println("Buffer : " + buffer.toString());
                buffer.forEach(item -> {
                    System.out.println(DataTransportErrorCode.enumForValue((short) (item & 0xFF)));
                });

                ByteBuf bytedata = S7EventHelper.ListToByteBuf(buffer);

                System.out.println("Dump values");
                StringBuilder hexdump = new StringBuilder();
                ByteBufUtil.appendPrettyHexDump(hexdump, bytedata);
                System.out.println(hexdump.toString());
            } else {
                System.out.println("Problema en el reconocimiento de alarmas.");
            }
            
            Thread.sleep(10000);;
            
            //registerCyc3.unregister();
            subscription2.addEventField("killCYC3", "CANCEL:" + jobids[0]);
            final PlcSubscriptionRequest subcanceljob = subscription2.build();            
            final PlcSubscriptionResponse rescanceljob = subcanceljob.execute().get();
            System.out.println("CalcelJob : " + rescanceljob.getResponseCode("killCYC3"));
            
            
            Thread.sleep(4000); */
            /*
            registerCyc3 = 
                    subresponse
                    .getSubscriptionHandle("myCYC3")
                    .register(msg -> {      
                        S7CyclicEvent s7cycmsg = (S7CyclicEvent) msg;
                        Map<String, Object> map = s7cycmsg.getMap();
                        PlcSubscriptionRequest request = (PlcSubscriptionRequest) msg.getObject("REQUEST");
                        if (request != null)
                        LOGGER.info("myCYC: Field Names: " + request.getFieldNames());                        
                        LOGGER.info("myCYC: " + map.get("JOBID") + " : " + map.get("TYPE"));
                        LOGGER.info("myCYC: " + s7cycmsg.getAllBytes("DATA_0"));
                    });                  
            
            Thread.sleep(6000);;
            */
            //registerCyc3.unregister();            
            
            while(true){
                Thread.sleep(15000);
            }
            //connection.close();
            
        }      
    }
    
}

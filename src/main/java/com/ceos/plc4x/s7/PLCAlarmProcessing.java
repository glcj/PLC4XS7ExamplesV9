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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This demo uses a "MonDigital" block to capture Alarm8 fields from a PCS7 
 * technology block.
 * The "MonDigital" instance block is configured with the following 
 * transfer data, and called every second.
 * . ExtVal105 -> MB200
 * . ExtVal106 -> MD100
 * . ExtVal107 -> MD300
 * . ExtVal108 -> M0.7 
 * 
 * @author cgarcia
 */
public class PLCAlarmProcessing {

private static Logger LOGGER = LoggerFactory.getLogger(PLCAlarmProcessing.class);  

    private final static String ALARM_MSG_TEST = "BatchId: @3X%4x@\r\nBatch Name @1c%32s@\r\nThis take one byte value:@5Y%2u@.\r\nThe int en Hex format: @6X%4x@\r\nReading real number: @7R%2.3f@ y una lista  @8b%t#lista1@ ";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  throws Exception   {
        /*
        try (PlcConnection connection = new PlcDriverManager().getConnection("s7://192.168.1.51?remote-rack=0&remote-slot=3&controller-type=S7_400")) {  
            final PlcSubscriptionRequest.Builder subscription = connection.subscriptionRequestBuilder();
            subscription.addEventField("myALM", "ALM");
            
            final PlcSubscriptionRequest sub = subscription.build();
                                     
            final PlcSubscriptionResponse subresponse = sub.execute().get();
            
            PlcConsumerRegistration registerAlm = 
                    subresponse
                    .getSubscriptionHandle("myALM")
                    .register(msg -> {
                        LOGGER.info(msg.toString());
                        System.out.println("Message: " + S7EventHelper.AlarmProcessing((S7AlarmEvent) msg, ALARM_MSG_TEST, null));
                        System.out.println("");
                    });            
            
            Thread.sleep(5000);            
            
            connection.close();
            
        }
    */
    }
    
}

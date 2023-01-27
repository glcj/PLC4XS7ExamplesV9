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

/**
 *
 * @author cgarcia
 */
public class PLCEventModeSubscription {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        /*
        try (PlcConnection connection = new PlcDriverManager().getConnection("s7://192.168.1.51?remote-rack=0&remote-slot=3&controller-type=S7_400")) {
            final PlcSubscriptionRequest.Builder subscription = connection.subscriptionRequestBuilder();

            subscription.addEventField("myMODE", "MODE");
            final PlcSubscriptionRequest sub = subscription.build();
            
            System.out.println("Query: " + sub.toString());

            final PlcSubscriptionResponse subresponse = sub.execute().get();
            
            //Si todo va bien con la subscripción puedo
            PlcConsumerRegistration registerMode = 
                    subresponse
                    .getSubscriptionHandle("myMODE")
                    .register(msg -> {
                        System.out.println("******** S7ModeEvent ********");
                        Map<String, Object> map = ((S7ModeEvent) msg).getMap();
                        map.forEach((x, y) -> { 
                            System.out.println(x + " : " + y);
                        });
                        short currentmode = (short) 
                                map.get(S7ModeEvent.Fields.CURRENT_MODE.name());
                        System.out.println("CURRENT_MODE MSG: " + ModeTransitionType.enumForValue(currentmode).name());
                        System.out.println("****************************");
                    });

            System.out.println("Waiting for the messages.");
            
            Thread.sleep(120000);
            
            connection.close();
            
            System.out.println("Ending the connection.");             
            
        }   
*/
    }
    
}

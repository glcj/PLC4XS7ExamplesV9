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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.api.types.PlcResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In the way the driver is designed, an "X" quantity of items can be 
 * requested from the PLC. 
 * The optimizer must be able to partition the requested "Items" and 
 * rebuild the corresponding original responses in an orderly manner.
 * 
 * Input to optimizer
 * 
 * MaxPDUSize
 * +-----------------------------------+
 * Item01
 *       Item02
 *             Item03
 *                   Item04
 *                         Item05
 *                               Item06
 * 
 *                                      ...
 *                                         Item[X-1]
 *                                                  Item[X]
 * MaxPDUSize
 * +-----------------------------------+
 * ItemA1
 *       ItemA2
 *             ItemA3
 *                   ItemA4
 *                         ItemA5
 *                               ItemA6
 * MaxPDUSize
 * +-----------------------------------+
 * ItemB1
 *       ItemB2
 *             ItemB3
 *                   ItemB4
 *                         ItemB5
 *                               ItemB6
 * MaxPDUSize
 * +-----------------------------------+
 * ItemC1
 *       ItemC2
 *             ItemC[X-1]
 *                       ItemC[X]
 * 
 * 
 * 
 * @author cgarcia
 */
public class PLCOptimizerByRequest {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(PLCOptimizerByRequest.class);
    
    /**

     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (PlcConnection plcConnection = new PlcDriverManager().getConnection("s7://192.168.1.51?remote-rack=0&remote-slot=3&controller-type=S7_400")){
            
            PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();
            
            for (int i=1;i<=500;i++){
                builder.addItem("value-"+i, "%Q"+i+".4:BOOL");
                builder.addItem("values-"+i, "%DB63.DBW1:INT[500]");
            };

            
            PlcReadRequest readRequest = builder.build();
            
            CompletableFuture<? extends PlcReadResponse> asyncResponse = readRequest.execute();            
            
            //PlcReadResponse response = readRequest.execute().get(5000, TimeUnit.MILLISECONDS);
            
            asyncResponse.whenComplete((response, throwable) -> {
                try {
                    for (String fieldName : response.getFieldNames()) {            
                        if(response.getResponseCode(fieldName) == PlcResponseCode.OK) {
                            int numValues = response.getNumberOfValues(fieldName);
                            // If it's just one element, output just one single line.
                            if(numValues == 1) {
                                LOGGER.info("Value[" + fieldName + "]: " + response.getObject(fieldName));
                            }
                            // If it's more than one element, output each in a single row.
                            else {
                                LOGGER.info("Value[" + fieldName + "]:");
                                for(int i = 0; i < numValues; i++) {
                                    LOGGER.info(" - " + response.getObject(fieldName, i));
                                }
                            }
                        }
                        // Something went wrong, to output an error message instead.
                        else {
                            LOGGER.error("Error[" + fieldName + "]: " + response.getResponseCode(fieldName).name());
                        }
                    }
                } catch (Exception ex) {
                  LOGGER.debug(ex.getMessage());
              }
            });            
            
            Thread.sleep(4000);            
            
            plcConnection.close();
            
            
        }  catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
}

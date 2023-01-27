/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ceos.plc4x.s7;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.apache.plc4x.java.DefaultPlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.api.types.PlcResponseCode;

/**
 *
 * @author cgarcia
 */
public class PLCReadTags {
    
    public static void main(String[] args) {
  
        // TODO code application logic here
        try (PlcConnection plcConnection = new DefaultPlcDriverManager().getConnection("s7://10.10.1.45?remote-rack=0&remote-slot=0")) {
            
            PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();               

            builder.addTagAddress("008","%DB9000.DBD100:REAL");
            
            PlcReadRequest readRequest = builder.build(); 
              
            PlcReadResponse response = readRequest.execute().get(1, TimeUnit.SECONDS);
            
            System.out.println("Valor: " + response.getFloat("008"));
            
            Collection<String> names = response.getTagNames();
            
            for (String name:names){
                System.out.println("Index: " + name +
                        " Response code: " + response.getResponseCode(name) +
                        " Number of values: " + ((response.getResponseCode(name)==PlcResponseCode.OK)?
                        ((Integer)(response.getNumberOfValues(name))).toString():response.getResponseCode(name)));
            }
            
            plcConnection.close();                 
            
        } catch (Exception ex){
            ex.printStackTrace();
        }    
    }    
}

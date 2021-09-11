/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceos.plc4x.s7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author cgarcia
 */
public class PLCReadBitFields {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(PLCReadBitFields.class);    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
            Thread.sleep(120000);                        
            System.out.println("Ending the connection.");          
    }    
}

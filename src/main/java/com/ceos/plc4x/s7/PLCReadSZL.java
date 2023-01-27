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

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import java.util.List;
import org.apache.plc4x.java.DefaultPlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;

import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.s7.utils.*;

/**
 *
 * @author cgarcia
 */
public class PLCReadSZL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        /*
        try (PlcConnection connection = new DefaultPlcDriverManager().getConnection("s7://192.168.1.50?remote-rack=0&remote-slot=2")) {
            final PlcReadRequest.Builder builder = connection.readRequestBuilder();
            builder.addItem("MySZL", "SZL_ID=16#0011;INDEX=16#0000"); // true
            //builder.addItem("MySZL", "%M1.0:BOOL"); // true
            final PlcReadRequest readRequest = builder.build();

            final PlcReadResponse readResponse = readRequest.execute().get();
            
            Thread.sleep(2000);
            
            System.out.println("Response code: " + readResponse.getResponseCode("MySZL").toString());
            
            System.out.println(readResponse.getByte("MySZL"));
            List<Byte> buffer = (List<Byte>) readResponse.getAllBytes("MySZL");
            System.out.println("Buffer : " + buffer.toString());
            ByteBuf bytedata = S7EventHelper.ListToByteBuf(buffer);
            System.out.println(readResponse.getNumberOfValues("MySZL"));
            
            System.out.println("Dump values");
            StringBuilder hexdump = new StringBuilder();
            ByteBufUtil.appendPrettyHexDump(hexdump, bytedata);
            System.out.println(hexdump.toString());

            
            System.out.println("Parser values");
            StringBuilder szl = S7EventHelper.SZL.ID_0x001C.execute(bytedata);
            System.out.println(szl.toString());

            System.out.println("Ending the connection.");   
*/
        }        
    }
   

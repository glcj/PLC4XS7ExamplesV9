/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceos.plc4x.s7;

/**
 *
 * @author cgarcia
 */
public class PLCOptimizerByRequest {

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
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}

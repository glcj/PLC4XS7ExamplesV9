**PLC4XS7ExamplesV9**

Examples for the use of Siemens Simatic S7 communication libraries [V9.X[ developed in the Apache PLC4X project.

![Image of PLC4X](https://plc4x.apache.org/images/apache_plc4x_logo.png)

For more details visit: http://plc4x.apache.org

If you find this information useful, feel free to collaborate with the project.

If you are not a programmer, you can document or promote the use of this tool.

In order to execute the example programs, use the PLC4X libraries in version 0.9.X.

Many of these features are available on the S7-1500 in its own version of the protocol. Unfortunately this is not open, allowing only PUT / GET functions. Event handling is proprietary, which is a shame.

The examples indicated below each represent the most basic possible handling of the features to be exploited, to facilitate the introduction to the use of the PLC4X libraries.

I hope you find it useful,

![Image of PLC4XS7](../assets/Finales/Medianos/11.png?raw=true)


File | Description
------------ | -------------
PLCReadBitFields |  Example for Read bit fields from S7.
PLCEventSubscription | Step by step event subscription.
PLCEventModeSubscription | "MODE", PLC operation mode event subscription example.
PLCEventSysSubscription  | "SYS", PLC system event subscriptio example.
PLCEventUsrSubscription  | "USR", User defined event subscriptio example.
PLCEventAlarmSubscription | "ALM", ALARM, ALARM_8, ALARM_S type event subscriptio example. 




*Possible SSL Partial Lists:*

The interpretation of the information is done in the _S7Helper.java_ class.


Module class                                              |    SSL-ID    | Implemented
-----------------------------------------------------------|--------------|----
Module identification                                     |    16#xy11   | 
CPU characteristics                                       |    16#xy12   | 
User memory areas                                         |    16#xy13   | 
System areas                                              |    16#xy14   | 
Block types                                               |    16#xy15   | 
CPU information                                           |    16#xy1C   | 
Interrupt status                                          |    16#xy22   | 
Assignment between process image partitions and OBs       |    16#xy25   |
Communication status data                                 |    16#xy32   |
H CPU group information                                   |    16#xy71   |
Status of the module LEDs                                 |    16#xy74   | 
Switched DP slaves in the H-system                        |    16#xy75   |
Module status information                                 |    16#xy91   |
Rack / station status information                         |    16#xy92   |
Rack / station status information                         |    16#xy94   |
Extended DP master system / PROFINET IO system information|    16#xy95   |
Module status information, PROFINET IO and PROFIBUS DP    |    16#xy96   |
Tool changer information (PROFINET IO)                    |    16#xy9C   |
Diagnostic buffer of the CPU                              |    16#xyA0   | 
Module diagnostic information (data record 0)             |    16#xyB1   |
Module diagnostic information (data record 1),geographical address |    16#xyB2   |
Module diagnostic information (data record 1), logical address|    16#xyB3   |
Diagnostic data of a DP slave                             | 16#xyB4   |

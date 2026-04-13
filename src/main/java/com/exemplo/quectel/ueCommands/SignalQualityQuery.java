package com.exemplo.quectel.ueCommands;

// SignalQualityCommand.java
//This command indicates the received signal strength <RSSI>
public class SignalQualityQuery extends ExtendedCommand {
    public SignalQualityQuery() {
        super("+CSQ"); // AT+CSQ -> +CSQ: <rssi>,<ber> then OK Se retonar rssi = 99 => sem medição.
        this.typeOfAnswer = "+CSQ:";
    }
}

// Veja respostas possível na página 89 do manual :

/*
 * RG520N&RG525F&RG5x0F
 * &RM5x0N&RM521F Series
 * AT Commands Manual
 * 
 * 5G Module Series
 * Version: 1.0
 * Date: 2024-02-07
 */

// ->->->-> This command only takes effect under WCDMA and LTE, and does not
// apply to 5G.
package com.exemplo.quectel.ueCommands;

/*This command returns information about the current operators and their status, and allows automatic or
manual network selection.
The Test Command returns a set or sets of five parameters, each set representing an operator present in
the network. Any of the formats may be unavailable and should then be an empty field. The list of
operators shall be in the order of: home network, networks referenced in (U)SIM and other networks.
The Read Command returns the current network registration/deregistration mode and the currently
selected operator. If no operator is selected, <format>, <oper> and <AcT> are omitted.
The Write Command forces an attempt to select and register the GSM/UMTS/EPS/5G network operator.
If the selected operator is not available, no other operator shall be selected (except <mode>=4). The
format of selected operator name shall apply to further Read Command (AT+COPS?).*/

// OperatorQueryCommand.java
public class OperatorSelection extends ExtendedCommand {
    public OperatorSelection() {
        super("+COPS=0"); // AT+COPS=? -> +COPS: (...)
        this.typeOfAnswer = "OK";
    }
}

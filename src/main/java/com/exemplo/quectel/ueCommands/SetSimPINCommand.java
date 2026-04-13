package com.exemplo.quectel.ueCommands;

/*This command sends to the MT a password that is necessary before it can be operated, or queries
whether MT requires a password before it can be operated. The password may be (U)SIM PIN, (U)SIM
PUK (PIN Unlocking Key), PH-SIM PIN, etc.*/

public class SetSimPINCommand extends ExtendedCommand {
    public SetSimPINCommand(String pin) {
        super("+CPIN=" + pin); // EX: AT+CPIN="1234"

        this.typeOfAnswer = "OK";
    }

}

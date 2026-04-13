package com.exemplo.quectel.ueCommands;

// AttachStatusCommand.java

/*This command attaches or detaches MT to/from the packet domain service. After the command has
been completed, MT remains in V.250 command state. If MT is already in the requested state, the
command will be ignored and the OK response returned. If the requested state cannot be achieved, an
ERROR or +CME ERROR response will be returned.*/

public class AttachStatusQuery extends ExtendedCommand {
    public AttachStatusQuery() {
        super("+CGATT?"); // AT+CGATT? -> +CGATT: 0/1 (0 = detached, 1 = attached)
        this.typeOfAnswer = "+CGATT:";
    }
}

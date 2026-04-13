package com.exemplo.quectel.ueCommands;

/*This command powers off MT. Once the command is executed successfully, UE returns OK immediately
and deactivates the network. After the deactivation is completed, UE outputs POWERED DOWN and
enters power-off state. The maximum time for unregistering network is 60 seconds. To avoid data loss,
the power supply for the module cannot be disconnected before POWERED DOWN is outputted. */

public class PowerOffCommand extends ExtendedCommand {
    public PowerOffCommand() {
        super("+QPOWD=1");
        this.typeOfAnswer = "POWERED DOWN";
    }
}

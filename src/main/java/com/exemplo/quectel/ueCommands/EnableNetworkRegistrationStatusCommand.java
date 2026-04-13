package com.exemplo.quectel.ueCommands;

/*The Read Command returns the presentation of URC (Unsolicited Result Code) and an integer <stat>
which shows whether the network has currently indicated the registration of MT. Location information
parameters <lac> and <ci> are returned only when <n>=2 and MT is registered on the network.
The Write Command sets whether to return an URC or not and controls the presentation of URC +CREG:
<stat> when <n>=1 and there is a change in the MT network registration status.*/

public class EnableNetworkRegistrationStatusCommand extends ExtendedCommand {
    public EnableNetworkRegistrationStatusCommand() {
        super("+CREG=1");
        this.typeOfAnswer = "OK";
    }

}

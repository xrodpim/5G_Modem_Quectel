package com.exemplo.quectel.ueCommands;

//MT returns an alphanumeric string indicating if a password is required.

// SimPinQueryCommand.java
public class SimPinQuery extends ExtendedCommand {
    public SimPinQuery() {
        super("+CPIN?"); // AT+CPIN? -> +CPIN: READY / SIM PIN / SIM PUK
        this.typeOfAnswer = "+CPIN:";
    }

    public void handleResponse() {

        // System.out.println(response);

        if (response.contains("READY")) {

            System.out.println("SIMCard pronto para uso. Não requer PIN code.");

        } else if (response.contains("SIM PIN")) {
            System.out.println(" MT is waiting for (U)SIM PIN");
        } else if (response.contains("SIM PUK")) {
            System.out.println(" MT is waiting for (U)SIM PUK");
        } else if (response.contains("SIM PIN2")) {
            System.out.println(" MT is waiting for (U)SIM PIN2");
        } else if (response.contains("SIM PUK2")) {
            System.out.println(" MT is waiting for (U)SIM PUK2");
        } else if (response.contains("PH-NET PIN")) {
            System.out.println(" MT is waiting for network personalization password");
        } else if (response.contains("PH-NET PUK")) {
            System.out.println(" MT is waiting for network personalization unlocking password");
        } else if (response.contains("PH-NETSUB PIN")) {
            System.out.println(" MT is waiting for network subset personalization password");
        } else if (response.contains("PH-NETSUB PUK")) {
            System.out.println(" MT is waiting for network subset personalization unlocking password");
        } else if (response.contains("PH-SP PIN")) {
            System.out.println(" MT is waiting for service provider personalization password");
        } else if (response.contains("PH-SP PUK")) {
            System.out.println(" MT is waiting for service provider personalization unlocking password");
        } else if (response.contains("PH-CORP PIN")) {
            System.out.println(" MT is waiting for corporate personalization password");
        } else if (response.contains("PH-CORP PUK")) {
            System.out.println(" MT is waiting for corporate personalization unlocking password");
        }

        else {
            System.out.println("Não foi possivel usar o SIMCard. Verifique se ele requer PIN code.");

        }
    }

}

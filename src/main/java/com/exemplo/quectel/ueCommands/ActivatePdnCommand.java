package com.exemplo.quectel.ueCommands;

// ActivatePdnCommand.java
public class ActivatePdnCommand extends ExtendedCommand {
    public ActivatePdnCommand() {
        super("+QIACT=1"); // Quectel: ativa PDP/PDN (dependendo do CID/APN configurado)
        // resposta típica: "OK" e/ou "+QIACT: <cid>,<addr>" — aceitaremos OK
        this.typeOfAnswer = "OK";
    }
}

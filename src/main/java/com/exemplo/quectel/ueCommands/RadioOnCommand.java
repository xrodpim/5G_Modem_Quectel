package com.exemplo.quectel.ueCommands;

////This command controls UE functionality level. It can also be used for resetting UE.

// RadioOnCommand.java
public class RadioOnCommand extends ExtendedCommand {
    public RadioOnCommand() {
        super("+CFUN=1"); // envia AT+CFUN=1
        // normalmente retorna "OK" e depois URCs; aceitamos OK como sinal imediato
        // provavelmente retorna "+CPIN: NOT INSERTED" se o cartão não estiver inserido.
        // Insira o cartão (SIMcard) antes de usar esse comando.
        this.typeOfAnswer = "OK";
    }
}

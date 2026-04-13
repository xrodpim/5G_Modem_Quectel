package com.exemplo.quectel.ueCommands;

// CheckCfunCommand.java

//This command controls UE functionality level. It can also be used for resetting UE.

public class CheckCfunQuery extends ExtendedCommand {
    public CheckCfunQuery() {
        super("+CFUN?"); // consulta modo CFUN: resposta tipo "+CFUN: <mode>"
        this.typeOfAnswer = "+CFUN:"; // procure por substring "+CFUN:"
    }
}

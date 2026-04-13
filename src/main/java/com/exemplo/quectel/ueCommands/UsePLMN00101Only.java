package com.exemplo.quectel.ueCommands;

//Esse comando obriga o modem considerar apenas rede com MCC 001 e MNC 01 (PLMN de teste).

public class UsePLMN00101Only extends ExtendedCommand {
    public UsePLMN00101Only(String _MCC, String _MNC) {
        super("+COPS=1,2,\""+_MCC + _MNC +"\""); // EX: Set PLMN 00101 (Test PLMN). AT+COPS=1,2,"00101" Se falhar deixa o UE
                                      // escolher
                                      // automaticamente.
                                      // AT+COPS=1,2,"00101" obriga registrar com PLMN 00101 e não tenta em outra
                                      // célula se falhar nessa.
        this.typeOfAnswer = "OK";
    }

}

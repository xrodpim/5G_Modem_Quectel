package com.exemplo.quectel.ueCommands;

// This command obtains the network information, such as serving cell and
// neighbour cell.

// Exemplos de respostas para serving cell:
//
// In WCDMA mode:
// +QENG: "servingcell",<state>,"WCDMA",<MCC>,<MNC>,
// <LAC>,<cellID>,<uarfcn>,<PSC>,<RAC>,<RSCP>,<ecio>,
// <phych>,<SF>,<slot>,<speech_code>,<comMod>

// In SA mode:
// +QENG: "servingcell",<state>,"NR5G-SA",<duplex_mod
// e>,<MCC>,<MNC>,<cellID>,<PCID>,<TAC>,<ARFCN>,<ba
// nd>,<NR_DL_bandwidth>,<RSRP>,<RSRQ>,<SINR>,<sc
// s>,<srxlev>

// Exemplos de respostas para neighbour cell:
// In WCDMA mode:
// [+QENG:"neighbourcell","WCDMA",<uarfcn>,<srxqual>,
// <PSC>,<RSCP>,<ecno>,<set>,<rank>,<srxlev>]

public class NeighbourCellInformationQuery extends ExtendedCommand {
    public NeighbourCellInformationQuery() {
        super("+QENG=\"neighbourcell\"");
        this.typeOfAnswer = "OK";
    }
}

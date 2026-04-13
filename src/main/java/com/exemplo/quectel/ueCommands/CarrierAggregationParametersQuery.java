package com.exemplo.quectel.ueCommands;

//This command queries carrier aggregation parameters.

public class CarrierAggregationParametersQuery extends ExtendedCommand {
    public CarrierAggregationParametersQuery() {
        super("+QCAINFO");
        this.typeOfAnswer = "OK";
    }

}

// Exemplo:
// AT+QCAINFO
// +QCAINFO: "PCC",300,100,"LTE BAND 1",1,23,-66,-12,-34,30
// +QCAINFO: "SCC",1575,100,"LTE BAND 3",2,43,-64,-7,-24,30,0,-,-
// OK

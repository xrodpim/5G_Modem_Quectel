package com.exemplo.quectel.ueCommands;

import java.util.List;

import com.exemplo.quectel.metrics.Metrics;

public class RSRQ_Query extends ExtendedCommand {
    Metrics _metrics;

    public RSRQ_Query() {
        super("+QRSRQ");
        this.typeOfAnswer = "OK";

    }

    public void setMetrics(Metrics metrics) {
        _metrics = metrics;
    }

    public void handleResponse() {

        System.out.println(response);

        if (response.contains("+QRSRQ")) {

            // Nesse ponto da execução, coletar métrica de interesse: //QRSRQ.
            // etc.

            this.atualizarRSRQFromQrsrq(response, _metrics);

        } else {

        }

    }

    /**
     * Atualiza RSRQ_PRX e RSRQ_DRX a partir de +QRSRQ:
     * <PRX>,<DRX>,<RX2>,<RX3>,<sysmode>
     */
    private boolean atualizarRSRQFromQrsrq(String line, Metrics metrics) {
        if (line == null || metrics == null)
            return false;
        if (line.toLowerCase().contains("no service"))
            return false;

        int colon = line.indexOf(':');
        String payload = colon >= 0 ? line.substring(colon + 1) : line;
        List<String> tokens = splitRespectingQuotes(payload);
        if (tokens.size() < 5)
            return false;

        for (int i = 0; i < tokens.size(); i++)
            tokens.set(i, stripQuotes(tokens.get(i).trim()));

        String prxTok = tokens.get(0);
        String drxTok = tokens.get(1);
        String rx2Tok = tokens.get(2);
        String rx3Tok = tokens.get(3);

        Integer prxVal = null;
        Integer drxVal = null;
        Integer rx2Val = null;
        Integer rx3Val = null;

        try {
            if (!"-".equals(prxTok))
                prxVal = Integer.valueOf(prxTok);
            if (!"-".equals(drxTok))
                drxVal = Integer.valueOf(drxTok);
            if (!"-".equals(rx2Tok))
                rx2Val = Integer.valueOf(rx2Tok);
            if (!"-".equals(rx3Tok))
                rx3Val = Integer.valueOf(rx3Tok);
        } catch (NumberFormatException nfe) {
            return false;
        }

        boolean updated = false;
        if (prxVal != null) {
            metrics.setRSRQ_PRX(prxVal);
            updated = true;
        }
        if (drxVal != null) {
            metrics.setRSRQ_DRX(drxVal);
            updated = true;
        }
        if (rx2Val != null) {
            metrics.setRSRQ_RX2(rx2Val);
            updated = true;
        }
        if (rx3Val != null) {
            metrics.setRSRQ_RX3(rx3Val);
            updated = true;
        }
        return updated;
    }
}

// The command queries and reports the RSRQ of the current service network.
// Interesting Response format :
// +QRSRQ: <PRX>,<DRX>,<RX2>,<RX3>,<sysmode>

// PRX path RSRQ value. Range: -20 to -3 dB.
// DRX path RSRQ value. Range: -20 to -3 dB.
// RX2 path RSRQ value. Range: -20 to -3 dB.
// RX3 path RSRQ value. Range: -20 to -3 dB.
// <Sysmode> String type. It indicates the service mode in which the MT will
// report the RSRQ:
// LTE -> LTE mode
// NR5G -> 5G mode

// NOTE:

// This command is only supported in LTE and 5G.

// If the queried <PRX>, <DRX>, <RX2> or <RX3> is -32768, it indicates that the
// RSRQ value is
// invalid.

// This command is strongly related to the RF link and is generally only used
// for customer reference
// and cannot be used as a sensitivity test. In addition, it is best to use it
// when measuring the speed,
// the results are more accurate.

// Example:

// AT+QRSRQ

// +QRSRQ: -16,-19,-19,-15,LTE
// OK
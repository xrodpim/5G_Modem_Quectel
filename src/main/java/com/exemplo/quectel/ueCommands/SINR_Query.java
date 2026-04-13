package com.exemplo.quectel.ueCommands;

import java.util.List;

import com.exemplo.quectel.metrics.Metrics;

public class SINR_Query extends ExtendedCommand {
    Metrics _metrics;

    public SINR_Query() {
        super("+QSINR");
        this.typeOfAnswer = "OK";

    }

    public void setMetrics(Metrics metrics) {
        _metrics = metrics;
    }

    public void handleResponse() {

        System.out.println(response);

        if (response.contains("+QSINR")) {

            // Nesse ponto da execução, coletar métrica de interesse: //QSINR.
            // etc.

            this.atualizarSINRFromQsinr(response, _metrics);

        } else {

        }
    }

    /**
     * Atualiza SINR_PRX, SINR_DRX, SINR_RX2 e SINR_RX3 a partir de +QSINR:
     * <PRX>,<DRX>,<RX2>,<RX3>,<sysmode>
     */
    private boolean atualizarSINRFromQsinr(String line, Metrics metrics) {
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
            metrics.setSINR_PRX(prxVal);
            updated = true;
        }
        if (drxVal != null) {
            metrics.setSINR_DRX(drxVal);
            updated = true;
        }
        if (rx2Val != null) {
            metrics.setSINR_RX2(rx2Val);
            updated = true;
        }
        if (rx3Val != null) {
            metrics.setSINR_RX3(rx3Val);
            updated = true;
        }
        return updated;
    }

}

// The command queries and reports the SINR of the current service network.

// Interesting response:
// Response
// +QSINR: <PRX>,<DRX>,<RX2>,<RX3>,<sysmode>

// Integer type. PRX path SINR value. Range: -20 to 30 dB in LTE, -23 to 40 dB
// in 5G.
// Integer type. DRX path SINR value. Range: -20 to 30 dB in LTE, -23 to 40 dB
// in 5G.
// Integer type. RX2 path SINR value. Range: -20 to 30 dB in LTE, -23 to 40 dB
// in 5G.
// Integer type. RX3 path SINR value. Range: -20 to 30 dB in LTE, -23 to 40 dB
// in 5G.
// String type. It indicates the service mode in which the MT will report the
// SINR.
// LTE -> LTE mode
// NR5G -> 5G mode

// This command is only supported in LTE and 5G.

// If the queried <PRX>, <DRX>, <RX2> or <RX3> is -32768, it indicates that the
// SINR value is
// invalid.

// This command is strongly related to the RF link and is generally only used
// for customer reference
// and cannot be used as a sensitivity test. In addition, it is best to use it
// when measuring the speed,
// the results are more accurate.

// Example:

// AT+QSINR

// +QSINR: -3,-7,-1,-2,LTE
// OK

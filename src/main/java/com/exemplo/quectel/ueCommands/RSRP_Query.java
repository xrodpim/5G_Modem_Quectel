package com.exemplo.quectel.ueCommands;

import java.util.List;

import com.exemplo.quectel.metrics.Metrics;

public class RSRP_Query extends ExtendedCommand {
    Metrics _metrics;

    public RSRP_Query() {
        super("+QRSRP");
        this.typeOfAnswer = "OK";
    }

    public void setMetrics(Metrics metrics) {
        _metrics = metrics;
    }

    public void handleResponse() {

        System.out.println(response);

        if (response.contains("+QRSRP")) {

            // Nesse ponto da execução, coletar métrica de interesse: //QRSRP.
            // etc.

            this.atualizarRSRPFromQrsrp(response, _metrics);

        } else {

        }

    }

    /**
     * Atualiza RSRP_PRX e RSRP_DRX a partir de uma linha +QRSRP:
     * <PRX>,<DRX>,<RX2>,<RX3>,<sysmode>
     * Retorna true se alguma atualização foi realizada; false se a linha for
     * inválida ou se nada foi atualizado.
     */
    private boolean atualizarRSRPFromQrsrp(String line, Metrics metrics) {
        if (line == null || metrics == null)
            return false;
        if (line.toLowerCase().contains("no service"))
            return false;

        int colon = line.indexOf(':');
        String payload = colon >= 0 ? line.substring(colon + 1) : line;
        List<String> tokens = splitRespectingQuotes(payload);
        if (tokens.size() < 5)
            return false; // precisa ter pelo menos 5 campos

        // normalize e strip quotes
        for (int i = 0; i < tokens.size(); i++)
            tokens.set(i, stripQuotes(tokens.get(i).trim()));

        // tokens[0] = PRX, tokens[1] = DRX
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
            return false; // parsing failed -> nada atualiza
        }

        // só grava após toda validação
        boolean updated = false;
        if (prxVal != null) {
            metrics.setRSRP_PRX(prxVal);
            updated = true;
        }
        if (drxVal != null) {
            metrics.setRSRP_DRX(drxVal);
            updated = true;
        }
        if (rx2Val != null) {
            metrics.setRSRP_RX2(rx2Val);
            updated = true;
        }
        if (rx3Val != null) {
            metrics.setRSRP_RX3(rx3Val);
            updated = true;
        }
        return updated;
    }

}

// The command queries and reports the RSRP of the current service network.
// Interesting Response format :
// +QRSRP: <PRX>,<DRX>,<RX2>,<RX3>,<sysmode>

// PRX path RSRP value. Range: -140 to -44 dBm.
// DRX path RSRP value. Range: -140 to -44 dBm.
// RX2 path RSRP value. Range: -140 to -44 dBm.
// RX3 path RSRP value. Range: -140 to -44 dBm.
// <Sysmode> String type. It indicates the service mode in which the MT will
// report the RSRP:
// LTE -> LTE mode
// NR5G -> 5G mode

// NOTE:

// This command is only supported in LTE and 5G.

// If the queried <PRX>, <DRX>, <RX2> or <RX3> is -32768, it indicates that the
// RSRP value is
// invalid.

// This command is strongly related to the RF link and is generally only used
// for customer reference
// and cannot be used as a sensitivity test. In addition, it is best to use it
// when measuring the speed,
// the results are more accurate.

// Example:

// AT+QRSRP
// +QRSRP: -101,-105,-105,-99,LTE
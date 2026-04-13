package com.exemplo.quectel.ueCommands;

import java.util.List;

import com.exemplo.quectel.metrics.Metrics;

//This command gets average uplink rate and downlink rate in delta time.

public class UpDownLinkRateQuery extends ExtendedCommand {
    Metrics _metrics;

    public UpDownLinkRateQuery() {
        super("+QNWCFG=\"up/down\"");
        this.typeOfAnswer = "OK";
    }

    public void setMetrics(Metrics metrics) {
        _metrics = metrics;
    }

    public void handleResponse() {

        System.out.println(response);

        if (response.contains("+QNWCFG")) {

            // Nesse ponto da execução, coletar métrica de interesse: //QNWCFG.
            // etc.

            this.atualizarUpDownFromQnwcfg(response, _metrics);

        } else {

        }

    }

    /**
     * Atualiza uplink e downlink a partir de +QNWCFG: "up/down",<...>
     * O primeiro token entre aspas deve ter formato NNN/NNN (ou "-" para pular).
     * Retorna true se alguma atualização foi feita.
     */
    private boolean atualizarUpDownFromQnwcfg(String line, Metrics metrics) {
        if (line == null || metrics == null)
            return false;
        if (line.toLowerCase().contains("no service"))
            return false;

        int colon = line.indexOf(':');
        String payload = colon >= 0 ? line.substring(colon + 1) : line;
        List<String> tokens = splitRespectingQuotes(payload);
        if (tokens.isEmpty())
            return false;

        // primeiro token deve ser "up/down" (pode estar entre aspas)
        String first = stripQuotes(tokens.get(0).trim());
        if (first.isEmpty())
            return false;

        // se for "-" então nada atualiza
        if ("-".equals(first))
            return false;

        // split 'up/down'
        String[] parts = first.split("/");
        if (parts.length != 2)
            return false;

        Integer upVal = null;
        Integer downVal = null;

        try {
            String upTok = parts[0].trim();
            String downTok = parts[1].trim();
            if (!"-".equals(upTok))
                upVal = Integer.valueOf(upTok);
            if (!"-".equals(downTok))
                downVal = Integer.valueOf(downTok);
        } catch (NumberFormatException nfe) {
            return false;
        }

        boolean updated = false;
        if (upVal != null) {
            metrics.setUplink(upVal);
            updated = true;
        }
        if (downVal != null) {
            metrics.setDownlink(downVal);
            updated = true;
        }
        return updated;
    }

}

// Exemplo:

// AT+QNWCFG="up/down" //Query the current setting.
// +QNWCFG: "up/down",2056,384,2
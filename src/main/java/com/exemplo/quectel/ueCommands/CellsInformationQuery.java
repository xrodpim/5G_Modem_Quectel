package com.exemplo.quectel.ueCommands;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.exemplo.quectel.metrics.Metrics;

//This command obtains the network information, such as serving cell and neighbour cell.

//Exemplos de respostas para serving cell:
//
//In WCDMA mode:
//+QENG: "servingcell",<state>,"WCDMA",<MCC>,<MNC>,
//<LAC>,<cellID>,<uarfcn>,<PSC>,<RAC>,<RSCP>,<ecio>,
//<phych>,<SF>,<slot>,<speech_code>,<comMod>

//In SA mode:
//+QENG: "servingcell",<state>,"NR5G-SA",<duplex_mod
//e>,<MCC>,<MNC>,<cellID>,<PCID>,<TAC>,<ARFCN>,<ba
//nd>,<NR_DL_bandwidth>,<RSRP>,<RSRQ>,<SINR>,<sc
//s>,<srxlev>

//Exemplos de respostas para neighbour cell:
//In WCDMA mode:
//[+QENG:"neighbourcell","WCDMA",<uarfcn>,<srxqual>,
//<PSC>,<RSCP>,<ecno>,<set>,<rank>,<srxlev>]

public class CellsInformationQuery extends ExtendedCommand {

    int vezesAcampadoNaRedeDeTEstes = 0;
    int vezesAcampadoEmOperadora = 0;
    Metrics _metrics;

    public CellsInformationQuery() {
        super("+QENG=\"servingcell\"");
        this.typeOfAnswer = "OK";
    }

    public void setMetrics(Metrics metrics) {
        _metrics = metrics;
    }

    /**
     * Atualiza os campos de data/hora do Metrics com a hora local atual da máquina.
     * Campos atualizados: hora, minuto, segundo, milessegundo, dia, mes, ano.
     *
     * Uso: TimeUtils.atualizarTimestampLocal(metrics);
     */
    private void atualizarTimestampLocal(Metrics metrics) {
        if (metrics == null) {
            throw new IllegalArgumentException("metrics não pode ser null");
        }

        // Obtém tempo local (data e hora) da máquina
        LocalDateTime now = LocalDateTime.now(); // usa ZoneId.systemDefault()

        // Preenche ano, mes, dia, hora, minuto, segundo
        metrics.setAno(now.getYear());
        metrics.setMes(now.getMonthValue()); // 1..12
        metrics.setDia(now.getDayOfMonth()); // 1..31
        metrics.setHora(now.getHour()); // 0..23
        metrics.setMinuto(now.getMinute()); // 0..59
        metrics.setSegundo(now.getSecond()); // 0..59

        // Para milissegundo, usa Instant (epoch milli) e pega os últimos 3 dígitos
        long epochMillis = Instant.now().toEpochMilli();
        int milli = (int) (epochMillis % 1000);
        metrics.setMilessegundo(milli);
    }

    public void handleResponse() {

        System.out.println(response);

        String MCC;
        String MNC;
        MCC = _metrics.getMCC();
        MNC = _metrics.getMNC();

        if (response.contains(MCC + "," + MNC + ",")) {

            // if (response.contains("001,01,000000E00")) {
            vezesAcampadoNaRedeDeTEstes++;
            System.out.println("Acampado na rede de testes 00101. Vezes: " + vezesAcampadoNaRedeDeTEstes);
            System.out.println("NÃO está acampado na rede de testes 00101. Vezes: " + vezesAcampadoEmOperadora);

            // Nesse ponto da execução, coletar métrica de interesse: //RSRP, RSRQ, SINR e
            // timestamp.
            atualizarTimestampLocal(_metrics);
            atualizarMetricsFromQeng(response, _metrics);

        } else {
            vezesAcampadoEmOperadora++;
            System.out.println("Acampado na rede de testes 00101. Vezes: " + vezesAcampadoNaRedeDeTEstes);
            System.out.println("NÃO está acampado na rede de testes 00101. Vezes: " + vezesAcampadoEmOperadora);
        }

    }

    /**
     * Atualiza os campos do Metrics a partir de uma linha +QENG no formato
     * especificado.
     *
     * Formato esperado (pós o prefixo +QENG:):
     * "servingcell",<state>,"NR5G-SA",<duplex_mode>,<MCC>,<MNC>,<cellID>,<PCID>,<TAC>,<ARFCN>,<band>,<NR_DL_bandwidth>,<RSRP>,<RSRQ>,<SINR>,<scs>,<srxlev>
     *
     * Exemplo:
     * +QENG:
     * "servingcell","NOCONN","NR5G-SA","TDD",001,01,000000E00,0,1,641280,78,3,-92,-11,21,1,38
     *
     * @param qengLine a linha completa começando por "+QENG:" (ou somente a parte
     *                 após ':')
     * @param metrics  o objeto Metrics a ser atualizado (não pode ser null)
     * @throws IllegalArgumentException se a linha não tiver o número mínimo de
     *                                  campos
     * @throws NumberFormatException    se algum campo numérico estiver com formato
     *                                  inválido
     */
    private void atualizarMetricsFromQeng(String qengLine, Metrics metrics) {
        if (metrics == null) {
            throw new IllegalArgumentException("metrics não pode ser null");
        }
        if (qengLine == null) {
            throw new IllegalArgumentException("qengLine não pode ser null");
        }

        // Remove prefixo até ':' se presente (para aceitar tanto a linha completa
        // quanto só a parte após ':')
        int colonIndex = qengLine.indexOf(':');
        String payload = (colonIndex >= 0) ? qengLine.substring(colonIndex + 1) : qengLine;

        // Faz o split CSV que respeita aspas
        List<String> tokens = _splitRespectingQuotes(payload);

        // Remover spaces e aspas de cada token
        for (int i = 0; i < tokens.size(); i++) {
            tokens.set(i, _stripQuotes(tokens.get(i).trim()));
        }

        // Precisamos ao menos dos campos até index 16 (0..16 -> 17 tokens)
        if (tokens.size() < 17) {
            throw new IllegalArgumentException("Linha QENG tem número insuficiente de campos: encontrado "
                    + tokens.size() + ", esperava pelo menos 17. Linha: " + qengLine);
        }

        // Mapeamento de índices (após o split):
        // 0 -> "servingcell"
        // 1 -> <state>
        // 2 -> "NR5G-SA"
        // 3 -> <duplex_mode>
        // 4 -> <MCC>
        // 5 -> <MNC>
        // 6 -> <cellID> <-- string
        // 7 -> <PCID>
        // 8 -> <TAC>
        // 9 -> <ARFCN> <-- int
        // 10 -> <band> <-- int
        // 11 -> <NR_DL_bandwidth> <-- int
        // 12 -> <RSRP> <-- int
        // 13 -> <RSRQ> <-- int
        // 14 -> <SINR> <-- int
        // 15 -> <scs> <-- int
        // 16 -> <srxlev> <-- int

        // Extrai e seta no metrics (faz parse dos inteiros)
        String cellID = tokens.get(6);
        int arfcn = Integer.parseInt(tokens.get(9));
        int band = Integer.parseInt(tokens.get(10));
        int nrDlBw = Integer.parseInt(tokens.get(11));
        int rsrp = Integer.parseInt(tokens.get(12));
        int rsrq = Integer.parseInt(tokens.get(13));
        int sinr = Integer.parseInt(tokens.get(14));
        int scs = Integer.parseInt(tokens.get(15));
        int srxlev = Integer.parseInt(tokens.get(16));

        // Atribuições no bean
        metrics.setCellID(cellID);
        metrics.setARFCN(arfcn);
        metrics.setBand(band);
        metrics.setNR_DL_bandwidth(nrDlBw);
        metrics.setRSRP(rsrp);
        metrics.setRSRQ(rsrq);
        metrics.setSINR(sinr);
        metrics.setScs(scs);
        metrics.setSrxlev(srxlev);
    }

    // ----------------- Helpers -----------------

    /**
     * Remove aspas iniciais/finais de uma string (se existirem).
     */

    private String _stripQuotes(String s) {
        if (s == null)
            return null;
        if (s.length() >= 2 && ((s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"')
                || (s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\''))) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    private List<String> _splitRespectingQuotes(String input) {
        List<String> tokens = new ArrayList<>();
        if (input == null || input.isEmpty())
            return tokens;

        // Considerar apenas a primeira linha (desprezar o restante)
        String firstLine = input.split("\\r?\\n", 2)[0];

        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        char quoteChar = 0;

        for (int i = 0; i < firstLine.length(); i++) {
            char c = firstLine.charAt(i);

            if (inQuotes) {
                if (c == quoteChar) {
                    // Trata aspas escapadas: "" dentro de um campo citado -> um caractere "
                    if (i + 1 < firstLine.length() && firstLine.charAt(i + 1) == quoteChar) {
                        cur.append(quoteChar);
                        i++; // consome a aspa escapada extra
                    } else {
                        // Fecha a citação (não adiciona a aspa ao token)
                        inQuotes = false;
                    }
                } else {
                    cur.append(c);
                }
            } else {
                if (c == '"' || c == '\'') {
                    // inicia citação (não adiciona a aspa ao token)
                    inQuotes = true;
                    quoteChar = c;
                } else if (c == ',') {
                    // separador fora de aspas
                    String token = cur.toString().trim();
                    if ("-".equals(token))
                        token = "0"; // normaliza "-" para "0"
                    tokens.add(token);
                    cur.setLength(0);
                } else {
                    cur.append(c);
                }
            }
        }

        // adiciona último token (trim aplicado + normalização de "-")
        String last = cur.toString().trim();
        if ("-".equals(last))
            last = "0";
        tokens.add(last);
        return tokens;
    }

}

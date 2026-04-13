package com.exemplo.quectel.ueCommands;

import java.util.ArrayList;
import java.util.List;

/*This command queries the network registration status and controls the presentation of URC +C5GREG:
<stat> when <n>=1 and there is a change in the MT’s network registration status in 5GS.
It also controls the presentation of URC +C5GREG:
<stat>[,[<tac>],[<ci>],[<AcT>],[<Allowed_NSSAI_length>],[<Allowed_NSSAI>]] when <n>=2 and
there is a change of the network cell in 5GS or when the network provides an allowed NSSAI. The
parameters <AcT>, <tac>, <ci>, <Allowed_NSSAI_length> and <Allowed_NSSAI> are included only if
available.*/

// RegistrationStatusCommand.java
public class RegistrationStatusQuery extends ExtendedCommand {
    public RegistrationStatusQuery() {
        // CEREG é o recomendado para EPS/5G contexts; se seu firmware usar CREG/CGREG
        // ajuste
        super("+C5GREG?");
        this.typeOfAnswer = "OK";
    }

    public int handleTheResponse() {

        // System.out.println(response);

        if (response.contains("+C5GREG:")) {

            return this.parseTheResponse(response);

        } else {
            System.out.println("No +C5GREG response received.");
            return 0;

        }
    }

    private int parseTheResponse(String response) {
        if (response == null || response.trim().isEmpty()) {
            System.out.println("Resposta vazia.");
            return 0;
        }

        // pega apenas a primeira linha (despreza o resto)
        String firstLine = response.split("\\r?\\n", 2)[1].trim();

        // procura o prefixo +C5GREG:
        int colon = firstLine.indexOf(':');
        if (colon < 0 || !firstLine.substring(0, colon).toUpperCase().contains("+C5GREG")) {
            System.out.println("Linha não parece ser um +C5GREG: " + firstLine);
            return 0;
        }

        String payload = firstLine.substring(colon + 1).trim();
        List<String> tokens = splitFirstLineRespectingQuotes(payload);

        if (tokens.size() < 1) {
            System.out.println("Sem campos após +C5GREG.");
            return 0;
        }

        // extrai n e stat (se existirem)
        String nStr = tokens.size() > 0 ? tokens.get(0) : "";
        String statStr = tokens.size() > 1 ? tokens.get(1) : "";

        Integer n = tryParseInt(nStr);
        Integer stat = tryParseInt(statStr);

        // imprime n
        /*
         * System.out.println("Campo <n> encontrado: " + nStr);
         * if (n == null) {
         * System.out.println("  (não foi possível interpretar <n> como inteiro)");
         * } else {
         * switch (n) {
         * case 0:
         * System.out.println(
         * "  Significado: 0 = Desabilitar URC de registro de rede (Disable network registration URC)."
         * );
         * break;
         * case 1:
         * System.out.println(
         * "  Significado: 1 = Habilitar URC de registro de rede: +C5GREG:<stat> (apenas status)."
         * );
         * break;
         * case 2:
         * System.out.println(
         * "  Significado: 2 = Habilitar URC de registro de rede e informação de localização: +C5GREG:<stat>[,[<tac>],[<ci>],[<AcT>],[<Allowed_NSSAI_length>],[<Allowed_NSSAI>]]."
         * );
         * break;
         * default:
         * System.out.
         * println("  Significado: valor <n> desconhecido/fora de especificação: " + n);
         * break;
         * }
         * }
         */

        // imprime stat
        System.out.println("Campo <stat> encontrado (status de registro na rede): " + statStr);
        if (stat == null) {
            System.out.println("  (não foi possível interpretar <stat> como inteiro)");
        } else {
            switch (stat) {
                case 0:
                    System.out.println(
                            "  Significado: 0 = Não registrado. O UE não está procurando por operador para se registrar. Tente novamente.");
                    return 1;
                case 1:
                    System.out.println("  Significado: 1 = Registrado. Rede doméstica.");
                    break;
                case 2:
                    System.out.println(
                            "  Significado: 2 = Não registrado. O UE está tentando anexar ou procurando um operador para se registrar...");
                    break;
                case 3:
                    System.out.println("  Significado: 3 = Registro negado.");
                    break;
                case 4:
                    System.out.println("  Significado: 4 = Desconhecido.");
                    break;
                case 5:
                    System.out.println("  Significado: 5 = Registrado. Roaming.");
                    break;
                case 8:
                    System.out.println("  Significado: 8 = Registrado apenas para serviços de emergência.");
                    break;
                default:
                    System.out.println("  Significado: status de registro NR desconhecido: " + stat);
                    break;
            }
        }

        // se houver TAC e CI, imprime também (opcional)
        if (tokens.size() >= 4) {
            String tac = stripQuotes(tokens.get(2));
            String ci = stripQuotes(tokens.get(3));
            System.out.println("TAC  (se presente): " + tac);
            System.out.println("CI   (se presente): " + ci);
        } else if (tokens.size() == 3) {
            String tac = stripQuotes(tokens.get(2));
            System.out.println("TAC (se presente): " + tac);
        }

        System.out.println("-------------------------------------------"); // linha em branco no final
        return 0;
    }

    /* ----------------- Helpers ----------------- */

    private Integer tryParseInt(String s) {
        if (s == null)
            return null;
        s = s.trim();
        if (s.isEmpty())
            return null;
        // remove aspas se houver
        s = stripQuotes(s);
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /*
     * private String stripQuotes(String s) {
     * if (s == null)
     * return null;
     * s = s.trim();
     * if (s.length() >= 2 && ((s.charAt(0) ==
     * '"' && s.charAt(s.length() - 1) == '"')
     * || (s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\''))) {
     * return s.substring(1, s.length() - 1);
     * }
     * return s;
     * }
     */

    /**
     * Faz split em vírgulas respeitando aspas (apenas na primeira linha).
     * Retorna tokens já trimados (sem espaços externos) e sem aspas externas.
     */
    private List<String> splitFirstLineRespectingQuotes(String input) {
        List<String> tokens = new ArrayList<>();
        if (input == null || input.isEmpty())
            return tokens;

        String firstLine = input.split("\\r?\\n", 2)[0];
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        char quoteChar = 0;

        for (int i = 0; i < firstLine.length(); i++) {
            char c = firstLine.charAt(i);
            if (inQuotes) {
                if (c == quoteChar) {
                    // aspas escapadas: "" -> "
                    if (i + 1 < firstLine.length() && firstLine.charAt(i + 1) == quoteChar) {
                        cur.append(quoteChar);
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    cur.append(c);
                }
            } else {
                if (c == '"' || c == '\'') {
                    inQuotes = true;
                    quoteChar = c;
                } else if (c == ',') {
                    String tok = cur.toString().trim();
                    tokens.add(tok);
                    cur.setLength(0);
                } else {
                    cur.append(c);
                }
            }
        }

        String last = cur.toString().trim();
        tokens.add(last);
        // remove aspas externas de cada token
        List<String> out = new ArrayList<>(tokens.size());
        for (String t : tokens)
            out.add(stripQuotes(t));
        return out;
    }

}

/*
 * Respostas:
 * 0 Disable network registration URC
 * 1 Enable network registration URC +C5GREG:<stat>
 * 2 Enable network registration and location information URC +C5GREG:
 * 
 * 
 * 
 * 0 Not registered. MT is currently not searching an operator to registerto.
 * 1 Registered. Home network.
 * 2 Not registered. MT is currently trying to attach or searching an operator
 * to register to.
 * 3 Registration denied.
 * 4 Unknown
 * 5 Registered. Roaming.
 * 8 Registered for emergency services only.
 */

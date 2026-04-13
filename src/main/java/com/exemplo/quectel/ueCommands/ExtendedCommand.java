package com.exemplo.quectel.ueCommands;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ExtendedCommand {

    // Any AT command to be sent to the UE (Ex: Quectel RM520N)
    private String atCommandSufix = "\r";
    private String atCommandPrefix = "AT";
    private String atCommand;
    protected String typeOfAnswer;
    protected String response;

    public String getResponse() {
        return response;
    }

    public ExtendedCommand(String command) {
        this.atCommand = atCommandPrefix + command + atCommandSufix;
    }

    public byte[] getBytes() {
        return atCommand.getBytes(StandardCharsets.US_ASCII);
    }

    public void printLog() {
        System.out.println("-------------------------------------------");
        System.out.println();
        System.out.println("Enviado: " + atCommand.trim());
    }

    public boolean checkResponse(InputStream in) {
        StringBuilder sb = new StringBuilder();
        long start = System.currentTimeMillis();
        long globalTimeoutMs = 50000; // 50 segundos
        byte[] buffer = new byte[256];

        try {
            while (System.currentTimeMillis() - start < globalTimeoutMs) {
                int available;
                try {
                    available = in.available();
                } catch (Exception e) {
                    available = 0;
                }
                if (available > 0) {
                    int read = in.read(buffer, 0, Math.min(buffer.length, available));
                    if (read > 0) {
                        String chunk = new String(buffer, 0, read, StandardCharsets.US_ASCII);
                        sb.append(chunk);

                        // A resposta nesse chunk é o que interessa como resposta. Ela pode ser
                        // analisada para decidir o que fazer no modem 5G.

                        // System.out.print(chunk); // print parcial para debugging
                        if (chunk.indexOf("+CME ERROR:") >= 0) {
                            System.out.println(chunk);
                        }
                        response = chunk;

                        // checar se a resposta já contém "OK" ou "ERROR"
                        if (sb.indexOf(typeOfAnswer) >= 0) {
                            System.out.println("\n--- Recebido " + typeOfAnswer + " ---");
                            return true;
                        }
                        if (sb.indexOf("ERROR") >= 0) {
                            System.out.println(sb.toString());
                            break;
                        }
                    }
                } else {
                    // pequenas pausas para evitar busy-waiting
                    Thread.sleep(50);
                }
            }

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrompida.");
            return false;
        } catch (Exception e) {
            System.err.println("Erro durante comunicação serial: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        // Se o timeout foi atingido sem encontrar "OK" , retorna falso
        if (sb.indexOf(typeOfAnswer) < 0) {
            System.out.println("\nTimeout — resposta recebida até agora:");
            System.out.println(sb.toString());
            return false;
        }
        return true; // Isso nunca será alcançado se o timeout for atingido antes de encontrar "OK"
                     // ou "ERROR".
    }

    // ----------------- Helpers (reutilizáveis) -----------------

    /**
     * Remove aspas iniciais/finais de uma string (se existirem).
     */
    protected String stripQuotes(String s) {
        if (s == null)
            return null;
        s = s.trim();
        if (s.length() >= 2 && ((s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"')
                || (s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\''))) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    protected List<String> splitRespectingQuotes(String input) {
        List<String> tokens = new ArrayList<>();
        if (input == null || input.isEmpty())
            return tokens;

        // Considera somente a primeira linha (despreza o restante)
        String firstLine = input.split("\\r?\\n", 2)[0];

        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        char quoteChar = 0;

        for (int i = 0; i < firstLine.length(); i++) {
            char c = firstLine.charAt(i);

            if (inQuotes) {
                if (c == quoteChar) {
                    // Trata aspas escapadas: "" dentro de um campo citado -> adiciona uma aspa
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
                    // Inicia citação (não adiciona a aspa ao token)
                    inQuotes = true;
                    quoteChar = c;
                } else if (c == ',') {
                    // separador fora de aspas -> finaliza token
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

        // adiciona último token (com trim e normalização de "-")
        String last = cur.toString().trim();
        if ("-".equals(last))
            last = "0";
        tokens.add(last);
        return tokens;
    }

}

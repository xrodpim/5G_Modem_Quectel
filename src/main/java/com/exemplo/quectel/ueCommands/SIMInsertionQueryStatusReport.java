package com.exemplo.quectel.ueCommands;

import java.util.Scanner;

//This command queries (U)SIM card insertion status or determines whether (U)SIM card insertion status
//report is enabled.

public class SIMInsertionQueryStatusReport extends ExtendedCommand {
    public SIMInsertionQueryStatusReport() {
        super("+QSIMSTAT?");
        this.typeOfAnswer = "OK";
    }

    public void handleResponse() {

        // System.out.println(response);

        if (response.contains("+QSIMSTAT")) {

            this.parseResponse(response);

        } else {
            System.out.println("Não foi possível ler o status do SIMCard.");

        }
    }

    private void parseResponse(String response) {
        Scanner scanner = new Scanner(response);

        // Lê a primeira linha da resposta
        if (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            line = scanner.nextLine(); // Pula a primeira linha (comando ecoado)

            // Remove o sufixo "OK" e qualquer espaço em branco adicional
            line = line.replace("OK", "").trim();

            // Divide a linha em partes usando a vírgula como delimitador
            String[] parts = line.split(",");

            if (parts.length == 2) {
                int enable = Integer.parseInt(parts[0].substring(10).trim());
                int insertedStatus = Integer.parseInt(parts[1].trim());

                // Converte os valores para as mensagens traduzidas
                String enableMessage = convertEnableToPortuguese(enable);
                String insertedStatusMessage = convertInsertedStatusToPortuguese(insertedStatus);

                // Escreve as mensagens para o usuário final1
                System.out.println("SIM card: " + insertedStatusMessage);
                System.out.println(enableMessage);
            } else {
                System.out.println("Resposta sobre status do SIMCard incompreensível.");
            }
        } else {
            System.out.println("Resposta inválida sobre status do SIMCard.");
        }
        scanner.close();
    }

    private static String convertEnableToPortuguese(int enable) {
        switch (enable) {
            case 0:
                return "SimCard desabilitado";
            case 1:
                return "SimCard habilitado";
            default:
                return "Status Desconhecido";
        }
    }

    private static String convertInsertedStatusToPortuguese(int insertedStatus) {
        switch (insertedStatus) {
            case 0:
                return "Removido";
            case 1:
                return "Inserido";
            case 2:
                return "Desconhecido";
            default:
                return "Desconhecido";
        }
    }

}

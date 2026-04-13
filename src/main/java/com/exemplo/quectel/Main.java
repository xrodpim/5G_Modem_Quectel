package com.exemplo.quectel;

import java.util.Scanner;

import com.exemplo.quectel.util.PromptPrincipal;
import com.fazecast.jSerialComm.SerialPort;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        String portName = args.length > 0 ? args[0] : "/dev/ttyUSB3";
        int baudRate = 115200;
        SerialPort port = SerialPort.getCommPort(portName);
        port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        // TIMEOUT_READ_SEMI_BLOCKING: read() will block until at least 1 byte or
        // timeout
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 2000, 0);

        PromptPrincipal prompt = new PromptPrincipal();

        int loopIterations = 25; // Número de vezes que deve-se ler as métricas do sinal 5G num mesmo ponto fixo
                                  // no espaço.
        int delayBetweenIterations = 30; // Tempo de espera entre cada leitura, em milissegundos.

        prompt.bemVindo();

        if (prompt.preparaUE5G(port, portName, baudRate).equals("ERROR")) {
            System.out.println("Erro na preparação do UE 5G. Encerrando.");
            return;
        }

        prompt.desligaEcho();

        // Verifica se o UE está responsivo.
        prompt.testaComandosAT();

        //Formata os erros retornáveis pelo modem, para facilitar o entendimento de qualquer problema ocorrido.
        prompt.formatError();

        // verifica status do SIMCard. Verifica se está inserido corretamente.
        prompt.simCardStatusCheck();

        // Verifica se o SIMCard está pronto para a coleta de dados.
        prompt.readySIMCardCheck();

        // Evita que o modem esteja restrito a ancorar em um tipo específico de rede.
        prompt.AutomaticOperatorSelection();

        // Seleciona a opção de rede 5G SA, para não trabalhar com LTE ou WCDMA (3G,
        // 4G).
        prompt.selectNetwork5GSA();

        prompt.defineBandaPreferencial();

        // Seleciona a banda preferida para trabalhar com 5G. A seleção ocorre depois da
        // definição no método anterior.
        prompt.selectPreferred5GdBand();

        // Pede para o user informar o código MCC e MNC do SIMCard.
        prompt.askMCC_MNC();

        // Obriga o UE ancorar somente na rede 5G do laboratório corrente. PLMN = 00101
        ////// ----   prompt.lockIn5GTestSignal();

        prompt.ueRadioOff();
        // Pede para o UE ligar suas funções que dependem do sinal de radio.
        prompt.ueRadioOn();

        Scanner userScanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {

            // -----Ações repetitivas por ponto (X,Y) -------------------

            // Verifica se conseguiu conectar na rede 5G.
            prompt.verify5GLinkConnection();

            // Checa se depois de ancorado na rede conseguiu se registrar também.
            prompt.check5GRegister(port,userScanner );

            prompt.iniciaColeta();

            // Coleta as métricas do sinal 5G para o ponto atual.
            prompt.colect5GSignalMetrics(delayBetweenIterations, loopIterations);
            // -----Fim das ações repetitivas por ponto -----------------

            // Pergunta ao usuário se quer mover-se para outro ponto
            String resposta = null;
            while (true) {
                System.out.println();
                System.out.println("Escolha a melhor opção abaixo:");
                System.out.println("(A) - Desejo mover-me para outro ponto (X,Y,Z) e coletar métricas dele também.");
                System.out.println("(B) - Desejo interromper agora a execução de coletas de métricas 5G. (Exit)");
                System.out.println("(C) - Desejo coletar métricas com outra banda 5G. (Ancorar em outra célula)");
                System.out.print("Digite A ou B ou C: ");
                resposta = userScanner.nextLine();
                if (resposta == null) {
                    System.out.println();
                    System.out.println("Entrada nula. Tente novamente.");
                    continue;
                }
                resposta = resposta.trim().toLowerCase();
                if (resposta.equals("a")) {
                    System.out.println("Mova-se para o próximo ponto (X, Y).");
                    break; // reinicia o loop while(continuar) para nova coleta
                } else if (resposta.equals("b")) {
                    continuar = false;
                    break; // sai do loop de pergunta e depois do while(continuar)
                } else if (resposta.equals("c")) {
                    System.out.println("Ok. Vamos coletar novamente com outra banda.");

                    prompt.defineBandaPreferencial();

                    // Seleciona a banda preferida para trabalhar com 5G.
                    prompt.selectPreferred5GdBand();

                    // Obriga o UE ancorar somente na rede 5G do laboratório corrente. PLMN = 00101
                    //////  -------- prompt.lockIn5GTestSignal();

                    prompt.ueRadioOff();
                    // Pede para o UE ligar suas funções que dependem do sinal de radio.
                    prompt.ueRadioOn();

                    // Verifica se conseguiu conectar na rede 5G.
                    // prompt.verify5GLinkConnection();

                    break; // reinicia o loop while(continuar) para nova coleta
                } else {
                    System.out.println("Opção inválida. Digite apenas A ou B.");
                }
            }
        }

        // -----Ações repetitivas-------------------------------Start-------------------
        // prompt.iniciaColeta();

        // Colecta as métricas do sinal 5G. Essas métricas ajudam a compor um arquivo
        // csv.
        // prompt.colect5GSignalMetrics(delayBetweenIterations, loopIterations);
        // -----Ações repetitivas-------------------------------end-------------------

        // System.out.println("Coordenadas coletadas: X=" + prompt.getPositon_X() + ",
        // Y=" + prompt.getPosition_Y());

        userScanner.close();
        prompt.desligaUE(port);
    }

}

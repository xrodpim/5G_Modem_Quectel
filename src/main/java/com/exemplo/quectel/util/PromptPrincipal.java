package com.exemplo.quectel.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.exemplo.quectel.file.CSVFileHander;
import com.exemplo.quectel.metrics.Metrics;
import com.exemplo.quectel.ueCommands.AtCommand;
import com.exemplo.quectel.ueCommands.CauseNetworkRejectionQuery;
import com.exemplo.quectel.ueCommands.CellsInformationQuery;
import com.exemplo.quectel.ueCommands.EchoOffCommand;
import com.exemplo.quectel.ueCommands.ExtendedCommand;
import com.exemplo.quectel.ueCommands.OperatorSelection;
import com.exemplo.quectel.ueCommands.PowerOffCommand;
import com.exemplo.quectel.ueCommands.RSRP_Query;
import com.exemplo.quectel.ueCommands.RSRQ_Query;
import com.exemplo.quectel.ueCommands.RadioOnCommand;
//import com.exemplo.quectel.ueCommands.RadioOnResetCommand;
import com.exemplo.quectel.ueCommands.RadioOffCommand;
import com.exemplo.quectel.ueCommands.RegistrationStatusQuery;
import com.exemplo.quectel.ueCommands.SIMInsertionQueryStatusReport;
import com.exemplo.quectel.ueCommands.SINR_Query;
import com.exemplo.quectel.ueCommands.Set5GSACommand;
import com.exemplo.quectel.ueCommands.SetSimPINCommand;
import com.exemplo.quectel.ueCommands.SimPinQuery;
import com.exemplo.quectel.ueCommands.UpDownLinkRateQuery;
import com.exemplo.quectel.ueCommands.UseBand41Only;
import com.exemplo.quectel.ueCommands.UseBandXOnly;
import com.exemplo.quectel.ueCommands.UseBand78Only;
import com.exemplo.quectel.ueCommands.UseBand77Only;
import com.exemplo.quectel.ueCommands.UsePLMN00101Only;
import com.exemplo.quectel.ueCommands.FormatErrorCommand;
import com.fazecast.jSerialComm.SerialPort;

public class PromptPrincipal {

    private int positon_X;
    private int position_Y;
    private int position_Z;
    private String bandaPreferencial;
    OutputStream out;
    InputStream in;
    String _MCC;
    String _MNC;

    public String getMCC() {
        return _MCC;
    }

    public String getMNC() {
        return _MNC;
    }

    public OutputStream getOut() {
        return out;
    }

    public InputStream getIn() {
        return in;
    }

    // Scanner como campo para evitar fechar System.in acidentalmente
    private final Scanner scanner;

    public PromptPrincipal() {
        this.scanner = new Scanner(System.in);
    }

    public void bemVindo() {
        System.out.println();
        System.out.println("#########################################################################");
        System.out.println("Bem-vindo ao sistema de coleta de dados (métricas do sinal 5g) com UE 5G!");
        System.out.println("#########################################################################");
        System.out.println();
    }

    /**
     * Pergunta ao usuário qual banda prefere: (A) n78, (B) n41, (C) n77, (D) Outra.
     * Aceita 'a', 'b', 'c' ou maiúsculas. Guarda "n78", "n41", "n77" ou "Outra" em
     * bandaPreferencial.
     */
    public void defineBandaPreferencial() {
        System.out.println("---Em que banda você quer ancorar seu UE nesse momento?");
        System.out.println("(A) - n78");
        System.out.println("(B) - n41");
        System.out.println("(C) - n77");
        System.out.println("(D) - Outra");

        while (true) {
            System.out.print("Digite A, B, C ou D: ");
            String line = scanner.nextLine();
            if (line == null) {
                System.out.println("Entrada nula. Tente novamente.");
                continue;
            }
            line = line.trim().toLowerCase();
            if (line.equals("a")) {
                this.bandaPreferencial = "n78";
                return;
            } else if (line.equals("b")) {
                this.bandaPreferencial = "n41";
                return;
            } else if (line.equals("c")) {
                this.bandaPreferencial = "n77";
                return;
            } else if (line.equals("d")) {
                // Pergunta adicional para banda "Outra"
                while (true) {
                    System.out.println("Digite o número correspondente à banda da sua escolha. Ex: 1, 31, 125, etc.");
                    System.out.print("Banda (somente números): ");
                    String numLine = scanner.nextLine();
                    if (numLine == null) {
                        System.out.println("Entrada nula. Tente novamente.");
                        continue;
                    }
                    numLine = numLine.trim();
                    // aceita apenas um token composto somente por dígitos (um número), rejeita
                    // letras ou múltiplos números
                    if (numLine.matches("\\d+")) {
                        this.bandaPreferencial = numLine;
                        return;
                    } else {
                        System.out.println(
                                "Entrada inválida. Digite apenas um número (ex.: 78). Não use letras nem múltiplos números separados por espaço, vírgula ou ';'.");
                    }
                }
            } else {
                System.out.println("Opção inválida. Digite apenas A, B, C ou D.");
            }
        }
    }

    // TODO: verificar se é possível obter o MCC e o MNC do SimCard, perguntando
    // diretametne para o UE.
    public void askMCC_MNC() {
        System.out.println();
        System.out.println("--------------------------------------------------------");
        System.out.println("Qual é o MCC e o MNC registrados no SIMCard? Digite abaixo o MCC.");

        // Leitura do MCC (espera exatamente 3 dígitos)
        while (true) {
            System.out.print("MCC: ");
            String mccLine = scanner.nextLine();
            if (mccLine == null) {
                System.out.println("Entrada nula. Tente novamente.");
                continue;
            }
            String mcc = mccLine.trim();
            // MCC padrão: exatamente 3 dígitos (ex: 001, 724, 310)
            if (mcc.matches("\\d{3}")) {
                this._MCC = mcc;
                break;
            } else {
                System.out.println("MCC inválido. Digite exatamente 3 dígitos (ex.: 001).");
            }
        }

        // Leitura do MNC (espera 2 ou 3 dígitos)
        while (true) {
            System.out.print("MNC: ");
            String mncLine = scanner.nextLine();
            if (mncLine == null) {
                System.out.println("Entrada nula. Tente novamente.");
                continue;
            }
            String mnc = mncLine.trim();
            // MNC pode ter 2 ou 3 dígitos (ex: 01, 10, 001)
            if (mnc.matches("\\d{2,3}")) {
                this._MNC = mnc;
                break;
            } else {
                System.out.println("MNC inválido. Digite 2 ou 3 dígitos (ex.: 01 ou 001).");
            }
        }
    }

    /**
     * Inicia a coleta de dados via prompt do sistema.
     * Mostra as mensagens:
     * Iniciando coleta de dados. Digite as coordenadas X,Y onde você está:
     * X:
     * (aguarda inteiro)
     * Y:
     * (aguarda inteiro)
     *
     * Ao terminar, guarda os valores em positon_X, position_Z e position_Y e
     * retorna.
     */
    public void iniciaColeta() {

        try {
            System.out.println();
            System.out.println(
                    "---Iniciando coleta de dados. Digite as coordenadas X,Y e Z onde você está nesse momento:");
            // Pergunta X
            this.positon_X = readIntWithPrompt(scanner, "X:");

            // Pergunta Y
            this.position_Y = readIntWithPrompt(scanner, "Y:");

            // Pergunta Z
            this.position_Z = readIntWithPrompt(scanner, "Z:");

        } finally {
            // scanner.close(); será fechado no final da classe Main.
        }
    }

    /**
     * Lê um inteiro do usuário mostrando o prompt especificado.
     * Repetirá até receber um inteiro válido.
     */
    private int readIntWithPrompt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    // Getters para permitir acesso aos valores após a coleta (opcional)
    public int getPositon_X() {
        return positon_X;
    }

    public int getPosition_Y() {
        return position_Y;
    }

    public int getPosition_Z() {
        return position_Z;
    }

    public String preparaUE5G(SerialPort serialPort, String portName, int baudRate) {
        System.out.println("Tentando abrir porta: " + portName + " @ " + baudRate);
        if (!serialPort.openPort()) {
            System.err.println("ERRO: não foi possível abrir a porta " + portName);
            return "ERROR";
        } else {
            System.out.println("Porta " + portName + " aberta com sucesso.");
        }

        // Opcional: limpar buffers antes de iniciar
        serialPort.flushIOBuffers();

        out = serialPort.getOutputStream();
        in = serialPort.getInputStream();

        return "OK";
    }

    public void desligaEcho() {

        EchoOffCommand echoOffCmd = new EchoOffCommand(); // cria o comando
        try {
            out.write(echoOffCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando Echo Off. Encerrando de forma anormal.");
            System.exit(1);
        }
        // Espera e lê a resposta
        if (echoOffCmd.checkResponse(in)) {

        } else {
            System.err.println("Aviso: falha ao executar comando Echo Off.");
        }
    }

    public void desligaUE(SerialPort serialPort) {

        // Envia comando Power
        // Off------------------------------------------------------------------------------------------------
        System.out.println();
        System.out.println();
        ExtendedCommand powerOffCmd = new PowerOffCommand(); // cria o comando
        try {
            out.write(powerOffCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando Power Off. Encerrando de forma anormal.");
            System.exit(1);
        }
        powerOffCmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (powerOffCmd.checkResponse(in)) {
            System.out.println("Comando Power Off executado com sucesso.");
        } else {
            System.err.println("Falha ao executar comando Power Off.");
        }
        // --------------------------------------------------------------------------------------------------------------------

        try {
            Thread.sleep(500); // esperar um pouco antes de fechar
            // fechar streams (o closePort fecha também)
            out.close();
            in.close();
        } catch (InterruptedException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.print("Fechando porta...");
        serialPort.closePort();
        System.out.println("OK!");

    }

    public void testaComandosAT() {
        System.out.println();
        System.out.println();
        // Envia "AT" com CR (alguns modems aceitam apenas LF/CR dependendo do
        // firmware)-----------------------------------------

        AtCommand atCmd = new AtCommand(); // cria o comando

        try {
            out.write(atCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando de teste AT. Encerrando de forma anormal.");
            System.exit(1);
        }

        atCmd.printLog(); // imprime o log de envio

        // Espera e lê a resposta
        if (atCmd.checkResponse(in)) {
            System.out.println("Teste de comando AT executado com sucesso. Prosseguindo com a coleta...");
            System.out.println();
        } else {
            System.err.println("AVISO: Falha ao executar comando AT!");
        }
    }

    // Função para verificar se o UE está registrado e indicar o motivo de falha.
    public void check5GRegister(SerialPort port, Scanner userScanner) {
        System.out.println();
        System.out.println();
        RegistrationStatusQuery regStatusCmd = new RegistrationStatusQuery(); // cria o comando
        try {
            out.write(regStatusCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando de status do registro 5G. Encerrando de forma anormal.");
            System.exit(1);
        }
        regStatusCmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (regStatusCmd.checkResponse(in)) {
            System.out.println("Comando Registration Status Query executado com sucesso.");
            if (regStatusCmd.handleTheResponse() == 1) {

                userScanner.close();
                this.desligaUE(port);
                System.exit(1);

            }

            System.out.println();

        } else {
            System.err.println("AVISO: Falha ao executar comando Registration Status Query!!");
        }
    }

    public void simCardStatusCheck() {
        System.out.println();
        System.out.println();
        SIMInsertionQueryStatusReport simStatusCmd = new SIMInsertionQueryStatusReport(); // cria o comando
        try {
            out.write(simStatusCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando de status do SIMCard. Encerrando de forma anormal.");
            System.exit(1);
        }
        simStatusCmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (simStatusCmd.checkResponse(in)) {
            System.out.println("Comando SIM Insertion Status Query executado com sucesso.");
            simStatusCmd.handleResponse();
            System.out.println();
        } else {
            System.err.println("AVISO: Falha ao executar comando SIM Insertion Status!!");
        }
    }

    public void readySIMCardCheck() {

        System.out.println();
        System.out.println();
        SimPinQuery simPinQueryCmd = new SimPinQuery(); // cria o comando
        try {
            out.write(simPinQueryCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando de checagem do SIMCard. Encerrando de forma anormal.");
            System.exit(1);
        }
        simPinQueryCmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (simPinQueryCmd.checkResponse(in)) {
            System.out.println("Comando SIM PIN Query executado com sucesso.");
            simPinQueryCmd.handleResponse();
        } else {
            System.err.println("AVISO: Falha ao executar comando SIM PIN Query. !!");
        }

    }

    public void SetSimCartPIN(String sPIN) {

        System.out.println();
        System.out.println();
        SetSimPINCommand simPinCmd = new SetSimPINCommand(sPIN); // cria o comando (substitua "1234" pelo PIN correto)
        try {
            out.write(simPinCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando de configurar o SimCard PIN. Encerrando de forma anormal.");
            System.exit(1);
        }
        simPinCmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (simPinCmd.checkResponse(in)) {
            System.out.println("Comando Set SIM PIN executado com sucesso.");
        } else {
            System.err.println("AVISO: Falha ao executar comando Set SIM PIN!");
        }
    }

    public void AutomaticOperatorSelection() {

        System.out.println();
        System.out.println();
        OperatorSelection opSelection = new OperatorSelection(); // cria o comando
        try {
            out.write(opSelection.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err
                    .println("Erro ao enviar comando de selecionar rede automaticamente. Encerrando de forma anormal.");
            System.exit(1);
        }
        opSelection.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (opSelection.checkResponse(in)) {
            System.out.println("Comando 'selecionar rede automaticamente' executado com sucesso.");
        } else {
            System.err.println("AVISO: Falha ao executar comando 'selecionar rede automaticamente'!");
        }

    }

    public void selectNetwork5GSA() {

        System.out.println();
        System.out.println();
        Set5GSACommand set5GCmd = new Set5GSACommand(); // cria o comando
        try {
            out.write(set5GCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando de selecionar rede 5G SA. Encerrando de forma anormal.");
            System.exit(1);
        }
        set5GCmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (set5GCmd.checkResponse(in)) {
            System.out.println("Comando Set 5G SA executado com sucesso.");
        } else {
            System.err.println("AVISO: Falha ao executar comando Set 5G SA!");
        }

    }

    private void selectBand78() {
        System.out.println();
        System.out.println();
        UseBand78Only useBand78Cmd = new UseBand78Only(); // cria o comando
        try {
            out.write(useBand78Cmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando de selecionar banda de 5G SA. Encerrando de forma anormal.");
            System.exit(1);
        }
        useBand78Cmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (useBand78Cmd.checkResponse(in)) {
            System.out.println("Comando Use Band 78 Only executado com sucesso.");
        } else {
            System.err.println("AVISO: Falha ao executar comando Use Band 78 Only!!");
        }
    }

    private void selectBand77() {
        System.out.println();
        System.out.println();
        UseBand77Only useBand77Cmd = new UseBand77Only(); // cria o comando
        try {
            out.write(useBand77Cmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando de selecionar banda de 5G SA. Encerrando de forma anormal.");
            System.exit(1);
        }
        useBand77Cmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (useBand77Cmd.checkResponse(in)) {
            System.out.println("Comando Use Band 77 Only executado com sucesso.");
        } else {
            System.err.println("AVISO: Falha ao executar comando Use Band 77 Only!!");
        }
    }

    private void selectBand41() {
        System.out.println();
        System.out.println();
        UseBand41Only useBand41Cmd = new UseBand41Only(); // cria o comando
        try {
            out.write(useBand41Cmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando de selecionar banda de 5G SA. Encerrando de forma anormal.");
            System.exit(1);
        }
        useBand41Cmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (useBand41Cmd.checkResponse(in)) {
            System.out.println("Comando Use Band 41 Only executado com sucesso.");
        } else {
            System.err.println("AVISO: Falha ao executar comando Use Band 41 Only!!");
        }
    }

    private void selectBandX(int X) {
        System.out.println();
        System.out.println();
        UseBandXOnly useBandXCmd = new UseBandXOnly(X); // cria o comando
        try {
            out.write(useBandXCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando de selecionar banda de 5G SA. Encerrando de forma anormal.");
            System.exit(1);
        }
        useBandXCmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (useBandXCmd.checkResponse(in)) {
            System.out.println("Comando Use Band X Only executado com sucesso.");
        } else {
            System.err.println("AVISO: Falha ao executar comando Use Band X Only!!");
        }
    }

    public void selectPreferred5GdBand() {
        if (this.bandaPreferencial.equals("n78")) {
            selectBand78();
        } else if (this.bandaPreferencial.equals("n41")) {
            selectBand41();
        } else if (this.bandaPreferencial.equals("n77")) {
            selectBand77();
        } else {
            selectBandX(Integer.parseInt(bandaPreferencial));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * public void ueRadioOnReset() {
     * 
     * System.out.println();
     * System.out.println();
     * RadioOnResetCommand radioOnCmd = new RadioOnResetCommand(); // cria o comando
     * try {
     * out.write(radioOnCmd.getBytes()); // envia o comando
     * out.flush();
     * } catch (IOException e) {
     * // TODO Auto-generated catch block
     * e.printStackTrace();
     * System.err
     * .println("Erro ao enviar comando de ligar as funções de radio do UE e reset. Encerrando de forma anormal."
     * );
     * System.exit(1);
     * }
     * radioOnCmd.printLog(); // imprime o log de envio
     * // Espera e lê a resposta
     * if (radioOnCmd.checkResponse(in)) {
     * System.out.
     * println("Comando Radio On Reset executado com sucesso. Aguarde 70 segundos para o UE resetar o rádio."
     * );
     * } else {
     * System.err.println("AVISO: Falha ao executar comando Radio On Reset!");
     * }
     * 
     * try {
     * Thread.sleep(70000); // esperar 70 segundos para o UE ligar o rádio
     * } catch (InterruptedException e) {
     * // TODO Auto-generated catch block
     * e.printStackTrace();
     * }
     * 
     * }
     */

    public void ueRadioOn() {

        System.out.println();
        System.out.println();
        RadioOnCommand radioOnCmd = new RadioOnCommand(); // cria o comando
        try {
            out.write(radioOnCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err
                    .println("Erro ao enviar comando de ligar as funções de radio do UE. Encerrando de forma anormal.");
            System.exit(1);
        }
        radioOnCmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (radioOnCmd.checkResponse(in)) {
            System.out.println("Comando Radio On executado com sucesso. Aguarde 10 segundos para o UE ligar o rádio.");
        } else {
            System.err.println("AVISO: Falha ao executar comando Radio On!");
        }

        try {
            Thread.sleep(10000); // esperar 10 segundos para o UE ligar o rádio
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void ueRadioOff() {

        System.out.println();
        System.out.println();
        RadioOffCommand radioOffCmd = new RadioOffCommand(); // cria o comando
        try {
            out.write(radioOffCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err
                    .println(
                            "Erro ao enviar comando de desligar as funções de radio do UE. Encerrando de forma anormal.");
            System.exit(1);
        }
        radioOffCmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (radioOffCmd.checkResponse(in)) {
            System.out
                    .println("Comando Radio Off executado com sucesso. Aguarde 2 segundos para o UE desligar o rádio.");
        } else {
            System.err.println("AVISO: Falha ao executar comando Radio Off!");
        }

        try {
            Thread.sleep(2000); // esperar 10 segundos para o UE desligar o rádio
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void verify5GLinkConnection() {
        System.out.println();
        System.out.println();
        CauseNetworkRejectionQuery causeNetRejCmd = new CauseNetworkRejectionQuery(); // cria o comando
        try {
            out.write(causeNetRejCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Erro ao enviar comando de checar conexão 5G. Encerrando de forma anormal.");
            System.exit(1);
        }
        causeNetRejCmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (causeNetRejCmd.checkResponse(in)) {
            System.out.println("Comando Cause Network Rejection Query executado com sucesso.");
            causeNetRejCmd.handleResponse();
        } else {
            System.err.println("AVISO: Falha ao executar comando Cause Network Rejection Query!");
        }
    }

    public void formatError() {
        System.out.println();
        System.out.println();
        FormatErrorCommand formatCmd = new FormatErrorCommand();
        try {
            out.write(formatCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err
                    .println("Erro ao tentar enviar comando de formatação de erros. Encerrando de forma anormal.");
            System.exit(1);
        }
        formatCmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (formatCmd.checkResponse(in)) {
            System.out.println("Comando Format Error executado com sucesso.");
        } else {
            System.err.println(
                    "AVISO: Falha ao executar comando para formatar erros!");
        }
    }

    public void lockIn5GTestSignal() {

        System.out.println();
        System.out.println();
        UsePLMN00101Only usePLMNCmd = new UsePLMN00101Only(_MCC, _MNC); // cria o comando
        try {
            out.write(usePLMNCmd.getBytes()); // envia o comando
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err
                    .println("Erro ao tentar ancorar somente na rede de laboratório 5G. Encerrando de forma anormal.");
            System.exit(1);
        }
        usePLMNCmd.printLog(); // imprime o log de envio
        // Espera e lê a resposta
        if (usePLMNCmd.checkResponse(in)) {
            System.out.println("Comando Use PLMN 00101 Only executado com sucesso.");
        } else {
            System.err.println(
                    "AVISO: Falha ao executar comando Use PLMN 00101 Only! Check se a célula com PLMN 00101 exsite.");
        }

    }

    public void colect5GSignalMetrics(int delayBetweenIterations, int loopIterations) {
        int i = loopIterations;

        CellsInformationQuery cellsInfoCmd = new CellsInformationQuery();
        RSRP_Query rsrpCmd = new RSRP_Query();
        RSRQ_Query rsrqCmd = new RSRQ_Query();
        SINR_Query sinrCmd = new SINR_Query();
        UpDownLinkRateQuery upDownCmd = new UpDownLinkRateQuery();
        List<Metrics> listaDeMetricas = new ArrayList<>();
        CSVFileHander csvFileHandler;

        while (i > 0) {

            System.out.println();

            Metrics metrics = new Metrics();

            metrics.setMCC(_MCC); // MCC fixo para rede de laboratório
            metrics.setMNC(_MNC); // MNC fixo para rede de laboratório

            // Passa o objeto metrics para os comandos que precisam atualizar métricas
            cellsInfoCmd.setMetrics(metrics);
            rsrpCmd.setMetrics(metrics);
            rsrqCmd.setMetrics(metrics);
            sinrCmd.setMetrics(metrics);
            upDownCmd.setMetrics(metrics);
            metrics.setX(positon_X);
            metrics.setY(position_Y);
            metrics.setZ(position_Z);

            try {
                out.write(cellsInfoCmd.getBytes()); // envia o comando
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.err
                        .println(
                                "Erro ao tentar ler métricas do sinal 5G da célula servidora. Encerrando de forma anormal.");
                System.exit(1);
            }
            cellsInfoCmd.printLog(); // imprime o log de envio
            if (cellsInfoCmd.checkResponse(in)) {
                cellsInfoCmd.handleResponse();
                System.out.println("Comando Cells Information executado com sucesso.");
            } else {
                System.err.println("AVISO: Falha ao executar comando Cells Information!");
            }

            // ------------------------------------------------------------------------
            try {
                out.write(rsrpCmd.getBytes()); // envia o comando
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println(
                        "Erro ao tentar ler RSRP do sinal 5G da célula servidora. Encerrando de forma anormal.");
                System.exit(1);
            }
            rsrpCmd.printLog(); // imprime o log de envio
            if (rsrpCmd.checkResponse(in)) {
                rsrpCmd.handleResponse();
                System.out.println("Comando RSRP Query executado com sucesso.");
            } else {
                System.err.println("AVISO: Falha ao executar comando RSRP Query!");
            }
            // ------------------------------------------------------------------------

            try {
                out.write(rsrqCmd.getBytes()); // envia o comando
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.err
                        .println(
                                "Erro ao tentar ler RSRQ do sinal 5G da célula servidora. Encerrando de forma anormal.");
                System.exit(1);
            }
            rsrqCmd.printLog(); // imprime o log de envio
            if (rsrqCmd.checkResponse(in)) {
                rsrqCmd.handleResponse();
                System.out.println("Comando RSRQ Query executado com sucesso.");
            } else {
                System.err.println("AVISO: Falha ao executar comando RSRQ Query!");
            }
            // ------------------------------------------------------------------------

            try {
                out.write(sinrCmd.getBytes()); // envia o comando
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.err
                        .println(
                                "Erro ao tentar ler SINR do sinal 5G da célula servidora. Encerrando de forma anormal.");
                System.exit(1);
            }
            sinrCmd.printLog(); // imprime o log de envio
            if (sinrCmd.checkResponse(in)) {
                sinrCmd.handleResponse();
                System.out.println("Comando SINR Query executado com sucesso.");
            } else {
                System.err.println("AVISO: Falha ao executar comando SINR Query!");
            }
            // ------------------------------------------------------------------------

            try {
                out.write(upDownCmd.getBytes()); // envia o comando
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.err
                        .println(
                                "Erro ao tentar ler UpLink/DownLink do sinal 5G da célula servidora. Encerrando de forma anormal.");
                System.exit(1);
            }
            upDownCmd.printLog(); // imprime o log de envio
            if (upDownCmd.checkResponse(in)) {
                upDownCmd.handleResponse();
                System.out.println("Comando UpLink/DownLink Query executado com sucesso.");
            } else {
                System.err.println("AVISO: Falha ao executar comando UpLink/DownLink Query!");
            }
            // ------------------------------------------------------------------------

            // Imprime as métricas coletadas até agora

            System.out.println();
            System.out.println("--------------------------------------------------------");
            System.out.println("Métricas coletadas até agora: " + metrics);

            listaDeMetricas.add(metrics);

            // pequeno descanso para não ocupar 100% da CPU;

            try {
                Thread.sleep(delayBetweenIterations);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            i--;
        }

        // Finalmente grava um arquivo de métricas de sinal 5G em CSV.
        try {
            csvFileHandler = new CSVFileHander(positon_X, position_Y, position_Z);
            csvFileHandler.escreveMetricas(listaDeMetricas);
            csvFileHandler.fechaArquivo();
            System.out.println();
            System.out.println(" >>> Arquivo de métricas gerado com sucesso = " + csvFileHandler.getFilePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

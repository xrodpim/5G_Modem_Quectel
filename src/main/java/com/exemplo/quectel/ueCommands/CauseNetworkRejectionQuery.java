package com.exemplo.quectel.ueCommands;

import java.util.Scanner;

public class CauseNetworkRejectionQuery extends ExtendedCommand {
    public CauseNetworkRejectionQuery() {
        super("+QNETRC?");
        this.typeOfAnswer = "OK";
    }

    public void handleResponse() {

        // System.out.println(response);

        if (response.contains("5gmm_cause")) {

            int cause = parseQNetRc(response);
            System.out.print("Resultado: ");
            switch (cause) {
                case 0:
                    System.out.println("Sem problema.");
                    break;
                case 3:
                    System.out.println("UE ilegal");
                    break;
                case 5:
                    System.out.println("PEI não aceito");
                    break;
                case 6:
                    System.out.println("ME ilegal");
                    break;
                case 7:
                    System.out.println("Serviços 5GS não permitidos");
                    break;
                case 9:
                    System.out.println("Identidade do UE não pode ser derivada pela rede");
                    break;
                case 10:
                    System.out.println("Desregistrado implicitamente");
                    break;
                case 11:
                    System.out.println("PLMN não permitido");
                    break;
                case 12:
                    System.out.println("Área de rastreamento não permitida");
                    break;
                case 13:
                    System.out.println("Roaming não permitido nesta área de rastreamento");
                    break;
                case 15:
                    System.out.println("Nenhuma célula adequada na área de rastreamento");
                    break;
                case 20:
                    System.out.println("Falha de MAC");
                    break;
                case 21:
                    System.out.println("Falha de sincronização");
                    break;
                case 22:
                    System.out.println("Congestionamento");
                    break;
                case 23:
                    System.out.println("Incompatibilidade de capacidades de segurança do UE");
                    break;
                case 24:
                    System.out.println("Modo de segurança rejeitado, não especificado");
                    break;
                case 26:
                    System.out.println("Autenticação não-5G inaceitável");
                    break;
                case 27:
                    System.out.println("Modo N1 não permitido");
                    break;
                case 28:
                    System.out.println("Área de serviço restrita");
                    break;
                case 31:
                    System.out.println("Redirecionamento para EPC necessário");
                    break;
                case 43:
                    System.out.println("LADN não disponível");
                    break;
                case 62:
                    System.out.println("Nenhum slice de rede disponível");
                    break;
                case 65:
                    System.out.println("Número máximo de sessões PDU atingido");
                    break;
                case 67:
                    System.out.println("Recursos insuficientes para slice e DNN específicos");
                    break;
                case 69:
                    System.out.println("Recursos insuficientes para slice específico");
                    break;
                case 71:
                    System.out.println("ngKSI já em uso");
                    break;
                case 72:
                    System.out.println("Acesso não-3GPP ao 5GCN não permitido");
                    break;
                case 73:
                    System.out.println("Rede de atendimento não autorizada");
                    break;
                case 74:
                    System.out.println("Não autorizado temporariamente para este SNPN");
                    break;
                case 75:
                    System.out.println("Não autorizado permanentemente para este SNPN");
                    break;
                case 76:
                    System.out.println("Não autorizado para este CAG ou autorizado apenas para células CAG");
                    break;
                case 77:
                    System.out.println("Área de acesso com fio não permitida");
                    break;
                case 78:
                    System.out.println("PLMN não permitido para operar na localização atual do UE");
                    break;
                case 79:
                    System.out.println("Serviços UAS não permitidos");
                    break;
                case 90:
                    System.out.println("Carga não foi encaminhada");
                    break;
                case 91:
                    System.out.println("DNN não suportado ou não subscrito no slice");
                    break;
                case 92:
                    System.out.println("Recursos insuficientes de plano de usuário para a sessão PDU");
                    break;
                case 95:
                    System.out.println("Mensagem semanticamente incorreta");
                    break;
                case 96:
                    System.out.println("Informação obrigatória inválida");
                    break;
                case 97:
                    System.out.println("Tipo de mensagem inexistente ou não implementado");
                    break;
                case 98:
                    System.out.println("Tipo de mensagem não compatível com o estado do protocolo");
                    break;
                case 99:
                    System.out.println("Elemento de informação inexistente ou não implementado");
                    break;
                case 100:
                    System.out.println("Erro de IE condicional");
                    break;
                case 101:
                    System.out.println("Mensagem não compatível com o estado do protocolo");
                    break;
                case 111:
                    System.out.println("Erro de protocolo, não especificado");
                    break;
                default:
                    System.out.println("Causa de rejeição desconhecida.");
                    break;
            }

        } else {
            System.out.println("Não foi possível ler a causa de rejeição da rede.");

        }
    }

    public int parseQNetRc(String response) {
        Scanner scanner = new Scanner(response);

        // Ignora as duas primeiras linhas
        if (scanner.hasNextLine())
            scanner.nextLine();
        if (scanner.hasNextLine())
            scanner.nextLine();

        // Lê a terceira linha
        String line = scanner.nextLine();

        // Remove o sufixo "OK" e qualquer espaço em branco adicional
        line = line.replace("OK", "").trim();

        // Divide a linha em partes usando a vírgula como delimitador
        String[] parts = line.split(",");

        if (parts.length == 2) {
            int rejectCause = Integer.parseInt(parts[1].trim());
            return rejectCause;
        } else {
            throw new IllegalArgumentException("Formato da string inválido.");
        }
    }

}

// Respostas possíveis para o caso 5G:

// Ver página 115 do AT Commands Manual.
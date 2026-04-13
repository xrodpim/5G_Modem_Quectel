package com.exemplo.quectel.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Objects;

import com.exemplo.quectel.metrics.Metrics;

/**
 * Classe responsável por criar e escrever um arquivo CSV com métricas.
 * Nome do arquivo: Metricas_5G_AAAAMMDD_HHMMSS_Milessegundos.csv no diretório
 * corrente.
 */
public class CSVFileHander {

    private final BufferedWriter writer;
    private final Path filePath;

    /**
     * Construtor: cria/abre o arquivo CSV e escreve o header.
     *
     * @throws IOException em caso de erro de I/O
     */
    public CSVFileHander(int x, int y, int z) throws IOException {
        // monta timestamp
        LocalDateTime now = LocalDateTime.now();
        String dateTimePart = now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        int millis = now.get(ChronoField.MILLI_OF_SECOND);
        String filename = String.format("Metricas_5G_%s_%03d_%03d_%03d_%03d.csv", dateTimePart, millis, x, y, z);

        this.filePath = Paths.get(filename);
        // Abre o arquivo para escrita, truncando se já existir
        this.writer = Files.newBufferedWriter(
                filePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);

        // Escreve header (opcional, facilita abrir no Excel)
        String header = String.join(",",
                "RSRP", "RSRQ", "SINR", "cellID",
                "hora", "minuto", "segundo", "milessegundo",
                "dia", "mes", "ano",
                "ARFCN", "band", "NR_DL_bandwidth", "scs", "srxlev",
                "RSRP_PRX", "RSRP_DRX",
                "RSRP_RX2", "RSRP_RX3",
                "RSRQ_PRX", "RSRQ_DRX",
                "RSRQ_RX2", "RSRQ_RX3",
                "SINR_PRX", "SINR_DRX",
                "SINR_RX2", "SINR_RX3",
                "uplink", "downlink",
                "X", "Y", "Z");
        writer.write(header);
        writer.newLine();
        writer.flush();
    }

    /**
     * Escreve no arquivo CSV uma linha por cada objeto Metrics da lista.
     * Campos são escritos na mesma ordem do header.
     *
     * @param metricsList lista de Metrics (pode conter nulls, que serão ignorados)
     * @throws IOException se ocorrer erro de escrita
     */
    public void escreveMetricas(List<Metrics> metricsList) throws IOException {
        Objects.requireNonNull(metricsList, "metricsList não pode ser null");

        for (Metrics m : metricsList) {
            if (m == null)
                continue;

            // monta os campos na mesma ordem do header
            String[] fields = new String[] {
                    Integer.toString(m.getRSRP()),
                    Integer.toString(m.getRSRQ()),
                    Integer.toString(m.getSINR()),
                    escapeCsv(m.getCellID()),

                    Integer.toString(m.getHora()),
                    Integer.toString(m.getMinuto()),
                    Integer.toString(m.getSegundo()),
                    Integer.toString(m.getMilessegundo()),

                    Integer.toString(m.getDia()),
                    Integer.toString(m.getMes()),
                    Integer.toString(m.getAno()),

                    Integer.toString(m.getARFCN()),
                    Integer.toString(m.getBand()),
                    Integer.toString(m.getNR_DL_bandwidth()),
                    Integer.toString(m.getScs()),
                    Integer.toString(m.getSrxlev()),

                    Integer.toString(m.getRSRP_PRX()),
                    Integer.toString(m.getRSRP_DRX()),
                    Integer.toString(m.getRSRP_RX2()),
                    Integer.toString(m.getRSRP_RX3()),

                    Integer.toString(m.getRSRQ_PRX()),
                    Integer.toString(m.getRSRQ_DRX()),
                    Integer.toString(m.getRSRQ_RX2()),
                    Integer.toString(m.getRSRQ_RX3()),

                    Integer.toString(m.getSINR_PRX()),
                    Integer.toString(m.getSINR_DRX()),
                    Integer.toString(m.getSINR_RX2()),
                    Integer.toString(m.getSINR_RX3()),

                    Integer.toString(m.getUplink()),
                    Integer.toString(m.getDownlink()),
                    Integer.toString(m.getX()),
                    Integer.toString(m.getY()),
                    Integer.toString(m.getZ())
            };

            // junta com vírgulas e escreve
            writer.write(String.join(",", fields));
            writer.newLine();
        }

        // flush para garantir que dados cheguem ao disco
        writer.flush();
    }

    /**
     * Fecha o arquivo (flush + close). Deve ser chamado quando não for mais usado.
     *
     * @throws IOException se ocorrer erro ao fechar
     */
    public void fechaArquivo() throws IOException {
        writer.flush();
        writer.close();
    }

    /**
     * Escapa um valor de CSV: se o valor contiver vírgula, aspas ou nova linha,
     * envolve em aspas e duplica aspas internas.
     *
     * Se input for null, retorna string vazia.
     */
    private static String escapeCsv(String input) {
        if (input == null)
            return "";
        String s = input;
        boolean needQuotes = s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r");
        if (s.contains("\"")) {
            s = s.replace("\"", "\"\""); // duplica aspas
            needQuotes = true;
        }
        if (needQuotes) {
            return "\"" + s + "\"";
        } else {
            return s;
        }
    }

    /**
     * Retorna o caminho do arquivo que foi criado (útil para logs).
     */
    public Path getFilePath() {
        return filePath;
    }
}

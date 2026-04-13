package com.exemplo.quectel.metrics;

import java.io.Serializable;
import java.util.Objects;

/**
 * Bean que representa métricas de rádio/UE.
 */
public class Metrics implements Serializable {
    private static final long serialVersionUID = 1L;

    // Atributos (conforme especificado)
    private int RSRP;
    private int RSRQ;
    private int SINR;
    private String cellID;
    private int hora;
    private int minuto;
    private int segundo;
    private int milessegundo;
    private int dia;
    private int mes;
    private int ano;
    private int ARFCN;
    private int band;
    private int NR_DL_bandwidth;
    private int scs;
    private int srxlev;
    private int RSRP_PRX;
    private int RSRP_DRX;
    private int RSRP_RX2;
    private int RSRP_RX3;
    private int RSRQ_PRX;
    private int RSRQ_DRX;
    private int RSRQ_RX2;
    private int RSRQ_RX3;
    private int SINR_PRX;
    private int SINR_DRX;
    private int SINR_RX2;
    private int SINR_RX3;
    private int uplink;
    private int downlink;
    private int X;
    private int Y;
    private int Z;
    private String MCC;
    private String MNC;

    public int getRSRP_RX2() {
        return RSRP_RX2;
    }

    public void setRSRP_RX2(int RSRP_RX2) {
        this.RSRP_RX2 = RSRP_RX2;
    }

    public int getRSRP_RX3() {
        return RSRP_RX3;
    }

    public void setRSRP_RX3(int RSRP_RX3) {
        this.RSRP_RX3 = RSRP_RX3;
    }

    public int getRSRQ_RX2() {
        return RSRQ_RX2;
    }

    public void setRSRQ_RX2(int RSRQ_RX2) {
        this.RSRQ_RX2 = RSRQ_RX2;
    }

    public int getRSRQ_RX3() {
        return RSRQ_RX3;
    }

    public void setRSRQ_RX3(int RSRQ_RX3) {
        this.RSRQ_RX3 = RSRQ_RX3;
    }

    public int getSINR_RX2() {
        return SINR_RX2;
    }

    public void setSINR_RX2(int SINR_RX2) {
        this.SINR_RX2 = SINR_RX2;
    }

    public int getSINR_RX3() {
        return SINR_RX3;
    }

    public void setSINR_RX3(int SINR_RX3) {
        this.SINR_RX3 = SINR_RX3;
    }

    public String getMCC() {
        return MCC;
    }

    public void setMCC(String mCC) {
        MCC = mCC;
    }

    public String getMNC() {
        return MNC;
    }

    public void setMNC(String mNC) {
        MNC = mNC;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getZ() {
        return Z;
    }

    public void setZ(int z) {
        Z = z;
    }

    // Construtor padrão
    public Metrics() {
    }

    // Getters e setters
    public int getRSRP() {
        return RSRP;
    }

    public void setRSRP(int RSRP) {
        this.RSRP = RSRP;
    }

    public int getRSRQ() {
        return RSRQ;
    }

    public void setRSRQ(int RSRQ) {
        this.RSRQ = RSRQ;
    }

    public int getSINR() {
        return SINR;
    }

    public void setSINR(int SINR) {
        this.SINR = SINR;
    }

    public String getCellID() {
        return cellID;
    }

    public void setCellID(String cellID) {
        this.cellID = cellID;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getSegundo() {
        return segundo;
    }

    public void setSegundo(int segundo) {
        this.segundo = segundo;
    }

    public int getMilessegundo() {
        return milessegundo;
    }

    public void setMilessegundo(int milessegundo) {
        this.milessegundo = milessegundo;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getARFCN() {
        return ARFCN;
    }

    public void setARFCN(int ARFCN) {
        this.ARFCN = ARFCN;
    }

    public int getBand() {
        return band;
    }

    public void setBand(int band) {
        this.band = band;
    }

    public int getNR_DL_bandwidth() {
        return NR_DL_bandwidth;
    }

    public void setNR_DL_bandwidth(int NR_DL_bandwidth) {
        this.NR_DL_bandwidth = NR_DL_bandwidth;
    }

    public int getScs() {
        return scs;
    }

    public void setScs(int scs) {
        this.scs = scs;
    }

    public int getSrxlev() {
        return srxlev;
    }

    public void setSrxlev(int srxlev) {
        this.srxlev = srxlev;
    }

    public int getRSRP_PRX() {
        return RSRP_PRX;
    }

    public void setRSRP_PRX(int RSRP_PRX) {
        this.RSRP_PRX = RSRP_PRX;
    }

    public int getRSRP_DRX() {
        return RSRP_DRX;
    }

    public void setRSRP_DRX(int RSRP_DRX) {
        this.RSRP_DRX = RSRP_DRX;
    }

    public int getRSRQ_PRX() {
        return RSRQ_PRX;
    }

    public void setRSRQ_PRX(int RSRQ_PRX) {
        this.RSRQ_PRX = RSRQ_PRX;
    }

    public int getRSRQ_DRX() {
        return RSRQ_DRX;
    }

    public void setRSRQ_DRX(int RSRQ_DRX) {
        this.RSRQ_DRX = RSRQ_DRX;
    }

    public int getSINR_PRX() {
        return SINR_PRX;
    }

    public void setSINR_PRX(int SINR_PRX) {
        this.SINR_PRX = SINR_PRX;
    }

    public int getSINR_DRX() {
        return SINR_DRX;
    }

    public void setSINR_DRX(int SINR_DRX) {
        this.SINR_DRX = SINR_DRX;
    }

    public int getUplink() {
        return uplink;
    }

    public void setUplink(int uplink) {
        this.uplink = uplink;
    }

    public int getDownlink() {
        return downlink;
    }

    public void setDownlink(int downlink) {
        this.downlink = downlink;
    }

    @Override
    public String toString() {
        return "Metrics{" +
                "RSRP=" + RSRP +
                ", RSRQ=" + RSRQ +
                ", SINR=" + SINR +
                ", cellID='" + cellID + '\'' +
                ", hora=" + hora +
                ", minuto=" + minuto +
                ", segundo=" + segundo +
                ", milessegundo=" + milessegundo +
                ", dia=" + dia +
                ", mes=" + mes +
                ", ano=" + ano +
                ", ARFCN=" + ARFCN +
                ", band=" + band +
                ", NR_DL_bandwidth=" + NR_DL_bandwidth +
                ", scs=" + scs +
                ", srxlev=" + srxlev +
                ", RSRP_PRX=" + RSRP_PRX +
                ", RSRP_DRX=" + RSRP_DRX +
                ", RSRQ_PRX=" + RSRQ_PRX +
                ", RSRQ_DRX=" + RSRQ_DRX +
                ", SINR_PRX=" + SINR_PRX +
                ", SINR_DRX=" + SINR_DRX +
                ", uplink=" + uplink +
                ", downlink=" + downlink +
                ", X=" + X +
                ", Y=" + Y +
                ", Z=" + Z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Metrics metrics = (Metrics) o;
        return RSRP == metrics.RSRP &&
                RSRQ == metrics.RSRQ &&
                SINR == metrics.SINR &&
                hora == metrics.hora &&
                minuto == metrics.minuto &&
                segundo == metrics.segundo &&
                milessegundo == metrics.milessegundo &&
                dia == metrics.dia &&
                mes == metrics.mes &&
                ano == metrics.ano &&
                ARFCN == metrics.ARFCN &&
                band == metrics.band &&
                NR_DL_bandwidth == metrics.NR_DL_bandwidth &&
                scs == metrics.scs &&
                srxlev == metrics.srxlev &&
                RSRP_PRX == metrics.RSRP_PRX &&
                RSRP_DRX == metrics.RSRP_DRX &&
                RSRQ_PRX == metrics.RSRQ_PRX &&
                RSRQ_DRX == metrics.RSRQ_DRX &&
                SINR_PRX == metrics.SINR_PRX &&
                SINR_DRX == metrics.SINR_DRX &&
                uplink == metrics.uplink &&
                downlink == metrics.downlink &&
                Objects.equals(cellID, metrics.cellID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RSRP, RSRQ, SINR, cellID, hora, minuto, segundo, milessegundo, dia, mes, ano,
                ARFCN, band, NR_DL_bandwidth, scs, srxlev,
                RSRP_PRX, RSRP_DRX, RSRQ_PRX, RSRQ_DRX, SINR_PRX, SINR_DRX, uplink, downlink);
    }

}

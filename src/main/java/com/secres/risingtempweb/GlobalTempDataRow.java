package com.secres.risingtempweb;

import java.time.LocalDate;

public class GlobalTempDataRow {
    private LocalDate dt;
    private String landAverageTemperature;
    private String landAverageTemperatureUncertainty;
    private String landMaxTemperature;
    private String landMaxTemperatureUncertainty;
    private String landMinTemperature;
    private String landMinTemperatureUncertainty;
    private String landAndOceanAverageTemperature;
    private String landAndOceanAverageTemperatureUncertainty;

    public GlobalTempDataRow() {

    }

    public GlobalTempDataRow(LocalDate dt, String landAverageTemperature, String landAverageTemperatureUncertainty, String landMaxTemperature, String landMaxTemperatureUncertainty, String landMinTemperature, String landMinTemperatureUncertainty, String landAndOceanAverageTemperature, String landAndOceanAverageTemperatureUncertainty) {
        this.dt = dt;
        this.landAverageTemperature = landAverageTemperature;
        this.landAverageTemperatureUncertainty = landAverageTemperatureUncertainty;
        this.landMaxTemperature = landMaxTemperature;
        this.landMaxTemperatureUncertainty = landMaxTemperatureUncertainty;
        this.landMinTemperature = landMinTemperature;
        this.landMinTemperatureUncertainty = landMinTemperatureUncertainty;
        this.landAndOceanAverageTemperature = landAndOceanAverageTemperature;
        this.landAndOceanAverageTemperatureUncertainty = landAndOceanAverageTemperatureUncertainty;
    }

    public LocalDate getDt() {
        return dt;
    }

    public void setDt(LocalDate dt) {
        this.dt = dt;
    }

    public String getLandAverageTemperature() {
        return landAverageTemperature;
    }

    public void setLandAverageTemperature(String landAverageTemperature) {
        this.landAverageTemperature = landAverageTemperature;
    }

    public String getLandAverageTemperatureUncertainty() {
        return landAverageTemperatureUncertainty;
    }

    public void setLandAverageTemperatureUncertainty(String landAverageTemperatureUncertainty) {
        this.landAverageTemperatureUncertainty = landAverageTemperatureUncertainty;
    }

    public String getLandMaxTemperature() {
        return landMaxTemperature;
    }

    public void setLandMaxTemperature(String landMaxTemperature) {
        this.landMaxTemperature = landMaxTemperature;
    }

    public String getLandMaxTemperatureUncertainty() {
        return landMaxTemperatureUncertainty;
    }

    public void setLandMaxTemperatureUncertainty(String landMaxTemperatureUncertainty) {
        this.landMaxTemperatureUncertainty = landMaxTemperatureUncertainty;
    }

    public String getLandMinTemperature() {
        return landMinTemperature;
    }

    public void setLandMinTemperature(String landMinTemperature) {
        this.landMinTemperature = landMinTemperature;
    }

    public String getLandMinTemperatureUncertainty() {
        return landMinTemperatureUncertainty;
    }

    public void setLandMinTemperatureUncertainty(String landMinTemperatureUncertainty) {
        this.landMinTemperatureUncertainty = landMinTemperatureUncertainty;
    }

    public String getLandAndOceanAverageTemperature() {
        return landAndOceanAverageTemperature;
    }

    public void setLandAndOceanAverageTemperature(String landAndOceanAverageTemperature) {
        this.landAndOceanAverageTemperature = landAndOceanAverageTemperature;
    }

    public String getLandAndOceanAverageTemperatureUncertainty() {
        return landAndOceanAverageTemperatureUncertainty;
    }

    public void setLandAndOceanAverageTemperatureUncertainty(String landAndOceanAverageTemperatureUncertainty) {
        this.landAndOceanAverageTemperatureUncertainty = landAndOceanAverageTemperatureUncertainty;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected GlobalTempDataRow clone() throws CloneNotSupportedException {
        try {
            return (GlobalTempDataRow) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(
                    "The Row object could not be cloned.", e);
        }
    }

    @Override
    public String toString() {
        return dt.toString();
    }

}

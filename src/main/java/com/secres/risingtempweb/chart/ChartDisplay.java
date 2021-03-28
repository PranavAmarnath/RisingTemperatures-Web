package com.secres.risingtempweb.chart;

public interface ChartDisplay {

    void createAndShowChart();

    default Double round(double value) {
        return (double) Math.round(value * 1000d) / 1000d;
    }

}

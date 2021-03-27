package com.secres.vaadinrisingtemp.chart;

public interface ChartDisplay {

    void createAndShowChart();

    default Double round(double value) {
        return (double) Math.round(value * 1000d) / 1000d;
    }

}

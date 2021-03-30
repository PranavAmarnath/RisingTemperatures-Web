package com.secres.risingtempweb.chart;

import com.secres.risingtempweb.Model;
import com.secres.risingtempweb.TableView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

import java.time.ZoneId;

public class SeasonChart implements ChartDisplay {

    private final Model globalModel = TableView.getModel();
    private Chart chart;
    private int start;
    private String season;

    public SeasonChart(int start, String season, VerticalLayout layout) {
        this.start = start;
        this.season = season;
        if(globalModel == null) {
            layout.add(new RouterLink("Upload Table First", TableView.class));
        }
        else {
            createAndShowChart();
        }
    }

    @Override
    public void createAndShowChart() {
        chart = new Chart(ChartType.SPLINE);
        Configuration conf = chart.getConfiguration();
        conf.getTitle().setText("Seasonal Land Temperature in " + season + " (1750-2015)");
        conf.getSubTitle().setText("Data from Berkeley Earth");
        chart.setTimeline(true);
        //chart.setHeight("2000px"); // filler for pack later on

        conf.setExporting(true);

        conf.getxAxis().setTitle("Year");
        conf.getyAxis().setTitle("Temperature \u00B0C");

        Legend legend = new Legend();
        legend.setEnabled(true);

        conf.addSeries(createLandChart());

        conf.getTooltip().setEnabled(true);
        conf.getTooltip().setValueSuffix("\u00B0C");
        conf.setLegend(legend);
    }

    private DataSeries createLandChart() {
        DataSeries seriesLand = new DataSeries("Land Temperature " + season);
        for(int i = start; i < globalModel.getRowList().size(); i+=12) {
            if(!(globalModel.getRowList().get(i).getLandAverageTemperature().isEmpty())) {
                DataSeriesItem item = new DataSeriesItem(globalModel.getRowList().get(i).getDt().atStartOfDay(ZoneId.systemDefault()).toInstant(), round(Double.parseDouble(globalModel.getRowList().get(i).getLandAverageTemperature())));
                item.setName(globalModel.getRowList().get(i).getDt().toString());
                seriesLand.add(item);
            }
        }

        return seriesLand;
    }

    public Chart getChart() {
        return chart;
    }

}

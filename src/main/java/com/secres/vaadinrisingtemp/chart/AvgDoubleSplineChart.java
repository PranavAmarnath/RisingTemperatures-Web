package com.secres.vaadinrisingtemp.chart;

import com.secres.vaadinrisingtemp.MainView;
import com.secres.vaadinrisingtemp.Model;
import com.secres.vaadinrisingtemp.TableView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.time.ZoneId;

@Route("charts/avgtemp")
@PageTitle("Avg Temperature (1850-2015)")
public class AvgDoubleSplineChart extends VerticalLayout implements ChartDisplay {

    private Chart chart;
    private final Model globalModel = TableView.getModel();

    public AvgDoubleSplineChart() {
        if(globalModel == null) {
            add(new RouterLink("Upload Table First", MainView.class));
        }
        else {
            createAndShowChart();
        }
    }

    @Override
    public void createAndShowChart() {
        chart = new Chart();
        Configuration conf = chart.getConfiguration();
        conf.getTitle().setText("Global Average Yearly Temperature (1850-2015)");
        conf.getSubTitle().setText("Data from Berkeley Earth");
        chart.setTimeline(true);
        //chart.setHeight("2000px"); // filler for pack later on

        conf.setExporting(true);

        conf.getxAxis().setTitle("Year");
        conf.getxAxis().setType(AxisType.DATETIME);
        conf.getyAxis().setTitle("Temperature \u00B0C");

        Legend legend = new Legend();
        legend.setEnabled(true);

        Series landSeries = createLandChart();
        landSeries.setPlotOptions(new PlotOptionsSpline());
        conf.addSeries(landSeries);

        Series landAndOceanSeries = createLandAndOceanChart();
        landAndOceanSeries.setPlotOptions(new PlotOptionsSpline());
        conf.addSeries(landAndOceanSeries);

        //Series landAndOceanSeriesUncertainty = createLandAndOceanChart()[1];
        //landAndOceanSeriesUncertainty.setPlotOptions(new PlotOptionsArearange());
        //conf.addSeries(landAndOceanSeriesUncertainty);

        conf.getTooltip().setEnabled(true);
        conf.getTooltip().setValueSuffix("\u00B0C");
        conf.setLegend(legend);

        add(chart);
        setSizeFull(); // pack chart
    }

    private DataSeries createLandChart() {
        DataSeries seriesLand = new DataSeries("Average Land Temperature");
        double currentAvg = 0;
        int count = 0;
        for(int i = 1200; i < globalModel.getRowList().size(); i+=12) {
            currentAvg = 0;
            count = 0;
            for(int j = 0; j < 12; j++) {
                String avgTemp = globalModel.getRowList().get(i+j).getLandAverageTemperature();
                if (!avgTemp.isEmpty()) {
                    count++;
                    currentAvg += Double.parseDouble(avgTemp);
                }
            }
            currentAvg /= count;
            DataSeriesItem item = new DataSeriesItem(globalModel.getRowList().get(i).getDt().atStartOfDay(ZoneId.systemDefault()).toInstant(), round(currentAvg));
            item.setName(globalModel.getRowList().get(i).getDt().toString().substring(0, 4));
            seriesLand.add(item);
        }

        return seriesLand;
    }

    private Series createLandAndOceanChart() {
        DataSeries seriesOcean = new DataSeries("Average Land and Ocean Temperature");
        //RangeSeries seriesOceanUncertainty = new RangeSeries("Average Land and Ocean Temperature Uncertainty");
        //Number[][] rangeUncertainty = new Number[2000][3];

        double currentAvg = 0;
        int count = 0;
        //double currentAvgUncertainty = 0;
        for(int i = 1200; i < globalModel.getRowList().size(); i+=12) {
            currentAvg = 0;
            count = 0;
            for(int j = 0; j < 12; j++) {
                String avgTemp = globalModel.getRowList().get(i+j).getLandAndOceanAverageTemperature();
                //String avgTempUncertainty = globalModel.getRowList().get(i+j).getLandAndOceanAverageTemperatureUncertainty();
                if (!avgTemp.isEmpty()) {
                    count++;
                    currentAvg += Double.parseDouble(avgTemp);
                    //currentAvgUncertainty += Double.parseDouble(avgTempUncertainty);
                }
            }
            currentAvg /= count;
            //currentAvgUncertainty /= count;

            DataSeriesItem oceanItem = new DataSeriesItem(globalModel.getRowList().get(i).getDt().atStartOfDay(ZoneId.systemDefault()).toInstant(), round(currentAvg));
            oceanItem.setName(globalModel.getRowList().get(i).getDt().toString().substring(0, 4));
            seriesOcean.add(oceanItem);

            /*
            rangeUncertainty[i-1200][0] = Integer.parseInt(globalModel.getRowList().get(i).getDt().toString().substring(0, 4));
            rangeUncertainty[i-1200][1] = round(currentAvg) - round(currentAvgUncertainty);
            rangeUncertainty[i-1200][2] = round(currentAvg) + round(currentAvgUncertainty);
            System.out.println(globalModel.getRowList().get(i).getDt().toString().substring(0, 4) + ": " + rangeUncertainty[i-1200][0]);
            */
        }
        //seriesOceanUncertainty.setRangeData(rangeUncertainty);

        return seriesOcean;
        //return new Series[]{seriesOcean, seriesOceanUncertainty};
    }

}

package com.secres.risingtempweb.chart;

import com.secres.risingtempweb.MainView;
import com.secres.risingtempweb.Model;
import com.secres.risingtempweb.TableView;
import com.secres.risingtempweb.ext.Regression;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("charts/avgscatter")
@PageTitle("Avg Temperature (1750-2015)")
public class AvgScatterChart extends VerticalLayout implements ChartDisplay {

    private Chart chart;
    private final Model globalModel = TableView.getModel();

    public AvgScatterChart() {
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
        conf.getTitle().setText("Global Average Yearly Land Temperature (1750-2015)");
        conf.getSubTitle().setText("Data from Berkeley Earth");
        //chart.setTimeline(true);
        //chart.setHeight("2000px"); // filler for pack later on

        conf.setExporting(true);

        conf.getxAxis().setTitle("Year");
        //conf.getxAxis().setType(AxisType.DATETIME);
        conf.getyAxis().setTitle("Temperature \u00B0C");

        Legend legend = new Legend();
        legend.setEnabled(true);

        Series landSeries = (Series) initModel()[0];
        landSeries.setPlotOptions(new PlotOptionsScatter());
        conf.addSeries(landSeries);

        double[][] regressionData = (double[][]) initModel()[1];
        double[] coefficients = Regression.getOLSRegression(regressionData);
        double b = coefficients[0]; // intercept
        double m = coefficients[1]; // slope
        DataSeries trend = new DataSeries("Trend");
        trend.setPlotOptions(new PlotOptionsLine());
        double x = 1750;
        trend.add(new DataSeriesItem(x, m * x + b));
        x = 2015;
        trend.add(new DataSeriesItem(x, m * x + b));
        conf.addSeries(trend);

        conf.getTooltip().setEnabled(true);
        conf.getTooltip().setValueSuffix("\u00B0C");
        conf.setLegend(legend);

        add(chart);
        setSizeFull(); // pack chart
    }

    private Object[] initModel() {
        DataSeries seriesLand = new DataSeries("Average Land Temperature");
        double[][] seriesData = new double[4000][2];
        double currentAvg = 0;
        int count = 0;
        for(int i = 0; i < globalModel.getRowList().size(); i+=12) {
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
            seriesData[i][0] = Double.parseDouble(globalModel.getRowList().get(i).getDt().toString().substring(0, 4));
            seriesData[i][1] = currentAvg;
            DataSeriesItem item = new DataSeriesItem(Double.parseDouble(globalModel.getRowList().get(i).getDt().toString().substring(0, 4)), round(currentAvg));
            item.setName(globalModel.getRowList().get(i).getDt().toString().substring(0, 4));
            seriesLand.add(item);
        }

        return new Object[]{seriesLand, seriesData};
    }

}

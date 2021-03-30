package com.secres.risingtempweb.chart;

import com.secres.risingtempweb.Model;
import com.secres.risingtempweb.TableView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

import java.util.HashMap;

public class GaugeChart extends Div implements ChartDisplay {

    private final Model globalModel = TableView.getModel();
    private HashMap<String, Double> seriesModel;

    public GaugeChart() {
        if(globalModel == null) {
            add(new RouterLink("Upload Table First", TableView.class));
        }
        else {
            seriesModel = createAvgModel();
            createAndShowChart();
        }
    }

    @Override
    public void createAndShowChart() {
        Chart chart = new Chart(ChartType.SOLIDGAUGE);
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Avg. Land Temperature Gauge \u00B0C");

        conf.setExporting(true);

        Pane pane = conf.getPane();
        pane.setCenter(new String[] {"50%", "50%"});
        pane.setStartAngle(-90);
        pane.setEndAngle(90);

        Background paneBackground = new Background();
        paneBackground.setInnerRadius("60%");
        paneBackground.setOuterRadius("100%");
        paneBackground.setShape(BackgroundShape.ARC);
        pane.setBackground(paneBackground);

        YAxis yAxis = conf.getyAxis();
        yAxis.setTickAmount(2);
        yAxis.setTitle("Temp.");
        yAxis.setMinorTickInterval("null");
        yAxis.getTitle().setY(-50);
        yAxis.getLabels().setY(16);
        yAxis.setMin(0);
        yAxis.setMax(10);

        ListSeries series = new ListSeries("Temperature", seriesModel.get("1750"));
        conf.addSeries(series);

        HorizontalLayout sliderLayout = new HorizontalLayout();

        PaperSlider slider = new PaperSlider();
        slider.setMin(1750);
        slider.setValue(1750);
        slider.setEditable(true);
        slider.setPin(true);
        series.updateSeries();
        slider.addValueChangeListener(e -> {
            series.setData(seriesModel.get(String.valueOf(e.getValue())));
            series.updateSeries();
        });
        slider.setMax(2015);
        slider.setEnabled(true);
        slider.setVisible(true);

        sliderLayout.addAndExpand(slider);
        //sliderLayout.setAlignItems(Alignment.CENTER);
        //sliderLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        add(chart, sliderLayout);
    }

    private HashMap<String, Double> createAvgModel() {
        HashMap<String, Double> seriesLand = new HashMap<>();
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
            String date = globalModel.getRowList().get(i).getDt().toString().substring(0, 4);
            double value = round(currentAvg);
            seriesLand.put(date, value);
        }

        return seriesLand;
    }

}

package com.secres.risingtempweb.chart;

import com.secres.risingtempweb.Model;
import com.secres.risingtempweb.TableView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class SeptemberSeasonChart extends VerticalLayout {

    private final Model globalModel = TableView.getModel();

    public SeptemberSeasonChart() {
        if(globalModel == null) {
            add(new RouterLink("Upload Table First", TableView.class));
        }
        else {
            SeasonChart septSeason = new SeasonChart(8, "September", this);
            Chart chart = septSeason.getChart();

            add(chart);
            setSizeFull();
        }
    }

}

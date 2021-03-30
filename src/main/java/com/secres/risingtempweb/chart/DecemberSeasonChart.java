package com.secres.risingtempweb.chart;

import com.secres.risingtempweb.Model;
import com.secres.risingtempweb.TableView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class DecemberSeasonChart extends VerticalLayout {

    private final Model globalModel = TableView.getModel();

    public DecemberSeasonChart() {
        if(globalModel == null) {
            add(new RouterLink("Upload Table First", TableView.class));
        }
        else {
            SeasonChart decSeason = new SeasonChart(11, "December", this);
            Chart chart = decSeason.getChart();

            add(chart);
            setSizeFull();
        }
    }

}

package com.secres.risingtempweb.chart;

import com.secres.risingtempweb.Model;
import com.secres.risingtempweb.TableView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MarchSeasonChart extends VerticalLayout {

    private final Model globalModel = TableView.getModel();

    public MarchSeasonChart() {
        if(globalModel == null) {
            add(new RouterLink("Upload Table First", TableView.class));
        }
        else {
            SeasonChart marchSeason = new SeasonChart(2, "March", this);
            Chart chart = marchSeason.getChart();

            add(chart);
            setSizeFull();
        }
    }

}

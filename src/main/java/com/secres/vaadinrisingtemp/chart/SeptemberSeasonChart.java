package com.secres.vaadinrisingtemp.chart;

import com.secres.vaadinrisingtemp.Model;
import com.secres.vaadinrisingtemp.TableView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("charts/seasons/september")
@PageTitle("Land Temperature in September")
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

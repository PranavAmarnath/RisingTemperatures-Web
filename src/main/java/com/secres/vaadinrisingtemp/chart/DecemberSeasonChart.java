package com.secres.vaadinrisingtemp.chart;

import com.secres.vaadinrisingtemp.Model;
import com.secres.vaadinrisingtemp.TableView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("charts/seasons/december")
@PageTitle("Land Temperature in December")
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

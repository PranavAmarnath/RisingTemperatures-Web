package com.secres.vaadinrisingtemp.chart;

import com.secres.vaadinrisingtemp.Model;
import com.secres.vaadinrisingtemp.TableView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("charts/seasons/march")
@PageTitle("Land Temperature in March")
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

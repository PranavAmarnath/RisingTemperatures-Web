package com.secres.risingtempweb.chart;

import com.secres.risingtempweb.Model;
import com.secres.risingtempweb.TableView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("charts/seasons/june")
@PageTitle("Land Temperature in June")
public class JuneSeasonChart extends VerticalLayout {

    private final Model globalModel = TableView.getModel();

    public JuneSeasonChart() {
        if(globalModel == null) {
            add(new RouterLink("Upload Table First", TableView.class));
        }
        else {
            SeasonChart juneSeason = new SeasonChart(5, "June", this);
            Chart chart = juneSeason.getChart();

            add(chart);
            setSizeFull();
        }
    }

}

package com.secres.vaadinrisingtemp.chart;

import com.secres.vaadinrisingtemp.Model;
import com.secres.vaadinrisingtemp.TableView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.HashMap;
import java.util.Map;

@Route("charts")
@PageTitle("View Charts")
public class WelcomeScreen extends VerticalLayout {

    private final Model globalModel = TableView.getModel();
    private AppLayout appLayout = new AppLayout();

    public WelcomeScreen() {
        SplitLayout splitLayout = new SplitLayout();
        if(globalModel == null) {
            splitLayout.addToPrimary(new RouterLink("Upload Table First", TableView.class));
        }
        else {
            splitLayout.addToPrimary(showNewScreen());
            splitLayout.addToSecondary(appLayout);
            splitLayout.setSplitterPosition(15);
            Page page = UI.getCurrent().getPage();
            page.addBrowserWindowResizeListener(e -> {
                splitLayout.setSizeFull();
            });
        }
        add(splitLayout);
        splitLayout.setSizeFull();
    }

    private Accordion showNewScreen() {
        Accordion accordion = new Accordion();

        Tab avgSplineTab = new Tab("/avgtemp");
        AvgDoubleSplineChart avgSplineChart = new AvgDoubleSplineChart();
        Div avgSplinePage = new Div(avgSplineChart);
        avgSplinePage.setVisible(true);
        appLayout.setContent(avgSplinePage);

        Tab seasonsButtonsTab = new Tab("/seasons");
        SeasonsButtons seasonsButtons = new SeasonsButtons();
        Div seasonsButtonsPage = new Div(seasonsButtons);
        seasonsButtonsPage.setVisible(false);

        Tab gaugeTab = new Tab("/gauge");
        GaugeChart gaugeChart = new GaugeChart();
        Div gaugePage = new Div(gaugeChart);
        gaugePage.setVisible(false);

        Tab avgScatterTab = new Tab("/avgscatter");
        AvgScatterChart avgScatterChart = new AvgScatterChart();
        Div avgScatterPage = new Div(avgScatterChart);
        avgScatterPage.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(avgSplineTab, avgSplinePage);
        tabsToPages.put(seasonsButtonsTab, seasonsButtonsPage);
        tabsToPages.put(gaugeTab, gaugePage);
        tabsToPages.put(avgScatterTab, avgScatterPage);

        Tabs tabs = new Tabs(avgSplineTab, seasonsButtonsTab, gaugeTab, avgScatterTab);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addSelectedChangeListener(e -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            appLayout.setContent(selectedPage);
        });
        accordion.add("/charts", tabs);

        return accordion;
    }

    /*
    private void oldScreen() {
        if(globalModel == null) {
            add(new RouterLink("Upload Table First", TableView.class));
        }
        else {
            RouterLink avgSpline = new RouterLink("Average Temperature", AvgDoubleSplineChart.class);
            RouterLink seasons = new RouterLink("Temperature by Month", SeasonsButtons.class);
            RouterLink gauge = new RouterLink("Gauge Temperature", GaugeChart.class);

            add(avgSpline, seasons, gauge);
        }
    }
    */

}

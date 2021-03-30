package com.secres.risingtempweb.chart;

import com.secres.risingtempweb.MainView;
import com.secres.risingtempweb.Model;
import com.secres.risingtempweb.TableView;
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
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Route(value = "charts", layout = MainView.class)
@PageTitle("Charts | Rising Temperatures")
@UIScope
public class WelcomeScreen extends VerticalLayout implements RouterLayout {

    private final Model globalModel = TableView.getModel();
    private AppLayout appLayout = getAppLayout();

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

    private Accordion[] showNewScreen() {
        Accordion accordionAvg = new Accordion();
        Accordion accordionSeasons = new Accordion();

        Tab avgSplineTab = new Tab("/avgtemp");
        AvgDoubleSplineChart avgSplineChart = new AvgDoubleSplineChart();
        Div avgSplinePage = new Div(avgSplineChart);
        avgSplinePage.setVisible(true);
        appLayout.setContent(avgSplinePage);

        Tab gaugeTab = new Tab("/gauge");
        GaugeChart gaugeChart = new GaugeChart();
        Div gaugePage = new Div(gaugeChart);
        gaugePage.setVisible(false);

        Tab avgScatterTab = new Tab("/avgscatter");
        AvgScatterChart avgScatterChart = new AvgScatterChart();
        Div avgScatterPage = new Div(avgScatterChart);
        avgScatterPage.setVisible(false);

        Map<Tab, Div> chartsMap = new HashMap<>();
        chartsMap.put(avgSplineTab, avgSplinePage);
        chartsMap.put(gaugeTab, gaugePage);
        chartsMap.put(avgScatterTab, avgScatterPage);

        Tabs tabsAvg = new Tabs();
        tabsAvg.add(avgSplineTab);
        tabsAvg.add(gaugeTab);
        tabsAvg.add(avgScatterTab);
        tabsAvg.setOrientation(Tabs.Orientation.VERTICAL);
        accordionAvg.add("/avg", tabsAvg);

        Tab marchTab = new Tab("/march");
        MarchSeasonChart marchSeason = new MarchSeasonChart();
        Div marchPage = new Div(marchSeason);
        marchPage.setVisible(false);

        Tab juneTab = new Tab("/june");
        JuneSeasonChart juneSeason = new JuneSeasonChart();
        Div junePage = new Div(juneSeason);
        junePage.setVisible(false);

        Tab septemberTab = new Tab("/september");
        SeptemberSeasonChart septemberSeason = new SeptemberSeasonChart();
        Div septemberPage = new Div(septemberSeason);
        septemberPage.setVisible(false);

        Tab decemberTab = new Tab("/december");
        DecemberSeasonChart decemberSeason = new DecemberSeasonChart();
        Div decemberPage = new Div(decemberSeason);
        decemberPage.setVisible(false);

        Map<Tab, Div> seasonsMap = new HashMap<>();
        seasonsMap.put(marchTab, marchPage);
        seasonsMap.put(juneTab, junePage);
        seasonsMap.put(septemberTab, septemberPage);
        seasonsMap.put(decemberTab, decemberPage);

        Tabs tabsSeasons = new Tabs(marchTab, juneTab, septemberTab, decemberTab);
        tabsSeasons.setOrientation(Tabs.Orientation.VERTICAL);

        tabsAvg.addSelectedChangeListener(e -> {
            chartsMap.values().forEach(page -> page.setVisible(false));
            seasonsMap.values().forEach(page -> page.setVisible(false));
            Component selectedPage = chartsMap.get(tabsAvg.getSelectedTab());
            selectedPage.setVisible(true);
            appLayout.setContent(selectedPage);
        });
        tabsSeasons.addSelectedChangeListener(e -> {
            chartsMap.values().forEach(page -> page.setVisible(false));
            seasonsMap.values().forEach(page -> page.setVisible(false));
            Component selectedPage = seasonsMap.get(tabsSeasons.getSelectedTab());
            selectedPage.setVisible(true);
            appLayout.setContent(selectedPage);
        });
        accordionAvg.add("/seasons", tabsSeasons);

        return new Accordion[]{accordionAvg, accordionSeasons};
    }

    public AppLayout getAppLayout() {
        return Objects.requireNonNullElseGet(appLayout, AppLayout::new);
    }

}

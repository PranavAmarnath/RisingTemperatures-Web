package com.secres.vaadinrisingtemp;

import com.secres.vaadinrisingtemp.chart.WelcomeScreen;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.util.HashMap;
import java.util.Map;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Rising Temperatures",
        shortName = "risingtemp",
        description = "Web App Port of Rising Temperatures.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@PageTitle("Rising Temperatures")
public class MainView extends AppLayout {

    public MainView() {
        setPrimarySection(AppLayout.Section.DRAWER);
        Image img = new Image("https://raw.githubusercontent.com/PranavAmarnath/RisingTemperatures/master/src/main/resources/gear.png", "Rising Temperatures");
        img.setHeight("40px");
        Button iconButton = new Button(img);
        iconButton.addClickListener(e -> iconButton.getUI().ifPresent(ui -> ui.navigate("")));

        Tab tableTab = new Tab("Table");
        TableView tableView = new TableView();
        Div tablePage = new Div(tableView);
        tablePage.setVisible(true);
        setContent(tablePage);

        Tab chartsTab = new Tab("Charts");
        WelcomeScreen welcomeScreenCharts = new WelcomeScreen();
        Div chartsPage = new Div(welcomeScreenCharts);
        chartsPage.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tableTab, tablePage);
        tabsToPages.put(chartsTab, chartsPage);

        Tabs tabs = new Tabs(tableTab, chartsTab);
        tabs.addSelectedChangeListener(e -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            setContent(selectedPage);
        });
        addToNavbar(iconButton, tabs);
    }

}

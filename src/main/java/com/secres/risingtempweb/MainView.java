package com.secres.risingtempweb;

import com.flowingcode.vaadin.addons.carousel.Carousel;
import com.flowingcode.vaadin.addons.carousel.Slide;
import com.secres.risingtempweb.chart.WelcomeScreen;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;

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
@PageTitle("Home | Rising Temperatures")
public class MainView extends AppLayout {

    public MainView() {
        Slide slide1 = new Slide(createSlide1Layout());
        Slide slide2 = new Slide(createSlide2Layout());

        Carousel carousel = new Carousel(slide1, slide2)
                .withAutoProgress().withSlideDuration(4).withStartPosition(0);
        carousel.setHideNavigation(false);
        carousel.setWidthFull();
        carousel.setHeight("500px");
        carousel.setVisible(true);
        setContent(carousel);

        RouterLink tableTab = new RouterLink("Table", TableView.class);
        TableView tableView = new TableView();
        Div tablePage = new Div(tableView);
        tablePage.setVisible(false);

        RouterLink chartsTab = new RouterLink("Charts", WelcomeScreen.class);
        WelcomeScreen welcomeScreenCharts = new WelcomeScreen();
        Div chartsPage = new Div(welcomeScreenCharts);
        chartsPage.setVisible(false);

        RouteTabs tabs = new RouteTabs();
        tabs.add(tableTab);
        tabs.add(chartsTab);

        setPrimarySection(AppLayout.Section.DRAWER);
        Image img = new Image("https://raw.githubusercontent.com/PranavAmarnath/RisingTemperatures/master/src/main/resources/gear.png", "Rising Temperatures");
        img.setHeight("40px");
        Button iconButton = new Button(img);
        iconButton.addClickListener(e -> {
            iconButton.getUI().ifPresent(ui -> ui.navigate(""));
            tablePage.setVisible(false);
            chartsPage.setVisible(false);
            tabs.setSelectedTab(null);

            carousel.setVisible(true);
            setContent(carousel);
        });

        /*
        Map<RouterLink, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tableTab, tablePage);
        tabsToPages.put(chartsTab, chartsPage);
        */

        /*
        tabs.addSelectedChangeListener(e -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            setContent(selectedPage);
        });
        */
        addToNavbar(iconButton, tabs);
    }

    private Component createSlide1Layout() {
        // Content of the second slide
        VerticalLayout layout = new VerticalLayout();
        configSlideLayout(layout);

        H1 headLine = new H1();
        headLine.setText("About the Project");
        headLine.getStyle().set("color", "white");
        Div content = new Div();
        content.setText("The goal of this project is to deeply analyze large datasets of global and regional climate change data by pinpointing and investigating trends across 197 countries over 250 years.");
        content.getStyle().set("color", "white");
        content.getStyle().set("white-space", "pre-wrap");
        Button button = new Button("View Project");
        button.addClickListener(e -> {
            UI.getCurrent().getPage().setLocation("https://github.com/PranavAmarnath/RisingTemperatures");
        });
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addThemeVariants(ButtonVariant.LUMO_LARGE);
        layout.add(button, content, headLine);

        return layout;
    }

    private Component createSlide2Layout() {
        // Content of the first slide
        VerticalLayout layout = new VerticalLayout();
        configSlideLayout(layout);

        H1 headLine = new H1();
        headLine.setText("Video & Demo.");
        headLine.getStyle().set("color", "white");
        Div content = new Div();
        IFrame iFrame = new IFrame("https://www.youtube.com/embed/PKc64eGJSAU");
        iFrame.setHeight("261px");
        iFrame.setWidth("464px");
        iFrame.setAllow("accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture");
        iFrame.getElement().setAttribute("allowfullscreen", true);
        iFrame.getElement().setAttribute("frameborder", "0");
        content.add(iFrame);
        layout.add(content, headLine);

        return layout;
    }

    private void configSlideLayout(VerticalLayout verticalLayout) {
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        verticalLayout.setSizeFull();
        verticalLayout.getStyle().set("padding-left", "10%");
        verticalLayout.getStyle().set("padding-right", "10%");
        verticalLayout.getStyle().set("padding-bottom", "10%");
        verticalLayout.getStyle().set("flex-direction", "column-reverse");
        verticalLayout.getStyle().set("background-color", "#737A84");
    }

}

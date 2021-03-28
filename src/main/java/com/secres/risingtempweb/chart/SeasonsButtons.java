package com.secres.risingtempweb.chart;

import com.secres.risingtempweb.Model;
import com.secres.risingtempweb.TableView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("charts/seasons")
@PageTitle("Choose Season")
public class SeasonsButtons extends Div {

    private final Model globalModel = TableView.getModel();

    public SeasonsButtons() {
        if(globalModel == null) {
            add(new RouterLink("Upload Table First", TableView.class));
        }
        else {
            createAndShowGUI();
        }
    }

    public void createAndShowGUI() {
        VerticalLayout mainLayout = new VerticalLayout();
        HorizontalLayout layout1 = new HorizontalLayout();
        Button marchButton = new Button("March");
        marchButton.addClickListener(e -> {
            marchButton.getUI().ifPresent(ui -> ui.navigate("charts/seasons/march"));
        });
        Button juneButton = new Button("June");
        juneButton.addClickListener(e -> {
            juneButton.getUI().ifPresent(ui -> ui.navigate("charts/seasons/june"));
        });
        layout1.add(marchButton, juneButton);
        layout1.setAlignItems(FlexComponent.Alignment.CENTER); // horizontal center
        layout1.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); // vertical center

        HorizontalLayout layout2 = new HorizontalLayout();
        Button septButton = new Button("September");
        septButton.addClickListener(e -> {
            septButton.getUI().ifPresent(ui -> ui.navigate("charts/seasons/september"));
        });
        Button decButton = new Button("December");
        decButton.addClickListener(e -> {
            decButton.getUI().ifPresent(ui -> ui.navigate("charts/seasons/december"));
        });
        layout2.add(septButton, decButton);
        layout2.setAlignItems(FlexComponent.Alignment.CENTER);
        layout2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        mainLayout.add(layout1, layout2);
        setHeightFull();
        mainLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        mainLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        add(mainLayout);
    }

}

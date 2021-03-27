package com.secres.vaadinrisingtemp;

import com.secres.vaadinrisingtemp.chart.WelcomeScreen;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.io.PrintWriter;
import java.io.StringWriter;

@Route("table")
@PageTitle("Table Data")
public class TableView extends VerticalLayout {

    static Grid<GlobalTempDataRow> grid;
    static UI ui = UI.getCurrent();
    static Model globalModel;

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     */
    public TableView() {
        createAndShowGrid();
        createAndShowUpload();
        if(globalModel != null) {
            grid.setItems(globalModel.getRowList());
        }
    }

    private void createAndShowGrid() {
        grid = new Grid<>(GlobalTempDataRow.class);
        add(grid);
    }

    private void setColumns() {
        globalModel = new Model();
    }

    private void createAndShowUpload() {
        Div output = new Div();

        try {
            if (globalModel == null) {
                setColumns();
            }
            add(new RouterLink("View Produced Charts", WelcomeScreen.class));
            setSizeFull(); // pack components
        } catch (Exception e) {
            Paragraph component = new Paragraph();
            output.removeAll();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            showOutput(exceptionAsString, component, output);
        }

        add(output);
    }

    private void showOutput(String text, Component content,
                            HasComponents outputContainer) {
        HtmlComponent p = new HtmlComponent(Tag.P);
        p.getElement().setText(text);
        outputContainer.add(p);
        outputContainer.add(content);
    }

    public static Grid<GlobalTempDataRow> getGrid() {
        return grid;
    }

    public static UI getUIAccess() {
        return ui;
    }

    public static Model getModel() {
        return globalModel;
    }

}

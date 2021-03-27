package com.secres.vaadinrisingtemp;

import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("home")
@PageTitle("Home")
public class HomeView extends VerticalLayout {

    public HomeView() {
        IFrame iFrame = new IFrame("https://www.youtube.com/embed/PKc64eGJSAU");
        iFrame.setHeight("315px");
        iFrame.setWidth("560px");
        iFrame.setAllow("accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture");
        iFrame.getElement().setAttribute("allowfullscreen", true);
        iFrame.getElement().setAttribute("frameborder", "0");
        add(iFrame);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

}

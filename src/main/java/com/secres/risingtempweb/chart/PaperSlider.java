package com.secres.risingtempweb.chart;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

@Tag("paper-slider")
@NpmPackage(value = "@polymer/paper-slider",
        version = "3.0.1")
@JsModule("@polymer/paper-slider/paper-slider.js")
public class PaperSlider extends AbstractSinglePropertyField<PaperSlider, Integer> {

    public PaperSlider() {
        super("value", 0, false);
    }

    public void setMax(int max) {
        this.getElement().setProperty("max", max);
    }

    public void setMin(int min) {
        this.getElement().setProperty("min", min);
    }

    public void setEditable(boolean editable) {
        this.getElement().setProperty("editable", editable);
    }

    public void setPin(boolean pin) {
        this.getElement().setProperty("pin", pin);
    }

}
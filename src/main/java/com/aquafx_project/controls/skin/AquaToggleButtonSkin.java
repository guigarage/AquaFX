/**
 * Copyright (c) 2013, Claudine Zillmann All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met: * Redistributions of source code must retain the
 * above copyright notice, this list of conditions and the following disclaimer. * Redistributions
 * in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.
 * * Neither the name of AquaFX, the website aquafx-project.com, nor the names of its contributors
 * may be used to endorse or promote products derived from this software without specific prior
 * written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL CLAUDINE ZILLMANN BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.aquafx_project.controls.skin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.control.Control;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import com.aquafx_project.controls.skin.css.AquaCssProperties;
import com.aquafx_project.controls.skin.effects.FocusBorder;
import com.aquafx_project.controls.skin.effects.Shadow;
import com.aquafx_project.controls.skin.styles.ButtonType;
import com.sun.javafx.scene.control.skin.ToggleButtonSkin;

public class AquaToggleButtonSkin extends ToggleButtonSkin implements AquaSkin, AquaFocusBorder {

    public AquaToggleButtonSkin(ToggleButton button) {
        super(button);

        registerChangeListener(button.focusedProperty(), "FOCUSED");
        registerChangeListener(button.selectedProperty(), "SELECTED");

        if (getSkinnable().isFocused()) {
            setFocusBorder();
        } else {
            setDropShadow();
        }

        if (getSkinnable().isSelected()) {
            adjustToggleGroupBorders();
        }
    }

    private FocusBorder focusBorder;

    public FocusBorder getFocusBorder() {
        if (focusBorder == null) {
            focusBorder = new FocusBorder();
        }
        return focusBorder;
    }

    private void setFocusBorder() {
        getFocusBorder().setInnerFocusColor((Color) innerFocusColorProperty().get());
        getFocusBorder().setColor((Color) outerFocusColorProperty().get());
        getSkinnable().setEffect(getFocusBorder());
    }

    private void setDropShadow() {
        boolean isPill = false;
        if (getSkinnable().getStyleClass().contains(ButtonType.LEFT_PILL.getStyleName()) || getSkinnable().getStyleClass().contains(
                ButtonType.CENTER_PILL.getStyleName()) || getSkinnable().getStyleClass().contains(
                ButtonType.RIGHT_PILL.getStyleName())) {
            isPill = true;
        }
        getSkinnable().setEffect(new Shadow(isPill));
    }

    @Override protected void handleControlPropertyChanged(String p) {
        super.handleControlPropertyChanged(p);
        if (p == "FOCUSED") {
            if (getSkinnable().isFocused()) {
                setFocusBorder();
            } else if (!getSkinnable().isFocused() || getSkinnable().isDisable()) {
                setDropShadow();
            }
        }
        else if (p == "SELECTED") {
            adjustToggleGroupBorders();
        }
    }

    private void adjustToggleGroupBorders() {
        if (getSkinnable().isSelected() && getSkinnable().getToggleGroup() != null) {
            List<Toggle> toggles = getSkinnable().getToggleGroup().getToggles();
            int i = toggles.indexOf(getSkinnable().getToggleGroup().getSelectedToggle());
            if (toggles.size() > i + 1) {
                ToggleButton toggle = (ToggleButton) toggles.get(i + 1);
                toggle.getStyleClass().add("neighbor");
                for (int j = 0; toggles.size() > j; j++) {
                    if (j != i + 1) {
                        ((ToggleButton) toggles.get(j)).getStyleClass().remove("neighbor");
                    }
                }
            }
        } else if (!getSkinnable().isSelected() && getSkinnable().getToggleGroup() != null) {
            List<Toggle> toggles = getSkinnable().getToggleGroup().getToggles();
            int i = toggles.indexOf(getSkinnable());
            if (toggles.size() > i + 1) {
                ToggleButton toggle = (ToggleButton) toggles.get(i + 1);
                toggle.getStyleClass().remove("neighbor");
            }
        }
    }

    /***********************************************************************************
     * Adding the possibility to set a CSS-Property *
     **********************************************************************************/

    @Override public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        List<CssMetaData<? extends Styleable, ?>> ret = new ArrayList<>(super.getCssMetaData());
        ret.addAll(getClassCssMetaData());
        return ret;
    }

    private static class StyleableProperties {
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<CssMetaData<? extends Styleable, ?>>(Control.getClassCssMetaData());
            styleables.add(AquaCssProperties.getInnerFocusColorMetaData());
            styleables.add(AquaCssProperties.getOuterFocusColorMetaData());
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    /**
     * @return The CssMetaData associated with this class, which may include the CssMetaData of its
     *         super classes.
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    /***********************************************************************************
     * Adding the possibility to set the inner focus color to a Button via CSS-Property *
     **********************************************************************************/

    private ObjectProperty<Paint> innerFocusColor;

    @Override public final ObjectProperty<Paint> innerFocusColorProperty() {
        if (innerFocusColor == null) {
            innerFocusColor = AquaCssProperties.createProperty(this, "innerFocusColor",
                    AquaCssProperties.getInnerFocusColorMetaData());
        }
        return innerFocusColor;
    }

    @Override public void setInnerFocusColor(Paint innerFocusColor) {
        innerFocusColorProperty().setValue(innerFocusColor);
    }

    @Override public Paint getInnerFocusColor() {
        return innerFocusColor == null ? null : innerFocusColor.getValue();
    }

    /***********************************************************************************
     * Adding the possibility to set the outer focus color to a Button via CSS-Property *
     **********************************************************************************/

    private ObjectProperty<Paint> outerFocusColor;

    @Override public final ObjectProperty<Paint> outerFocusColorProperty() {
        if (outerFocusColor == null) {
            outerFocusColor = AquaCssProperties.createProperty(this, "outerFocusColor",
                    AquaCssProperties.getOuterFocusColorMetaData());
        }
        return outerFocusColor;
    }

    @Override public void setOuterFocusColor(Paint outerFocusColor) {
        outerFocusColorProperty().setValue(outerFocusColor);
    }

    @Override public Paint getOuterFocusColor() {
        return outerFocusColor == null ? null : outerFocusColor.getValue();
    }
}

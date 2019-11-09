/**
 * Copyright (c) 2013, Claudine Zillmann
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of AquaFX, the website aquafx-project.com, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL CLAUDINE ZILLMANN BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.aquafx_project.controls.skin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import com.aquafx_project.controls.skin.css.AquaCssProperties;
import com.aquafx_project.controls.skin.effects.Shadow;
import com.sun.javafx.scene.control.skin.CheckBoxSkin;


public class AquaCheckBoxSkin extends CheckBoxSkin implements AquaSkin, AquaFocusBorder{

    public AquaCheckBoxSkin(CheckBox checkbox) {
        super(checkbox);
        
        registerChangeListener(checkbox.focusedProperty(), "FOCUSED");
        registerChangeListener(checkbox.selectedProperty(), "SELECTED");

        if (getSkinnable().isFocused()) {
            setFocusBorder();
        } else {
            setDropShadow();
        }
        
        final ChangeListener<Boolean> windowFocusChangedListener = new ChangeListener<Boolean>() {

            @Override public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (newValue != null) {
                    if (!newValue.booleanValue()) {
                        if (getSkinnable().isSelected() | getSkinnable().isIndeterminate()) {

                            for (Node child : getChildren()) {
                                if (child.getStyleClass().get(0).equals("box")) {
                                    child.getStyleClass().add("unfocused");
                                }
                            }
                        }
                    } else {
                        for (Node child : getChildren()) {
                            if (child.getStyleClass().get(0).equals("box")) {
                                child.getStyleClass().remove("unfocused");
                            }
                        }
                    }
                }

            }
        };

        getSkinnable().sceneProperty().addListener(new ChangeListener<Scene>() {

            @Override public void changed(ObservableValue<? extends Scene> observableValue, Scene oldScene, Scene newScene) {
                if (oldScene != null && oldScene.getWindow() != null) {
                    oldScene.getWindow().focusedProperty().removeListener(windowFocusChangedListener);
                }
                if (newScene != null && newScene.getWindow() != null) {
                    newScene.getWindow().focusedProperty().addListener(windowFocusChangedListener);
                }

            }
        });

        if (getSkinnable().getScene() != null && getSkinnable().getScene().getWindow() != null) {
            getSkinnable().getScene().getWindow().focusedProperty().addListener(windowFocusChangedListener);
        }

    }

    private void setDropShadow() {
        for (Node child : getChildren()) {
            if (child.getStyleClass().get(0).equals("box")) {
                child.setEffect(new Shadow(false));
            }
        }
    }

    private void setSelectedFocusBorder() {
        InnerShadow innerFocus = new InnerShadow();
        innerFocus.setColor((Color) innerFocusColorProperty().get());
        innerFocus.setBlurType(BlurType.ONE_PASS_BOX);
        innerFocus.setRadius(6.5);
        innerFocus.setChoke(0.7);
        innerFocus.setOffsetX(0.0);
        innerFocus.setOffsetY(0.0);

        DropShadow outerFocus = new DropShadow();
        outerFocus.setColor((Color) outerFocusColorProperty().get());
        outerFocus.setBlurType(BlurType.ONE_PASS_BOX);
        outerFocus.setRadius(7.0);
        outerFocus.setSpread(0.7);
        outerFocus.setOffsetX(0.0);
        outerFocus.setOffsetY(0.0);
        outerFocus.setInput(innerFocus);

        for (Node child : getChildren()) {
            if (child instanceof StackPane) {
                child.setEffect(outerFocus);
            }
        }
    }

    private void setFocusBorder() {
        InnerShadow innerFocus = new InnerShadow();
        innerFocus.setColor((Color) innerFocusColorProperty().get());
        innerFocus.setBlurType(BlurType.ONE_PASS_BOX);
        innerFocus.setRadius(6.5);
        innerFocus.setChoke(0.7);
        innerFocus.setOffsetX(0.0);
        innerFocus.setOffsetY(0.0);

        DropShadow outerFocus = new DropShadow();
        outerFocus.setColor((Color) outerFocusColorProperty().get());
        outerFocus.setBlurType(BlurType.ONE_PASS_BOX);
        outerFocus.setRadius(5.0);
        outerFocus.setSpread(0.6);
        outerFocus.setOffsetX(0.0);
        outerFocus.setOffsetY(0.0);
        outerFocus.setInput(innerFocus);

        for (Node child : getChildren()) {
            if (child instanceof StackPane) {
                child.setEffect(outerFocus);
            }
        }
    }
    

    @Override protected void handleControlPropertyChanged(String p) {
        super.handleControlPropertyChanged(p);
        if (p == "SELECTED") {
            if (getSkinnable().isFocused()) {
                if (getSkinnable().isSelected()) {
                    setSelectedFocusBorder();
                } else {
                    setFocusBorder();
                }
            }
        }
        if (p == "FOCUSED") {
            if (getSkinnable().isFocused()) {
                if (getSkinnable().isSelected()) {
                    setSelectedFocusBorder();
                } else {
                    setFocusBorder();
                }
            } else if (!getSkinnable().isFocused()) {
                setDropShadow();
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

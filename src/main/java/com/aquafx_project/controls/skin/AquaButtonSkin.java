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

import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

import com.aquafx_project.controls.skin.css.AquaCssProperties;
import com.aquafx_project.controls.skin.effects.FocusBorder;
import com.aquafx_project.controls.skin.effects.Shadow;
import com.aquafx_project.controls.skin.styles.ButtonType;
import com.aquafx_project.controls.skin.styles.MacOSDefaultIcons;
import com.aquafx_project.util.BindableTransition;
import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.css.converters.StringConverter;
import com.sun.javafx.scene.control.skin.ButtonSkin;

public class AquaButtonSkin extends ButtonSkin implements AquaSkin, AquaFocusBorder {

    private BindableTransition defaultButtonTransition;

    private String usualButtonStyle = "-fx-background-color: rgb(255, 255, 255), linear-gradient(#ffffff 20%, #ececec 60%, #ececec 80%, #eeeeee 100%); -fx-background-insets:  0, 1; -fx-background-radius: 4, 4; -fx-border-radius: 4; -fx-border-width: 0.5; -fx-border-color: rgb(129, 129, 129);";

    private String armedButtonStyle = armedStyleProperty().get();

    public AquaButtonSkin(Button button) {
        super(button);

        registerChangeListener(button.disabledProperty(), "DISABLED");
        registerChangeListener(button.hoverProperty(), "HOVER");

        if (getSkinnable().isFocused()) {
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    setFocusBorder();
                }
            });
        } else {
            setDropShadow();
        }

        if (getSkinnable().isDefaultButton()) {
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    setDefaultButtonAnimation();
                }
            });
        }
        /**
         * if the button is a default button, it has to stop blinking when pressed
         */
        getSkinnable().setOnMousePressed(new EventHandler<Event>() {
            @Override public void handle(Event arg0) {
                if (getSkinnable().isFocused()) {
                    setFocusBorder();
                }
                if (getSkinnable().isDefaultButton()) {
                    setDefaultButtonAnimation();
                    getSkinnable().setStyle(armedButtonStyle);
                }
            }
        });

        /**
         * if the button is default, the button has to start blinking again, when mouse is released
         */
        getSkinnable().setOnMouseReleased(new EventHandler<Event>() {
            @Override public void handle(Event arg0) {
                if (getSkinnable().isDefaultButton()) {
                    setDefaultButtonAnimation();
                }
            }
        });

        final ChangeListener<Boolean> windowFocusChangedListener = new ChangeListener<Boolean>() {

            @Override public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (newValue != null) {
                    if (newValue.booleanValue() && getSkinnable().isDefaultButton()) {
                        if (defaultButtonTransition != null && defaultButtonTransition.getStatus() != Status.RUNNING) {
                            setDefaultButtonAnimation();
                        }
                    } else if (defaultButtonTransition != null && defaultButtonTransition.getStatus() == Status.RUNNING) {
                        setDefaultButtonAnimation();
                        // button has to look like a usual button again
                        getSkinnable().setStyle(usualButtonStyle);
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

        iconProperty().addListener(new ChangeListener<MacOSDefaultIcons>() {

            @Override public void changed(ObservableValue<? extends MacOSDefaultIcons> observable, MacOSDefaultIcons oldValue,
                    MacOSDefaultIcons newValue) {
                if (newValue != null && newValue != oldValue) {
                    if (newValue == MacOSDefaultIcons.SHARE) {
                        StackPane stack = new StackPane();
                        String iconBase = MacOSDefaultIcons.SHARE.getStyleName();
                        stack.getStyleClass().add("aquaicon");
                        Region svgIcon = new Region();
                        svgIcon.getStyleClass().add(iconBase + "-square");
                        stack.getChildren().add(svgIcon);
                        Region svgIcon2 = new Region();
                        svgIcon2.getStyleClass().add(iconBase + "-arrow");
                        stack.getChildren().add(svgIcon2);
                        getSkinnable().setGraphic(stack);
                    } else {
                        Region svgIcon = new Region();
                        svgIcon.getStyleClass().add("aqua-" + newValue.getStyleName());
                        svgIcon.getStyleClass().add("aquaicon");
                        getSkinnable().setGraphic(svgIcon);
                        getSkinnable().getStyleClass().add("button-" + newValue.getStyleName());
                    }
                    getSkinnable().requestLayout();
                }
            }
        });
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
        if (p == "HOVER") {
            if (getSkinnable().isDefaultButton() && getSkinnable().isPressed() && getSkinnable().isHover()) {
                getSkinnable().setStyle(armedButtonStyle);
            } else if (getSkinnable().isDefaultButton() && getSkinnable().isPressed() && !getSkinnable().isHover()) {
                getSkinnable().setStyle(usualButtonStyle);
            }
        }
        if (p == "FOCUSED") {
            if (getSkinnable().isFocused()) {
                setFocusBorder();
            } else if (!getSkinnable().isFocused() || getSkinnable().isDisable()) {
                setDropShadow();
            }
        }
        if (p == "DEFAULT_BUTTON") {
            setDefaultButtonAnimation();
        }
        if (p == "DISABLED") {
            if (getSkinnable().isDefaultButton()) {
                if (getSkinnable().isDisabled() && defaultButtonTransition != null && defaultButtonTransition.getStatus() != Status.RUNNING) {
                    defaultButtonTransition.stop();
                } else {
                    setDefaultButtonAnimation();
                }
            }
        }
    }

    private void setDefaultButtonAnimation() {
        if (!getSkinnable().isDisabled()) {
            if (defaultButtonTransition != null && defaultButtonTransition.getStatus() == Status.RUNNING) {
                defaultButtonTransition.stop();
            } else {
                final Duration duration = Duration.millis(500);
                defaultButtonTransition = new BindableTransition(duration);
                defaultButtonTransition.setCycleCount(Timeline.INDEFINITE);
                defaultButtonTransition.setAutoReverse(true);

                // The gradient
                final Color startColor1val = (Color) startColor1Property().get();
                final Color startColor2val = (Color) startColor2Property().get();
                final Color startColor3val = (Color) startColor3Property().get();
                final Color startColor4val = (Color) startColor4Property().get();

                final Color endColor1val = (Color) endColor1Property().get();
                final Color endColor2val = (Color) endColor2Property().get();
                final Color endColor3val = (Color) endColor3Property().get();
                final Color endColor4val = (Color) endColor4Property().get();

                defaultButtonTransition.fractionProperty().addListener(new ChangeListener<Number>() {

                    @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                        List<BackgroundFill> list = new ArrayList<>();

                        // the animated fill
                        Stop[] stops = new Stop[] { new Stop(0f, Color.color(
                                (endColor1val.getRed() - startColor1val.getRed()) * newValue.doubleValue() + startColor1val.getRed(),
                                (endColor1val.getGreen() - startColor1val.getGreen()) * newValue.doubleValue() + startColor1val.getGreen(),
                                (endColor1val.getBlue() - startColor1val.getBlue()) * newValue.doubleValue() + startColor1val.getBlue())), new Stop(0.5f, Color.color(
                                (endColor2val.getRed() - startColor2val.getRed()) * newValue.doubleValue() + startColor2val.getRed(),
                                (endColor2val.getGreen() - startColor2val.getGreen()) * newValue.doubleValue() + startColor2val.getGreen(),
                                (endColor2val.getBlue() - startColor2val.getBlue()) * newValue.doubleValue() + startColor2val.getBlue())), new Stop(0.51f, Color.color(
                                (endColor3val.getRed() - startColor3val.getRed()) * newValue.doubleValue() + startColor3val.getRed(),
                                (endColor3val.getGreen() - startColor3val.getGreen()) * newValue.doubleValue() + startColor3val.getGreen(),
                                (endColor3val.getBlue() - startColor3val.getBlue()) * newValue.doubleValue() + startColor3val.getBlue())), new Stop(1f, Color.color(
                                (endColor4val.getRed() - startColor4val.getRed()) * newValue.doubleValue() + startColor4val.getRed(),
                                (endColor4val.getGreen() - startColor4val.getGreen()) * newValue.doubleValue() + startColor4val.getGreen(),
                                (endColor4val.getBlue() - startColor4val.getBlue()) * newValue.doubleValue() + startColor4val.getBlue())) };

                        LinearGradient gradient = new LinearGradient(0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE, stops);
                        BackgroundFill backkgroudFill = new BackgroundFill(gradient, new CornerRadii(4.0), new Insets(0));
                        list.add(backkgroudFill);

                        getSkinnable().setBackground(new Background(list.get(0)));
                    }

                });

                defaultButtonTransition.play();
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
            styleables.add(AquaCssProperties.getIconMetaData());
            styleables.add(AquaCssProperties.getInnerFocusColorMetaData());
            styleables.add(AquaCssProperties.getOuterFocusColorMetaData());
            styleables.add(getStartColor1MetaData());
            styleables.add(getStartColor2MetaData());
            styleables.add(getStartColor3MetaData());
            styleables.add(getStartColor4MetaData());
            styleables.add(getEndColor1MetaData());
            styleables.add(getEndColor2MetaData());
            styleables.add(getEndColor3MetaData());
            styleables.add(getEndColor4MetaData());
            styleables.add(getArmedStyleMetaData());
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
     * Adding the possibility to set an Icon to a Button via CSS-Property *
     **********************************************************************************/

    private ObjectProperty<MacOSDefaultIcons> icon;

    public final ObjectProperty<MacOSDefaultIcons> iconProperty() {
        if (icon == null) {
            icon = AquaCssProperties.createProperty(this, "icon", AquaCssProperties.getIconMetaData());
        }
        return icon;
    }

    public void setIcon(MacOSDefaultIcons icon) {
        iconProperty().setValue(icon);
    }

    public MacOSDefaultIcons getIcon() {
        return icon == null ? null : icon.getValue();
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

    /***********************************************************************************
     * Adding the possibility to set the colors to a default Button animation via CSS-Property *
     **********************************************************************************/

    private ObjectProperty<Paint> startColor1;

    public final ObjectProperty<Paint> startColor1Property() {
        if (startColor1 == null) {
            startColor1 = AquaCssProperties.createProperty(this, "startColor1", getStartColor1MetaData());
        }
        return startColor1;
    }

    public void setStartColor1(Paint startColor1) {
        startColor1Property().setValue(startColor1);
    }

    public Paint getStartColor1() {
        return startColor1 == null ? null : startColor1.getValue();
    }

    private static CssMetaData<Control, Paint> startColor1MetaData;

    public static CssMetaData<Control, Paint> getStartColor1MetaData() {
        if (startColor1MetaData == null) {
            startColor1MetaData = new CssMetaData<Control, Paint>("-fx-aqua-start-color-1", PaintConverter.getInstance(), Color.BLUE) {
                @Override public boolean isSettable(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<Paint> getStyleableProperty(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return (StyleableProperty<Paint>) ((AquaButtonSkin) skin).startColor1Property();
                    }
                    return null;
                }
            };
        }
        return startColor1MetaData;
    }

    private ObjectProperty<Paint> startColor2;

    public final ObjectProperty<Paint> startColor2Property() {
        if (startColor2 == null) {
            startColor2 = AquaCssProperties.createProperty(this, "startColor2", getStartColor2MetaData());
        }
        return startColor2;
    }

    public void setStartColor2(Paint startColor2) {
        startColor2Property().setValue(startColor2);
    }

    public Paint getStartColor2() {
        return startColor2 == null ? null : startColor2.getValue();
    }

    private static CssMetaData<Control, Paint> startColor2MetaData;

    public static CssMetaData<Control, Paint> getStartColor2MetaData() {
        if (startColor2MetaData == null) {
            startColor2MetaData = new CssMetaData<Control, Paint>("-fx-aqua-start-color-2", PaintConverter.getInstance(), Color.BLUE) {
                @Override public boolean isSettable(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<Paint> getStyleableProperty(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return (StyleableProperty<Paint>) ((AquaButtonSkin) skin).startColor2Property();
                    }
                    return null;
                }
            };
        }
        return startColor2MetaData;
    }

    private ObjectProperty<Paint> startColor3;

    public final ObjectProperty<Paint> startColor3Property() {
        if (startColor3 == null) {
            startColor3 = AquaCssProperties.createProperty(this, "startColor3", getStartColor3MetaData());
        }
        return startColor3;
    }

    public void setStartColor3(Paint startColor3) {
        startColor3Property().setValue(startColor3);
    }

    public Paint getStartColor3() {
        return startColor3 == null ? null : startColor3.getValue();
    }

    private static CssMetaData<Control, Paint> startColor3MetaData;

    public static CssMetaData<Control, Paint> getStartColor3MetaData() {
        if (startColor3MetaData == null) {
            startColor3MetaData = new CssMetaData<Control, Paint>("-fx-aqua-start-color-3", PaintConverter.getInstance(), Color.BLUE) {
                @Override public boolean isSettable(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<Paint> getStyleableProperty(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return (StyleableProperty<Paint>) ((AquaButtonSkin) skin).startColor3Property();
                    }
                    return null;
                }
            };
        }
        return startColor3MetaData;
    }

    private ObjectProperty<Paint> startColor4;

    public final ObjectProperty<Paint> startColor4Property() {
        if (startColor4 == null) {
            startColor4 = AquaCssProperties.createProperty(this, "startColor4", getStartColor4MetaData());
        }
        return startColor4;
    }

    public void setStartColor4(Paint startColor4) {
        startColor4Property().setValue(startColor4);
    }

    public Paint getStartColor4() {
        return startColor4 == null ? null : startColor4.getValue();
    }

    private static CssMetaData<Control, Paint> startColor4MetaData;

    public static CssMetaData<Control, Paint> getStartColor4MetaData() {
        if (startColor4MetaData == null) {
            startColor4MetaData = new CssMetaData<Control, Paint>("-fx-aqua-start-color-4", PaintConverter.getInstance(), Color.BLUE) {
                @Override public boolean isSettable(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<Paint> getStyleableProperty(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return (StyleableProperty<Paint>) ((AquaButtonSkin) skin).startColor4Property();
                    }
                    return null;
                }
            };
        }
        return startColor4MetaData;
    }

    private ObjectProperty<Paint> endColor1;

    public final ObjectProperty<Paint> endColor1Property() {
        if (endColor1 == null) {
            endColor1 = AquaCssProperties.createProperty(this, "endColor1", getEndColor1MetaData());
        }
        return endColor1;
    }

    public void setEndColor1(Paint endColor1) {
        endColor1Property().setValue(endColor1);
    }

    public Paint getEndColor1() {
        return endColor1 == null ? null : endColor1.getValue();
    }

    private static CssMetaData<Control, Paint> endColor1MetaData;

    public static CssMetaData<Control, Paint> getEndColor1MetaData() {
        if (endColor1MetaData == null) {
            endColor1MetaData = new CssMetaData<Control, Paint>("-fx-aqua-end-color-1", PaintConverter.getInstance(), Color.BLUE) {
                @Override public boolean isSettable(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<Paint> getStyleableProperty(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return (StyleableProperty<Paint>) ((AquaButtonSkin) skin).endColor1Property();
                    }
                    return null;
                }
            };
        }
        return endColor1MetaData;
    }

    private ObjectProperty<Paint> endColor2;

    public final ObjectProperty<Paint> endColor2Property() {
        if (endColor2 == null) {
            endColor2 = AquaCssProperties.createProperty(this, "endColor2", getEndColor2MetaData());
        }
        return endColor2;
    }

    public void setEndColor2(Paint endColor2) {
        endColor2Property().setValue(endColor2);
    }

    public Paint getEndColor2() {
        return endColor2 == null ? null : endColor2.getValue();
    }

    private static CssMetaData<Control, Paint> endColor2MetaData;

    public static CssMetaData<Control, Paint> getEndColor2MetaData() {
        if (endColor2MetaData == null) {
            endColor2MetaData = new CssMetaData<Control, Paint>("-fx-aqua-end-color-2", PaintConverter.getInstance(), Color.BLUE) {
                @Override public boolean isSettable(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<Paint> getStyleableProperty(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return (StyleableProperty<Paint>) ((AquaButtonSkin) skin).endColor2Property();
                    }
                    return null;
                }
            };
        }
        return endColor2MetaData;
    }

    private ObjectProperty<Paint> endColor3;

    public final ObjectProperty<Paint> endColor3Property() {
        if (endColor3 == null) {
            endColor3 = AquaCssProperties.createProperty(this, "endColor3", getEndColor3MetaData());
        }
        return endColor3;
    }

    public void setEndColor3(Paint endColor3) {
        endColor3Property().setValue(endColor3);
    }

    public Paint getEndColor3() {
        return endColor3 == null ? null : endColor3.getValue();
    }

    private static CssMetaData<Control, Paint> endColor3MetaData;

    public static CssMetaData<Control, Paint> getEndColor3MetaData() {
        if (endColor3MetaData == null) {
            endColor3MetaData = new CssMetaData<Control, Paint>("-fx-aqua-end-color-3", PaintConverter.getInstance(), Color.BLUE) {
                @Override public boolean isSettable(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<Paint> getStyleableProperty(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return (StyleableProperty<Paint>) ((AquaButtonSkin) skin).endColor3Property();
                    }
                    return null;
                }
            };
        }
        return endColor3MetaData;
    }

    private ObjectProperty<Paint> endColor4;

    public final ObjectProperty<Paint> endColor4Property() {
        if (endColor4 == null) {
            endColor4 = AquaCssProperties.createProperty(this, "endColor4", getEndColor4MetaData());
        }
        return endColor4;
    }

    public void setEndColor4(Paint endColor4) {
        endColor4Property().setValue(endColor4);
    }

    public Paint getEndColor4() {
        return endColor4 == null ? null : endColor4.getValue();
    }

    private static CssMetaData<Control, Paint> endColor4MetaData;

    public static CssMetaData<Control, Paint> getEndColor4MetaData() {
        if (endColor4MetaData == null) {
            endColor4MetaData = new CssMetaData<Control, Paint>("-fx-aqua-end-color-4", PaintConverter.getInstance(), Color.BLUE) {
                @Override public boolean isSettable(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<Paint> getStyleableProperty(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return (StyleableProperty<Paint>) ((AquaButtonSkin) skin).endColor4Property();
                    }
                    return null;
                }
            };
        }
        return endColor4MetaData;
    }

    private ObjectProperty<String> armedStyle;

    public final ObjectProperty<String> armedStyleProperty() {
        if (armedStyle == null) {
            armedStyle = AquaCssProperties.createProperty(this, "armedStyle", getArmedStyleMetaData());
        }
        return armedStyle;
    }

    public void setArmedStyle(String armedStyle) {
        armedStyleProperty().setValue(armedStyle);
    }

    public String getArmedStyle() {
        return armedStyle == null ? null : armedStyle.getValue();
    }

    private static CssMetaData<Control, String> armedStyleMetaData;

    public static CssMetaData<Control, String> getArmedStyleMetaData() {
        if (armedStyleMetaData == null) {
            armedStyleMetaData = new CssMetaData<Control, String>("-fx-aqua-armed-style", StringConverter.getInstance()) {
                @Override public boolean isSettable(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<String> getStyleableProperty(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return (StyleableProperty<String>) ((AquaButtonSkin) skin).armedStyleProperty();
                    }
                    return null;
                }
            };
        }
        return armedStyleMetaData;
    }
}
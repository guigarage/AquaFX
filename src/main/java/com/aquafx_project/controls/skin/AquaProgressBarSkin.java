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

import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

import com.aquafx_project.AquaFx;
import com.aquafx_project.controls.skin.css.AquaCssProperties;
import com.aquafx_project.util.BindableTransition;
import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.css.converters.StringConverter;
import com.sun.javafx.scene.control.skin.ProgressBarSkin;

public class AquaProgressBarSkin extends ProgressBarSkin implements AquaSkin {

    private BindableTransition indeterminateProgressTransition;
    private BindableTransition determinateProgressTransition;
 
    private final Background originalBackground = getSkinnable().getBackground();

    public AquaProgressBarSkin(ProgressBar progressBar) {
        super(progressBar);
        if (progressBar.isIndeterminate()) {
            setIndeterminateProgressAnimation();
        } else {
            Platform.runLater(new Runnable() {
                
                @Override public void run() {
                    setDeterminateProgressAnimation();                    
                }
            });
        }
        registerChangeListener(progressBar.disabledProperty(), "DISABLED");
        registerChangeListener(progressBar.indeterminateProperty(), "INDETERMINATE");
    }

    @Override protected void handleControlPropertyChanged(String propertyReference) {
        super.handleControlPropertyChanged(propertyReference);
        if (propertyReference == "DISABLED") {
            if (getSkinnable().isIndeterminate()) {
                if (getSkinnable().isDisabled() && indeterminateProgressTransition != null && indeterminateProgressTransition.getStatus() == Status.RUNNING) {
                    indeterminateProgressTransition.stop();
                } else {
                    setIndeterminateProgressAnimation();
                }
            } else {
                if (getSkinnable().isDisabled() && determinateProgressTransition != null && determinateProgressTransition.getStatus() == Status.RUNNING) {
                    determinateProgressTransition.stop();
                } else {
                    setDeterminateProgressAnimation();
                }
            }
        }
        if (propertyReference == "INDETERMINATE") {
            if (getSkinnable().isIndeterminate()) {
                if (determinateProgressTransition != null && determinateProgressTransition.getStatus() == Status.RUNNING) {
                    determinateProgressTransition.stop();
                }
                ((StackPane) getSkinnable().lookup(".bar")).setBackground(null);
                setIndeterminateProgressAnimation();
            } else {
                if (indeterminateProgressTransition != null && indeterminateProgressTransition.getStatus() == Status.RUNNING) {
                    indeterminateProgressTransition.stop();
                }
                getSkinnable().setBackground(originalBackground);
                setDeterminateProgressAnimation();
            }
        }
    }

    private void setIndeterminateProgressAnimation() {
        if (!getSkinnable().isDisabled()) {
            if (indeterminateProgressTransition != null && indeterminateProgressTransition.getStatus() == Status.RUNNING) {
                indeterminateProgressTransition.stop();
            } else {
                final Duration duration = Duration.millis(2000);
                indeterminateProgressTransition = new BindableTransition(duration);
                indeterminateProgressTransition.setCycleCount(Timeline.INDEFINITE);
                indeterminateProgressTransition.setAutoReverse(false);
                indeterminateProgressTransition.setInterpolator(Interpolator.LINEAR);
                indeterminateProgressTransition.fractionProperty().addListener(new ChangeListener<Number>() {

                    @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                        double startX = newValue.doubleValue() * 0.82;
                        double endX = newValue.doubleValue() * 0.82 + 0.05d;

                        List<BackgroundFill> list = new ArrayList<>();

                        // the animated fill

                        Stop[] stops0 = new Stop[] { new Stop(0.0f, Color.rgb(176, 176, 176)), new Stop(1f, Color.rgb(207, 207,
                                207)) };
                        LinearGradient gradient0 = new LinearGradient(0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE, stops0);
                        BackgroundFill backkgroudFill0 = new BackgroundFill(gradient0, new CornerRadii(2.0), new Insets(0));
                        list.add(backkgroudFill0);

                        Stop[] stops1 = new Stop[] { new Stop(0.5f, (Color)indeterminateColorProperty().get()), new Stop(0.5f, Color.rgb(236, 236,
                                236)) };
                        LinearGradient gradient1 = new LinearGradient(startX, 0.45, endX, 0.0, true, CycleMethod.REFLECT, stops1);
                        BackgroundFill backkgroudFill1 = new BackgroundFill(gradient1, new CornerRadii(2.0), new Insets(1));
                        list.add(backkgroudFill1);

                        Stop[] stops2 = new Stop[] { new Stop(0.05f, Color.rgb(255, 255, 255, 0.7)), new Stop(0.05f, Color.rgb(
                                255, 255, 255, 0.55)), new Stop(0.5f, Color.rgb(255, 255, 255, 0.1)), new Stop(0.5f, Color.rgb(
                                255, 255, 255, 0.0)), new Stop(0.6f, Color.rgb(255, 255, 255, 0.0)), new Stop(0.85f, Color.rgb(
                                255, 255, 255, 0.4)), new Stop(1f, Color.rgb(245, 245, 245, 0.7)) };
                        LinearGradient gradient2 = new LinearGradient(0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE, stops2);
                        BackgroundFill backkgroudFill2 = new BackgroundFill(gradient2, new CornerRadii(2.0), new Insets(0));
                        list.add(backkgroudFill2);

                        getSkinnable().setBackground(new Background(list, null));
                    }
                });
                indeterminateProgressTransition.play();
            }
        } else {
            List<BackgroundFill> list = new ArrayList<>();

            Stop[] stops0 = new Stop[] { new Stop(0.0f, Color.rgb(176, 176, 176)), new Stop(1f, Color.rgb(207, 207, 207)) };
            LinearGradient gradient0 = new LinearGradient(0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE, stops0);
            BackgroundFill backkgroudFill0 = new BackgroundFill(gradient0, new CornerRadii(2.0), new Insets(0));
            list.add(backkgroudFill0);

            Stop[] stops1 = new Stop[] { new Stop(0.5f, Color.rgb(84, 169, 239)), new Stop(0.5f, Color.rgb(236, 236, 236)) };
            LinearGradient gradient1 = new LinearGradient(0.0, 0.45, 0.05, 0.0, true, CycleMethod.REFLECT, stops1);
            BackgroundFill backkgroudFill1 = new BackgroundFill(gradient1, new CornerRadii(2.0), new Insets(1));
            list.add(backkgroudFill1);

            Stop[] stops2 = new Stop[] { new Stop(0.05f, Color.rgb(255, 255, 255, 0.7)), new Stop(0.05f, Color.rgb(255, 255, 255,
                    0.55)), new Stop(0.5f, Color.rgb(255, 255, 255, 0.1)), new Stop(0.5f, Color.rgb(255, 255, 255, 0.0)), new Stop(0.6f, Color.rgb(
                    255, 255, 255, 0.0)), new Stop(0.85f, Color.rgb(255, 255, 255, 0.4)), new Stop(1f, Color.rgb(245, 245, 245,
                    0.7)) };
            LinearGradient gradient2 = new LinearGradient(0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE, stops2);
            BackgroundFill backkgroudFill2 = new BackgroundFill(gradient2, new CornerRadii(2.0), new Insets(0));
            list.add(backkgroudFill2);

            getSkinnable().setBackground(new Background(list, null));
        }
    }

    private void setDeterminateProgressAnimation() {
        if (!getSkinnable().isDisabled()) {
            if (determinateProgressTransition != null && determinateProgressTransition.getStatus() == Status.RUNNING) {
                determinateProgressTransition.stop();
            } else {
                final Duration duration = Duration.millis(1000);
                determinateProgressTransition = new BindableTransition(duration);
                determinateProgressTransition.setCycleCount(Timeline.INDEFINITE);
                determinateProgressTransition.setAutoReverse(false);
                determinateProgressTransition.setInterpolator(Interpolator.LINEAR);
                determinateProgressTransition.fractionProperty().addListener(new ChangeListener<Number>() {

                    @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        StackPane bar = ((StackPane) getSkinnable().lookup(".bar"));
                        int start = 1 - (int) ((38 * bar.getHeight() / 20) * newValue.doubleValue());
                        
                        String imageString= determinateImageProperty().get();
                        if(imageString == null){
                            imageString= "progress.png";
                        }
                        Image image = new Image(AquaFx.class.getResource("controls/skin/"+imageString).toExternalForm());
                        
                        ImagePattern pattern = new ImagePattern(image, start, 0, (38 * bar.getHeight() / 20), bar.getHeight(), false);

                        BackgroundFill backkgroudFill = new BackgroundFill(pattern, new CornerRadii(0), new Insets(0));

                        bar.setBackground(new Background(backkgroudFill));
                    }
                });
                determinateProgressTransition.play();
            }
        } else {
            StackPane bar = ((StackPane) getSkinnable().lookup(".bar"));
            
            String imageString= determinateImageProperty().get();
            Image image = new Image(AquaFx.class.getResource("controls/skin/"+imageString).toExternalForm());
            ImagePattern pattern = new ImagePattern(image, 0, 0, (38 * bar.getHeight() / 20), bar.getHeight(), false);
            BackgroundFill backkgroudFill = new BackgroundFill(pattern, new CornerRadii(0), new Insets(0));
            bar.setBackground(new Background(backkgroudFill));
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
            styleables.add(getIndeterminateColorMetaData());
            styleables.add(getDeterminateImageMetaData());
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

    private ObjectProperty<Paint> indeterminateColor;

    public final ObjectProperty<Paint> indeterminateColorProperty() {
        if (indeterminateColor == null) {
            indeterminateColor = AquaCssProperties.createProperty(this, "indeterminateColor", getIndeterminateColorMetaData());
        }
        return indeterminateColor;
    }

    public void setIndeterminateColor(Paint indeterminateColor) {
        indeterminateColorProperty().setValue(indeterminateColor);
    }

    public Paint getIndeterminateColor() {
        return indeterminateColor == null ? null : indeterminateColor.getValue();
    }

    private static CssMetaData<Control, Paint> indeterminateColorMetaData;

    public static CssMetaData<Control, Paint> getIndeterminateColorMetaData() {
        if (indeterminateColorMetaData == null) {
            indeterminateColorMetaData = new CssMetaData<Control, Paint>("-fx-aqua-indeterminate-color", PaintConverter.getInstance(), Color.BLUE) {
                @Override public boolean isSettable(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaProgressBarSkin) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<Paint> getStyleableProperty(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaProgressBarSkin) {
                        return (StyleableProperty<Paint>) ((AquaProgressBarSkin) skin).indeterminateColorProperty();
                    }
                    return null;
                }
            };
        }
        return indeterminateColorMetaData;
    }

    
    
    private ObjectProperty<String> determinateImage;

    public final ObjectProperty<String> determinateImageProperty() {
        if (determinateImage == null) {
            determinateImage = AquaCssProperties.createProperty(this, "determinateImage", getDeterminateImageMetaData());
        }
        return determinateImage;
    }

    public void setDeterminateImage(String determinateImage) {
        determinateImageProperty().setValue(determinateImage);
    }

    public String getDeterminateImage() {
        return determinateImage == null ? null : determinateImage.getValue();
    }

    private static CssMetaData<Control, String> determinateImageMetaData;

    public static CssMetaData<Control, String> getDeterminateImageMetaData() {
        if (determinateImageMetaData == null) {
            determinateImageMetaData = new CssMetaData<Control, String>("-fx-aqua-determinate-image", StringConverter.getInstance(), "fire.png") {
                @Override public boolean isSettable(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaProgressBarSkin) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<String> getStyleableProperty(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaProgressBarSkin) {
                        return (StyleableProperty<String>) ((AquaProgressBarSkin) skin).determinateImageProperty();
                    }
                    return null;
                }
            };
        }
        return determinateImageMetaData;
    }
}

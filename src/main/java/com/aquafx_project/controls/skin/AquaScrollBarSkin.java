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

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;

import com.aquafx_project.util.BindableTransition;
import com.sun.javafx.scene.control.skin.ScrollBarSkin;


public class AquaScrollBarSkin extends ScrollBarSkin implements AquaSkin{

    private BindableTransition growScrollbarTransition;
    private FadeTransition fadeIn;
    private SequentialTransition fadeOutSeq;
    private boolean alreadyFaded = false;
    private boolean alreadyHovered = false;
    private boolean wide = false;
    private boolean fadeable = false;

    public AquaScrollBarSkin(ScrollBar scrollBar) {
        super(scrollBar);

        if (getNode().getParent() instanceof ScrollPane) {
            fadeable = true;
        }
        scrollBar.setVisible(!fadeable);
        registerChangeListener(scrollBar.hoverProperty(), "HOVER");
        registerChangeListener(scrollBar.valueProperty(), "VALUE");
        registerChangeListener(scrollBar.visibleProperty(), "VISIBLE");
    }

    @Override protected void handleControlPropertyChanged(String p) {
        super.handleControlPropertyChanged(p);
        if (p == "HOVER") {
            setGrowScrollbarAnimation();
            if (getSkinnable().isHover() && fadeable) {
                fadeOutSeq.jumpTo(Duration.millis(0));
                fadeOutSeq.stop();
            } else if (fadeable && alreadyFaded) {
                fadeOutSeq.playFromStart();
            }
        }
        if (p == "VALUE") {
            /*
             * when value changes, scrolling is activated and the scrollbar has to fade in for some
             * time and fade out again, when there is no further interaction
             */
            if (fadeable && fadeOutSeq != null && fadeOutSeq.getCurrentRate() != 0.0d) {
                fadeOutSeq.playFromStart();
            } else if (fadeable) {
                fading();
            }
        }
        if (p == "VISIBLE") {
            if (fadeable && getSkinnable().isVisible()) {
                fading();
            }
        }
    }

    private void fading() {
        if (fadeIn == null) {
            
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(300));
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setOnFinished( new EventHandler<ActionEvent>() {

                                @Override public void handle(ActionEvent event) {
                                    alreadyFaded = false;
                                    alreadyHovered = false;
                                    wide = false;
                                    getSkinnable().setStyle(null);
                                    for (Node child : getChildren()) {
                                        child.setStyle(null);
                                    }
                                }
                            });
            fadeOutSeq = new SequentialTransition();
            fadeOutSeq.setDelay(Duration.millis(2000));
            fadeOutSeq.getChildren().add(fadeTransition);
            fadeOutSeq.setNode(getSkinnable());
            
            fadeIn = new FadeTransition(Duration.millis(100));
            fadeIn.setNode(getSkinnable());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.setOnFinished( new EventHandler<ActionEvent>() {

                @Override public void handle(ActionEvent event) {
                    alreadyFaded = true;
                    fadeOutSeq.playFromStart();
                }
            });
        }
        if (fadeIn.getCurrentRate() == 0.0d && !alreadyFaded) {
            fadeIn.play();
        }

    }

    private void setGrowScrollbarAnimation() {

        if (getSkinnable().isHover() && !alreadyHovered && alreadyFaded) {

            if (growScrollbarTransition == null) {
                growScrollbarTransition = new BindableTransition(Duration.millis(200));
                growScrollbarTransition.setCycleCount(1);

                final double startWidth = 4;
                final double endWidth = 6;

                growScrollbarTransition.fractionProperty().addListener(new ChangeListener<Number>() {

                    @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                        for (Node child : getChildren()) {
                            if (child.getStyleClass().get(0).equals("increment-button") || child.getStyleClass().get(0).equals(
                                    "decrement-button")) {
                                if (getSkinnable().getOrientation() == Orientation.VERTICAL) {
                                    child.setStyle("-fx-padding: 0.0em " + ((endWidth - startWidth) * newValue.doubleValue() + startWidth) + "pt 0.0em 0.0em;}");
                                } else if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
                                    child.setStyle("-fx-padding: " + ((endWidth - startWidth) * newValue.doubleValue() + startWidth) + "pt  0.0em 0.0em 0.0em;}");
                                }
                            }
                        }
                    }
                });
            }
            growScrollbarTransition.play();
            alreadyHovered = true;
        } else if (!wide && !getSkinnable().isHover() && alreadyHovered) {
            /*
             * when scrollbar is shown and we hover out, it still should be shown for the time of
             * the fadeOut- transition
             */
            if (growScrollbarTransition != null) {
                growScrollbarTransition.stop();
            }
            if (getSkinnable().getOrientation() == Orientation.VERTICAL) {
                getSkinnable().setStyle(" -fx-padding: -1.0 0.0 -1.0 0.0;");
            } else {
                getSkinnable().setStyle(" -fx-padding: 0.0 0.0 0.0 0.0;");
            }
            for (Node child : getChildren()) {
                if (child.getStyleClass().get(0).equals("track")) {
                    if (getSkinnable().getOrientation() == Orientation.VERTICAL) {
                        child.setStyle("-fx-background-color: linear-gradient(rgb(238.0, 238.0, 238.0, 0.8) 0.0%, rgb(255.0, 255.0, 255.0, 0.8) 100.0%);" + "-fx-border-width: 0.0 0.0 0.0 1.0;" + "-fx-border-insets: 0.0 0.0 0.0 -1.0;" + "-fx-border-color: rgb(198.0, 198.0, 198.0);");
                    } else if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
                        child.setStyle("-fx-background-color: linear-gradient(rgb(238.0, 238.0, 238.0, 0.8) 0.0%, rgb(255.0, 255.0, 255.0, 0.8) 100.0%);" + "-fx-border-width: 1.0 0.0 0.0 0.0;" + "-fx-border-insets: -1.0 0.0 0.0 0.0;" + "-fx-border-color: rgb(198.0, 198.0, 198.0);");
                    }
                } else if (child.getStyleClass().get(0).equals("thumb")) {
                    if (getSkinnable().getOrientation() == Orientation.VERTICAL) {
                        child.setStyle("-fx-background-radius: 6.0;" + "-fx-background-insets: 0.0 2.0 0.0 2.0;");
                    } else if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
                        child.setStyle("-fx-background-radius: 6.0;" + "-fx-background-insets: 2.0 0.0 2.0 0.0;");
                    }
                } else if (child.getStyleClass().get(0).equals("increment-button") || child.getStyleClass().get(0).equals(
                        "decrement-button")) {
                    if (getSkinnable().getOrientation() == Orientation.VERTICAL) {
                        child.setStyle("-fx-padding: 0.0em 6.0pt 0.0em 0.0em");
                    } else if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
                        child.setStyle("-fx-padding: 6.0pt 0.0em 0.0em 0.0em");
                    }
                }
            }
            wide = true;
        }
    }
}

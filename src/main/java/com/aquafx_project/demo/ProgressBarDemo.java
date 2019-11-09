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

package com.aquafx_project.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.aquafx_project.AquaFx;

public class ProgressBarDemo extends Application {
    final Button aqua = new Button("Aqua");
    final Button fire = new Button("Fire");
    final Button earth = new Button("Earth");
    final Button wind = new Button("Wind");

    @Override public void start(Stage stage) {
        stage.setTitle("Progress");
        VBox root = new VBox();
        root.setSpacing(50);
        root.setPadding(new Insets(60));
        final Scene scene = new Scene(root);
        root.setStyle("-fx-background-color: white;");

        HBox switcher = new HBox();
        switcher.setSpacing(5);

        aqua.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                AquaFx.style();
                aqua.setDisable(true);
                fire.setDisable(false);
                earth.setDisable(false);
                wind.setDisable(false);
            }
        });
        switcher.getChildren().add(aqua);

        fire.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                AquaFx.setFireStyle();
                aqua.setDisable(false);
                fire.setDisable(true);
                earth.setDisable(false);
                wind.setDisable(false);
            }
        });
        switcher.getChildren().add(fire);

        earth.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                AquaFx.setEarthStyle();
                aqua.setDisable(false);
                fire.setDisable(false);
                earth.setDisable(true);
                wind.setDisable(false);
            }
        });
        switcher.getChildren().add(earth);

        wind.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                AquaFx.setAirStyle();
                aqua.setDisable(false);
                fire.setDisable(false);
                earth.setDisable(false);
                wind.setDisable(true);
            }
        });
        switcher.getChildren().add(wind);
        root.getChildren().add(switcher);

        final ProgressBar p = new ProgressBar(0.6);
        root.getChildren().add(p);

        Button b = new Button("Disable");
        b.setOnAction(new EventHandler<ActionEvent>() {

            @Override public void handle(ActionEvent event) {
                if (p.isDisabled()) {
                    p.setDisable(false);
                } else {
                    p.setDisable(true);
                }
            }
        });
        // root.getChildren().add(b);

        Button bI = new Button("Button");
        bI.setOnAction(new EventHandler<ActionEvent>() {

            @Override public void handle(ActionEvent event) {
                if (p.isIndeterminate()) {
                    p.setProgress(0.6);
                } else {
                    p.setProgress(-1);
                }
            }
        });

        root.getChildren().add(bI);

        AquaFx.style();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
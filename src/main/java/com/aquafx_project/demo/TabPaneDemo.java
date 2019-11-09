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

package com.aquafx_project.demo;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.aquafx_project.AquaFx;
import com.aquafx_project.controls.skin.styles.ButtonType;

public class TabPaneDemo extends Application {

    @Override public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 210);
        AquaFx.style();
        stage.setScene(scene);
        stage.setTitle("TabPaneDemo");

        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.TOP);
        tabPane.setPadding(new Insets(15));

        // Create Tabs
        Tab tabD = new Tab();
        tabD.setText("tab 1");

        VBox box = new VBox();
        box.setSpacing(15);
        box.setPadding(new Insets(15));
        Button b1 = new Button("regular");
        box.getChildren().add(b1);
        Button b3 = new Button("round rect");
        AquaFx.createButtonStyler().setType(ButtonType.ROUND_RECT).style(b3);
        box.getChildren().add(b3);

        tabD.setContent(box);

        tabPane.getTabs().add(tabD);

        Tab tabE = new Tab();
        tabE.setText("tab 2");
        tabPane.getTabs().add(tabE);

        Tab tabF = new Tab();
        tabF.setText("tab 3");
        tabPane.getTabs().add(tabF);

        scene.setRoot(tabPane);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.aquafx_project.AquaFx;

public class ShutdownDialogDemo extends Application {

    public static void main(String[] args) {
        ShutdownDialogDemo.launch();
    }

    @Override public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();

        Image image = new Image(AquaFx.class.getResource("demo/images/bild.png").toExternalForm());
        ImageView iv = new ImageView(image);

        BorderPane.setMargin(iv, new Insets(18, 0, 0, 18));
        borderPane.setLeft(iv);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20, 20, 0, 20));
        vbox.setSpacing(10);
        Label title = new Label("M\u00F6chten Sie den Computer jetzt ausschalten?");
        title.setStyle("-fx-font-weight: bold");
        vbox.getChildren().add(title);

        Label info = new Label("Wenn Sie keine Auswahl treffen, wird der Computer in 43 Sekunden automatisch ausgeschaltet.");
        info.setStyle("-fx-font-size : 0.8em");
        info.setWrapText(true);
        VBox.setMargin(info, new Insets(14, 0, 0, 0));
        vbox.getChildren().add(info);

        CheckBox checkBox = new CheckBox("Beim n\u00E4chsten Anmelden alle Fenster wieder \u00F6ffnen");
        checkBox.setAllowIndeterminate(false);
        vbox.getChildren().add(checkBox);

        borderPane.setCenter(vbox);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setPadding(new Insets(19));
        hbox.setSpacing(12);

        Button cancel = new Button();
        cancel.setText("Abbrechen");
        cancel.setCancelButton(true);
        hbox.getChildren().add(cancel);

        Button off = new Button();
        off.setText("Ausschalten");
        off.setDefaultButton(true);
        hbox.getChildren().add(off);

        borderPane.setBottom(hbox);

        Scene myScene = new Scene(borderPane, 470, 172);
        AquaFx.style();
        stage.setResizable(false);
        stage.setScene(myScene);
        stage.show();
    }

}

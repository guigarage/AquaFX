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
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.aquafx_project.AquaFx;
import com.aquafx_project.controls.skin.styles.ControlSizeVariant;
import com.aquafx_project.controls.skin.styles.TabPaneType;

public class TinyTabPaneDemo extends Application {

    @Override public void start(Stage stage) {
        stage.setTitle("Verknüpfung");
        AquaFx.styleStage(stage, StageStyle.UTILITY);
        Pane root = new Pane();
        Scene scene = new Scene(root);

        /*
         * Tabpane section
         */
        TabPane tabPane = new TabPane();
        tabPane.setPrefWidth(218);
        AquaFx.createTabPaneStyler().setType(TabPaneType.SMALL_ICON_BUTTONS).style(tabPane);

        Tab tab1 = new Tab();
        Image image = new Image(AquaFx.class.getResource("demo/images/pages/pages.png").toExternalForm());
        ImageView pages = new ImageView(image);
        pages.setPreserveRatio(true);
        pages.setFitHeight(16);
        tab1.setGraphic(pages);
        Label label = new Label("Dokument...");
        label.setPadding(new Insets(15));
        tab1.setContent(label);
        tabPane.getTabs().add(tab1);

        Tab tab2 = new Tab();
        Image image2 = new Image(AquaFx.class.getResource("demo/images/pages/layout.png").toExternalForm());
        ImageView layout = new ImageView(image2);
        layout.setPreserveRatio(true);
        layout.setFitHeight(16);
        tab2.setGraphic(layout);
        Label label2 = new Label("layout");
        label2.setPadding(new Insets(15));
        tab2.setContent(label2);
        tabPane.getTabs().add(tab2);

        Tab tab3 = new Tab();
        Image image3 = new Image(AquaFx.class.getResource("demo/images/pages/umbruch.png").toExternalForm());
        ImageView umbruch = new ImageView(image3);
        umbruch.setPreserveRatio(true);
        umbruch.setFitHeight(16);
        tab3.setGraphic(umbruch);
        Label label3 = new Label("Zeilenumbruch...");
        label3.setPadding(new Insets(15));
        tab3.setContent(label3);
        tabPane.getTabs().add(tab3);

        Tab tab4 = new Tab();
        Image image4 = new Image(AquaFx.class.getResource("demo/images/pages/text.png").toExternalForm());
        ImageView text = new ImageView(image4);
        text.setPreserveRatio(true);
        text.setFitHeight(16);
        tab4.setGraphic(text);
        Label label4 = new Label("Zeilenumbruch...");
        label4.setPadding(new Insets(15));
        tab4.setContent(label4);
        tabPane.getTabs().add(tab4);

        Tab tab5 = new Tab();
        Image image5 = new Image(AquaFx.class.getResource("demo/images/pages/grafik.png").toExternalForm());
        ImageView grafik = new ImageView(image5);
        grafik.setPreserveRatio(true);
        grafik.setFitHeight(16);
        tab5.setGraphic(grafik);
        Label label5 = new Label("Zeilenumbruch...");
        label5.setPadding(new Insets(15));
        tab5.setContent(label5);
        tabPane.getTabs().add(tab5);

        Tab tab6 = new Tab();
        Image image6 = new Image(AquaFx.class.getResource("demo/images/pages/lineal.png").toExternalForm());
        ImageView lineal = new ImageView(image6);
        lineal.setPreserveRatio(true);
        lineal.setFitHeight(16);
        tab6.setGraphic(lineal);
        Label label6 = new Label("Lienal lineal lineal lineal lineal...");
        label6.setPadding(new Insets(15));
        tab6.setContent(label6);
        tabPane.getTabs().add(tab6);

        Tab tab7 = new Tab();
        Image image7 = new Image(AquaFx.class.getResource("demo/images/pages/diagramm.png").toExternalForm());
        ImageView diagramm = new ImageView(image7);
        diagramm.setPreserveRatio(true);
        diagramm.setFitHeight(16);
        tab7.setGraphic(diagramm);
        Label label7 = new Label("diagramm...");
        label7.setPadding(new Insets(15));
        tab7.setContent(label7);
        tabPane.getTabs().add(tab7);

        Tab tab8 = new Tab();
        Image image8 = new Image(AquaFx.class.getResource("demo/images/pages/verknuepfung.png").toExternalForm());
        ImageView verknuepfung = new ImageView(image8);
        verknuepfung.setPreserveRatio(true);
        verknuepfung.setFitHeight(16);
        tab8.setGraphic(verknuepfung);

        Label label8 = new Label("verknuepfung...");
        label8.setPadding(new Insets(15));

        TabPane innerTabpane = new TabPane();
        // innerTabpane.setMinWidth(268);
        Tab innerTab1 = new Tab("Hyperlink");
        innerTabpane.getTabs().add(innerTab1);
        VBox content = new VBox();
        content.setSpacing(5);
        content.setPadding(new Insets(10, 20, 10, 15));
        CheckBox cb = new CheckBox("Als Hyperlink aktivieren");
        cb.setStyle("-fx-font-weight: bold;");
        content.getChildren().add(cb);
        FlowPane flow1 = new FlowPane();
        Label l1 = new Label("Ziel:");
        ChoiceBox<String> choice = new ChoiceBox<>(FXCollections.observableArrayList("Webseite"));
        choice.getSelectionModel().selectFirst();
        choice.setPrefWidth(140);
        choice.setDisable(true);
        flow1.getChildren().addAll(l1, choice);
        flow1.setAlignment(Pos.CENTER_RIGHT);
        flow1.setHgap(5);
        content.getChildren().add(flow1);

        FlowPane flow2 = new FlowPane();
        Label l2 = new Label("URL:");
        TextField tf = new TextField();
        tf.setPrefWidth(140);
        tf.setDisable(true);
        flow2.getChildren().addAll(l2, tf);
        flow2.setAlignment(Pos.CENTER_RIGHT);
        flow2.setHgap(5);
        content.getChildren().add(flow2);

        VBox spacer = new VBox();
        spacer.setPrefSize(1, 50);
        content.getChildren().add(spacer);

        Separator sep = new Separator(Orientation.HORIZONTAL);
        sep.setPrefWidth(100);
        content.getChildren().add(sep);
        
        CheckBox cb2 = new CheckBox("Alle Hyperlinks deaktivieren");
        cb2.setPadding(new Insets(10));
        content.getChildren().add(cb2);
        
        
        innerTab1.setContent(content);

        Tab innerTab2 = new Tab("Lesez.");
        innerTabpane.getTabs().add(innerTab2);
        Tab innerTab3 = new Tab("Serienbrief");
        innerTabpane.getTabs().add(innerTab3);
        AquaFx.createTabPaneStyler().setSizeVariant(ControlSizeVariant.MINI).style(innerTabpane);
        innerTabpane.setPadding(new Insets(10, -10, -10, -10));

        tab8.setContent(innerTabpane);
        tabPane.getTabs().add(tab8);
        tabPane.getSelectionModel().select(tab8);

        Tab tab9 = new Tab();
        Image image9 = new Image(AquaFx.class.getResource("demo/images/pages/quicktime.png").toExternalForm());
        ImageView quicktime = new ImageView(image9);
        quicktime.setPreserveRatio(true);
        quicktime.setFitHeight(16);
        tab9.setGraphic(quicktime);
        Label label9 = new Label("quicktime...");
        label9.setPadding(new Insets(15));
        tab9.setContent(label9);
        tabPane.getTabs().add(tab9);

        root.getChildren().add(tabPane);

        AquaFx.style();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
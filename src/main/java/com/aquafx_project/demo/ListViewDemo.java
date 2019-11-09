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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import com.aquafx_project.AquaFx;

public class ListViewDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("John", "Doe", "john.doe@foo.com", "jd@foo.com", true),
            new Person("Jane", "Doe", "jane.doe@example.com", "jane.d@foo.com", false));

    @Override public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        
        HBox listContainer = new HBox();
        listContainer.setSpacing(10);
        listContainer.setPadding(new Insets(10));
        ListView<String> list = new ListView<String>();
        ObservableList<String> listItems = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3", "Item 4");
        list.setItems(listItems);
        list.setPrefWidth(150);
        list.setPrefHeight(70);
        listContainer.getChildren().add(list);
        TableView<Person> listTable = new TableView<Person>();
        listTable.getStyleClass().add("hide-header");
        listTable.setPrefHeight(250);
        listTable.setPrefWidth(150);
        TableColumn<Person, String> firstNameListCol = new TableColumn<Person, String>("First Name");
        firstNameListCol.setMinWidth(100);
        firstNameListCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        listTable.getColumns().add(firstNameListCol);
        listTable.setItems(data);
        listTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        listContainer.getChildren().add(listTable);
        ListView<String> horizontalList = new ListView<String>();
        horizontalList.setItems(listItems);
        horizontalList.setPrefWidth(250);
        horizontalList.setPrefHeight(50);
        horizontalList.setOrientation(Orientation.HORIZONTAL);
        listContainer.getChildren().add(horizontalList);
        
        pane.setCenter(listContainer);
        pane.setStyle("-fx-background-color: white;");
        Scene scene = new Scene(pane);
        AquaFx.style();
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
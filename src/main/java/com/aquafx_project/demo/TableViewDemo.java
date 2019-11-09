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
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.aquafx_project.AquaFx;

public class TableViewDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("John", "Doe", "john.doe@foo.com", "jd@foo.com", true),
            new Person("Jane", "Doe", "jane.doe@example.com", "jane.d@foo.com", false));

    @Override public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();

        HBox tableContainer = new HBox();
        tableContainer.setPadding(new Insets(20));
        TableView<Person> table = new TableView<Person>();
        // table.setPrefHeight(100);
        // table.setPrefWidth(250);
        table.setEditable(true);
        // table.getSelectionModel().setCellSelectionEnabled(true) ;

        TableColumn<Person, String> firstNameCol = new TableColumn<Person, String>("First Name");
        // firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setEditable(true);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
            @Override public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastName(t.getNewValue());
            }
        });
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        // TableColumn emailCol = new TableColumn("Email");
        TableColumn<Person, String> firstEmailCol = new TableColumn<Person, String>("Primary");
        firstEmailCol.setMinWidth(200);
        firstEmailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("primaryEmail"));
        TableColumn<Person, String> secondEmailCol = new TableColumn<Person, String>("Secondary");
        secondEmailCol.setMinWidth(200);
        secondEmailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("secondaryEmail"));
        // emailCol.getColumns().addAll(firstEmailCol, secondEmailCol);
        TableColumn<Person, Boolean> vipCol = new TableColumn<Person, Boolean>("VIP");
        vipCol.setEditable(true);
        vipCol.setCellFactory(CheckBoxTableCell.forTableColumn(vipCol));
        final Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>> cellFactory = CheckBoxTableCell.forTableColumn(vipCol);

        vipCol.setCellFactory(new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {
            @Override public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> column) {
                TableCell<Person, Boolean> cell = cellFactory.call(column);
                return cell;
            }
        });
        vipCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, Boolean>>() {
            @Override public void handle(CellEditEvent<Person, Boolean> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setVip(t.getNewValue());
            }
        });
        table.getColumns().addAll(firstNameCol);
        table.setItems(data);
        table.setTableMenuButtonVisible(false);
        table.setFocusTraversable(false);
        table.getStyleClass().add("hide-header");

        tableContainer.getChildren().add(table);

        pane.setCenter(tableContainer);
        pane.setStyle("-fx-background-color: white;");
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        AquaFx.style();
        primaryStage.show();

    }
}
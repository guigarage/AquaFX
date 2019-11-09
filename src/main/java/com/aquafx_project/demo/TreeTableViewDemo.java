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
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.aquafx_project.AquaFx;

public class TreeTableViewDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("John", "Doe", "john.doe@foo.com", "jd@foo.com", true),
            new Person("Jane", "Doe", "jane.doe@example.com", "jane.d@foo.com", false));

    @Override public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();

        HBox treeTableContainer = new HBox();
        treeTableContainer.setPadding(new Insets(10));
        TreeItem<Person> rootTreeTableItem = new TreeItem<Person>(new Person("Chef", "Chef", "chef@business.de", "chef@business.de", true));
        rootTreeTableItem.setExpanded(true);
        for (Person person : data) {
            TreeItem<Person> personLeaf = new TreeItem<Person>(person);
            boolean found = false;
            for (TreeItem<Person> statusNode : rootTreeTableItem.getChildren()) {
                if (statusNode.getValue().getVip() == person.getVip()) {
                    statusNode.getChildren().add(personLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<Person> statusNode = new TreeItem<Person>(person);
                rootTreeTableItem.getChildren().add(statusNode);
                statusNode.getChildren().add(personLeaf);
            }
        }
        TreeTableView<Person> treeTable = new TreeTableView<Person>(rootTreeTableItem);
        TreeTableColumn<Person, String> firstNameTreeCol = new TreeTableColumn<Person, String>("First Name");
        firstNameTreeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {

            @Override public ObservableValue<String> call(CellDataFeatures<Person, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getValue().getFirstName());
            }
        });

        TreeTableColumn<Person, String> lastNameTreeCol = new TreeTableColumn<Person, String>("Last Name");
        lastNameTreeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {

            @Override public ObservableValue<String> call(CellDataFeatures<Person, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getValue().getLastName());
            }
        });
        TreeTableColumn<Person, String> primaryMailCol = new TreeTableColumn<Person, String>("primary Mail");
        primaryMailCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person, String>, ObservableValue<String>>() {

            @Override public ObservableValue<String> call(CellDataFeatures<Person, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getValue().getPrimaryEmail());
            }
        });
        TreeTableColumn<Person, Boolean> vipTreeTableCol = new TreeTableColumn<Person, Boolean>("VIP");
        vipTreeTableCol.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(vipTreeTableCol));
        vipTreeTableCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person, Boolean>, ObservableValue<Boolean>>() {

            @Override public ObservableValue<Boolean> call(CellDataFeatures<Person, Boolean> param) {
                return new ReadOnlyBooleanWrapper(param.getValue().getValue().getVip());
            }
        });
        treeTable.getColumns().setAll(firstNameTreeCol, lastNameTreeCol, primaryMailCol, vipTreeTableCol);
        treeTable.setPrefHeight(150);
        treeTable.setPrefWidth(450);
        treeTableContainer.getChildren().add(treeTable);
        
        
        pane.setCenter(treeTableContainer);
        pane.setStyle("-fx-background-color: white;");
        Scene scene = new Scene(pane);
        AquaFx.style();
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
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

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

import com.sun.javafx.scene.control.skin.TabPaneSkin;

// public class AquaTabPaneSkin extends TabPaneSkin implements AquaSkin{
public class AquaTabPaneSkin extends TabPaneSkin implements AquaSkin{

    public AquaTabPaneSkin(TabPane tabPane) {
        super(tabPane);

        definePillPosition();
        adjustBorders();

        /**
         * Tabs are not closeable in Aqua
         */
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        addInactiveState();
    }

    @Override protected void handleControlPropertyChanged(String property) {
        super.handleControlPropertyChanged(property);
        if ("SIDE".equals(property)) {
            definePillPosition();
        }
        if ("SELECTED_TAB".equals(property)) {
            adjustBorders();
        }
    }

    private void definePillPosition() {
        ObservableList<Tab> list = getSkinnable().getTabs();
        for (Tab tab : list) {
            tab.getStyleClass().removeAll("first-tab", "last-tab", "single-tab");
        }
        if (list.size() > 1) {
            if (getSkinnable().getSide() == Side.TOP || getSkinnable().getSide() == Side.RIGHT) {
                list.get(0).getStyleClass().add("first-tab");
                list.get(list.size() - 1).getStyleClass().add("last-tab");
            } else if (getSkinnable().getSide() == Side.BOTTOM || getSkinnable().getSide() == Side.LEFT) {
                list.get(0).getStyleClass().add("last-tab");
                list.get(list.size() - 1).getStyleClass().add("first-tab");
            }
        } else if(list.size() == 1){
            list.get(0).getStyleClass().add("single-tab");
        }
    }

    private void adjustBorders() {
        boolean foundSelected = false;
        List<Tab> tabs = getSkinnable().getTabs();
        Tab selectedTab = getSkinnable().getSelectionModel().getSelectedItem();
        for (Tab tab : tabs) {
            tab.getStyleClass().remove("neighbor");
            if (foundSelected) {
                tab.getStyleClass().add("neighbor");
                foundSelected = false;
            }
            if (tab.equals(selectedTab)) {
                foundSelected = true;
            }
        }
    }
    
    private void addInactiveState() {

        final ChangeListener<Boolean> windowFocusChangedListener = new ChangeListener<Boolean>() {

            @Override public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (newValue != null) {
                    if (newValue.booleanValue()) {
                        getSkinnable().getStyleClass().remove("inactive");
                    } else {
                        getSkinnable().getStyleClass().add("inactive");
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
    }
}

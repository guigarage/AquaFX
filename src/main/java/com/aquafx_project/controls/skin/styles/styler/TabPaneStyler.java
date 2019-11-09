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

package com.aquafx_project.controls.skin.styles.styler;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import com.aquafx_project.controls.skin.styles.ControlSizeVariant;
import com.aquafx_project.controls.skin.styles.IllegalStyleCombinationException;
import com.aquafx_project.controls.skin.styles.StyleDefinition;
import com.aquafx_project.controls.skin.styles.TabPaneType;

/**
 * The TabPaneStyler with fluent API to change the default style of a TabPane.
 * 
 * @author claudinezillmann
 * 
 */
public class TabPaneStyler extends Styler<TabPane> {

    /**
     * TabPaneType of a TabPane.
     */
    private TabPaneType type;

    /**
     * Creates a new Instance of TabPaneStyler. This has to be the first invocation on
     * TabPaneStyler.
     * 
     * @return The TabPaneStyler.
     */
    public static TabPaneStyler create() {
        return new TabPaneStyler();
    }

    @Override public TabPaneStyler setSizeVariant(ControlSizeVariant sizeVariant) {
        return (TabPaneStyler) super.setSizeVariant(sizeVariant);
    }

    /**
     * Adds a TabPaneType to the TabPane
     * 
     * @param type
     *            The TabPaneType for the TabPane.
     * @return the TabPaneStyler with the added TabPaneType.
     */
    public TabPaneStyler setType(TabPaneType type) {
        this.type = type;
        check();
        return this;
    }

    @Override public void check() {
        if (type != null && (type.equals(TabPaneType.SMALL_ICON_BUTTONS) || type.equals(TabPaneType.ICON_BUTTONS)) 
                && getSizeVariant() != null && (getSizeVariant().equals(ControlSizeVariant.MINI) || getSizeVariant().equals(ControlSizeVariant.SMALL))) {
            throw new IllegalStyleCombinationException();
        }
    }

    @Override public List<StyleDefinition> getAll() {
        List<StyleDefinition> ret = new ArrayList<>(super.getAll());
        ret.add(getSizeVariant());
        ret.add(type);
        return ret;
    }

    @Override public void style(final TabPane tabPane) {
        super.style(tabPane);
        if (type != null && type == TabPaneType.ICON_BUTTONS) {
            Platform.runLater(new Runnable() {

                @Override public void run() {
                    for (Tab tab : tabPane.getTabs()) {
                        if (tab.getGraphic() != null) {
                            tab.getGraphic().getStyleClass().add("icon");
                        }
                    }
                }
            });
        }
    }

    public TabPaneType getType() {
        return type;
    }
}

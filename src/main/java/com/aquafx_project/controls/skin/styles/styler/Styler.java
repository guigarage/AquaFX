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

package com.aquafx_project.controls.skin.styles.styler;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Control;

import com.aquafx_project.controls.skin.styles.ControlSizeVariant;
import com.aquafx_project.controls.skin.styles.StyleDefinition;

/**
 * The Styler with fluent API to change the default style of a Control.
 * 
 * @author claudinezillmann
 * 
 * @param <T>
 *            T of type Control
 */
public class Styler<T extends Control> {

    /**
     * The SizeVariant for the Control.
     */
    private ControlSizeVariant sizeVariant;

    /**
     * Adds all StyleDfinitions, which were defined, to the Controls' style class definitions. This
     * has to be the last invocation on a Styler.
     * 
     * @param control
     *            The Control to be styled.
     */
    public void style(T control) {
        for (StyleDefinition definition : getAll()) {
            if (definition != null) {
                String style = definition.getStyleName();
                if (style != null) {
                    control.getStyleClass().add(style);
                }
            }
        }
    }

    /**
     * Adds a ControlSizeVariant to the Control.
     * 
     * @param sizeVariant
     *            The ControlSizeVariant for the Control.
     * @return the Styler with the added ControlSizeVariant.
     */
    public Styler<T> setSizeVariant(ControlSizeVariant sizeVariant) {
        this.sizeVariant = sizeVariant;
        check();
        return this;
    }

    /**
     * Retrieves the list with all StyleDefinitions added to the Styler.
     * 
     * @return The List of StyleDefinitions.
     */
    protected List<StyleDefinition> getAll() {
        List<StyleDefinition> ret = new ArrayList<>();
        ret.add(sizeVariant);
        return ret;
    }

    /**
     * The check method, which can be implemented in Styler subclasses to assure that no
     * incompatible Styles can be combined.
     */
    public void check() {}

    public ControlSizeVariant getSizeVariant() {
        return sizeVariant;
    }
}

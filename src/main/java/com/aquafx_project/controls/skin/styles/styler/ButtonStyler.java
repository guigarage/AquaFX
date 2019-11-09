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

import com.aquafx_project.controls.skin.AquaButtonSkin;
import com.aquafx_project.controls.skin.styles.ButtonType;
import com.aquafx_project.controls.skin.styles.ControlSizeVariant;
import com.aquafx_project.controls.skin.styles.IllegalStyleCombinationException;
import com.aquafx_project.controls.skin.styles.MacOSDefaultIcons;
import com.aquafx_project.controls.skin.styles.StyleDefinition;

import javafx.scene.control.Button;
import javafx.scene.control.Skin;

/**
 * The ButtonStyler with fluent API to change the default style of a Button.
 * 
 * @author claudinezillmann
 * 
 */
public class ButtonStyler extends Styler<Button> {

    /**
     * ButtonType of a Button.
     */
    private ButtonType type;
    /**
     * Icon for a Button.
     */
    private MacOSDefaultIcons icon;

    private ButtonStyler() {}

    /**
     * Creates a new Instance of ButtonStyler. This has to be the first invocation on ButtonStyler.
     * 
     * @return The ButtonStyler.
     */
    public static ButtonStyler create() {
        return new ButtonStyler();
    }

    /**
     * Adds a ButtonType to the Button
     * 
     * @param type
     *            The ButtonType for the Button.
     * @return the ButtonStyler with the added ButtonType.
     */
    public ButtonStyler setType(ButtonType type) {
        this.type = type;
        check();
        return this;
    }

    @Override public ButtonStyler setSizeVariant(ControlSizeVariant sizeVariant) {
        return (ButtonStyler) super.setSizeVariant(sizeVariant);
    }

    /**
     * Adds an Icon to the Button
     * 
     * @param icon
     *            The Icon of type MacOSDefaultIcons.
     * @return the ButtonStyler with the added Icon.
     */
    public ButtonStyler setIcon(MacOSDefaultIcons icon) {
        this.icon = icon;
        check();
        return this;
    }

    @Override public List<StyleDefinition> getAll() {
        List<StyleDefinition> ret = new ArrayList<>(super.getAll());
        ret.add(getSizeVariant());
        ret.add(type);
        return ret;
    }

    @Override public void check() {
        if (type != null && type.equals(ButtonType.HELP) && icon != null) {
            throw new IllegalStyleCombinationException();
        }
    }

    @Override public void style(final Button button) {
        super.style(button);
        button.setSkin(new AquaButtonSkin(button));
        Skin<?> skin = button.getSkin();
        if (skin != null && skin instanceof AquaButtonSkin) {
            ((AquaButtonSkin) skin).iconProperty().setValue(icon);
        }
    }
    
    public ButtonType getType() {
        return type;
    }

    public MacOSDefaultIcons getIcon() {
        return icon;
    }

}

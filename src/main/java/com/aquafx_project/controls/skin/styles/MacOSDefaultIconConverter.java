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

package com.aquafx_project.controls.skin.styles;

import javafx.css.ParsedValue;
import javafx.css.StyleConverter;
import javafx.scene.text.Font;

import com.sun.javafx.css.StyleConverterImpl;

/**
 * 
 * Converts the CSS for -fx-aqua-icon items into a MacOSDefaultIcons. Used by
 * {@link com.aquafx_project.controls.skin.AquaButtonSkin AquaButtonSkin}.
 * 
 * @author claudinezillmann
 * 
 */
public final class MacOSDefaultIconConverter extends StyleConverterImpl<String, MacOSDefaultIcons> {

    private static class Holder {
        static MacOSDefaultIconConverter ICON_INSTANCE = new MacOSDefaultIconConverter();
    }

    // lazy, thread-safe instantiation
    public static StyleConverter<String, MacOSDefaultIcons> getInstance() {
        return Holder.ICON_INSTANCE;
    }

    private MacOSDefaultIconConverter() {
        super();
    }

    @Override public MacOSDefaultIcons convert(ParsedValue<String, MacOSDefaultIcons> value, Font font) {
        String str = value.getValue();
        if (str == null || str.isEmpty() || "null".equals(str)) {
            return null;
        }
        try {
            return MacOSDefaultIcons.valueOf(str);
        } catch (final IllegalArgumentException e) {
            // TODO: use logger here
            System.err.println("not a Mac Icon: " + value);
            return MacOSDefaultIcons.RIGHT;
        }
    }

    @Override public String toString() {
        return "MacOSDefaultIconConverter";
    }
}
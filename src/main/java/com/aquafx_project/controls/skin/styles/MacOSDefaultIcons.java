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

package com.aquafx_project.controls.skin.styles;

/**
 * MacOSDefaultIcons values are used to define different Icons that can be applied to Controls, as
 * e.g. Buttons.
 * <p>
 * There are different types of Icons available on Mac OS X.
 * 
 * @author claudinezillmann
 * 
 */
public enum MacOSDefaultIcons implements StyleDefinition {
    /**
     * LEFT indicates an Icon that is an arrow to the left.
     */
    LEFT,
    /**
     * RIGHT indicates an Icon that is an arrow to the right.
     */
    RIGHT,
    /**
     * SHARE indicates an Icon that is the Mac OS share icon.
     */
    SHARE,
    /**
     * SEARCH indicates an Icon that is the Mac OS search icon (magnifying glass).
     */
    SEARCH,
    /**
     * DELETE indicates an Icon that is the Mac OS delete icon (x-symbol).
     */
    DELETE;

    /**
     * Constructs a String as name for the StyleClass.
     * 
     * @return the name for the MacOSDefaultIcon
     */
    @Override public String getStyleName() {
        String prefix = "icon";
        if (this.equals(LEFT)) {
            return prefix + "-" + "left";
        }
        if (this.equals(RIGHT)) {
            return prefix + "-" + "right";
        }
        if (this.equals(SHARE)) {
            return prefix + "-" + "share";
        }
        if (this.equals(SEARCH)) {
            return prefix + "-" + "search";
        }
        if (this.equals(DELETE)) {
            return prefix + "-" + "delete";
        }
        return null;
    }
}

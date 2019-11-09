package com.aquafx_project.controls.skin.css;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import com.aquafx_project.controls.skin.AquaButtonSkin;
import com.aquafx_project.controls.skin.AquaFocusBorder;
import com.aquafx_project.controls.skin.styles.MacOSDefaultIconConverter;
import com.aquafx_project.controls.skin.styles.MacOSDefaultIcons;
import com.sun.javafx.css.converters.PaintConverter;

public class AquaCssProperties {

    private static CssMetaData<Button, MacOSDefaultIcons> iconMetadata;
    private static CssMetaData<Control, Paint> innerFocusColorMetadata;
    private static CssMetaData<Control, Paint> outerFocusColorMetadata;

    public static <T> StyleableObjectProperty<T> createProperty(final Object bean, final String propertyName, final CssMetaData<? extends Styleable, T> metaData) {
        return new StyleableObjectProperty<T>() {

            @Override public CssMetaData<? extends Styleable, T> getCssMetaData() {
                return metaData;
            }

            @Override public Object getBean() {
                return bean;
            }

            @Override public String getName() {
                return propertyName;
            }
        };
    }
    
    public static CssMetaData<Button, MacOSDefaultIcons> getIconMetaData() {
        if (iconMetadata == null) {
            iconMetadata = new CssMetaData<Button, MacOSDefaultIcons>("-fx-aqua-icon", MacOSDefaultIconConverter.getInstance()) {
                @Override public boolean isSettable(Button n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return ((AquaButtonSkin) skin).iconProperty() == null || !((AquaButtonSkin) skin).iconProperty().isBound();
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<MacOSDefaultIcons> getStyleableProperty(Button n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaButtonSkin) {
                        return (StyleableProperty<MacOSDefaultIcons>) ((AquaButtonSkin) skin).iconProperty();
                    }
                    return null;
                }
            };
        }
        return iconMetadata;
    }
     
    public static CssMetaData<Control, Paint> getInnerFocusColorMetaData() {
        if (innerFocusColorMetadata == null) {
            innerFocusColorMetadata = new CssMetaData<Control, Paint>("-fx-aqua-inner-focus-color", PaintConverter.getInstance(), Color.BLUE) {
                @Override public boolean isSettable(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaFocusBorder) {
                        return true;
                    }
                    return false;
                }

                @SuppressWarnings("unchecked") @Override public StyleableProperty<Paint> getStyleableProperty(Control n) {
                    Skin<?> skin = n.getSkin();
                    if (skin != null && skin instanceof AquaFocusBorder) {
                        return (StyleableProperty<Paint>) ((AquaFocusBorder) skin).innerFocusColorProperty();
                    }
                    return null;
                }
            };
        }
        return innerFocusColorMetadata;
    }
    
   public static CssMetaData<Control, Paint> getOuterFocusColorMetaData() {
       if (outerFocusColorMetadata == null) {
           outerFocusColorMetadata = new CssMetaData<Control, Paint>("-fx-aqua-outer-focus-color", PaintConverter.getInstance(), Color.BLUE) {
               @Override public boolean isSettable(Control n) {
                   Skin<?> skin = n.getSkin();
                   if (skin != null && skin instanceof AquaFocusBorder) {
                       return true;
                   }
                   return false;
               }

               @SuppressWarnings("unchecked") @Override public StyleableProperty<Paint> getStyleableProperty(Control n) {
                   Skin<?> skin = n.getSkin();
                   if (skin != null && skin instanceof AquaFocusBorder) {
                       return (StyleableProperty<Paint>) ((AquaFocusBorder) skin).outerFocusColorProperty();
                   }
                   return null;
               }
           };
       }
       return outerFocusColorMetadata;
   }
}

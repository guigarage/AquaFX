package com.aquafx_project.controls.skin.styles;

import org.junit.Ignore;
import org.junit.Test;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import junit.framework.Assert;

public class StyleFactoryTest {

    @Test
    @Ignore
    public void test1() {
        StyleFactory factory = new StyleFactory();
        Button b = new Button();
        factory.addStyles(b, ControlSizeVariant.MINI);
        assertStyles(b, "button", "size-variant-mini");
    }
    
    public void assertStyles(Control c, String... styles) {
        if(styles == null || styles.length == 0) {
            Assert.assertEquals(0, c.getStyleClass().size());
            return;
        }
        Assert.assertEquals(styles.length, c.getStyleClass().size());
        for (String style : styles) {
            Assert.assertEquals(true, c.getStyleClass().contains(style));
        }
    }
}

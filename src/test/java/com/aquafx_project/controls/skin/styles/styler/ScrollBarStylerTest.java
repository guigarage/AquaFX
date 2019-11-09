package com.aquafx_project.controls.skin.styles.styler;

import org.junit.Assert;
import org.junit.Test;

import com.aquafx_project.controls.skin.styles.ControlSizeVariant;

public class ScrollBarStylerTest {

    @Test 
    public void testStylerCreation() {
        ScrollBarStyler styler = ScrollBarStyler.create();
        Assert.assertNotNull(styler);
    }

    @Test 
    public void testSetSize() {
        ScrollBarStyler styler = ScrollBarStyler.create();
        styler.setSizeVariant(ControlSizeVariant.MINI);
        Assert.assertEquals(ControlSizeVariant.MINI, styler.getSizeVariant());
    }
    
    @Test
    public void testApplyMultipleStyles() {
        try {
            ScrollBarStyler styler = ScrollBarStyler.create();
            styler.setSizeVariant(ControlSizeVariant.SMALL);
            Assert.assertEquals(ControlSizeVariant.SMALL, styler.getSizeVariant());
            styler.setSizeVariant(ControlSizeVariant.MINI);
            Assert.assertEquals(ControlSizeVariant.MINI, styler.getSizeVariant());
         } catch (Exception e) {
             Assert.fail("No Exception expected");
         } 
    }
}

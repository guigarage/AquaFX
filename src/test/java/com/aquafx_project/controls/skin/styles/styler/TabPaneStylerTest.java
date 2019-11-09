package com.aquafx_project.controls.skin.styles.styler;

import org.junit.Assert;
import org.junit.Test;

import com.aquafx_project.controls.skin.styles.ControlSizeVariant;
import com.aquafx_project.controls.skin.styles.IllegalStyleCombinationException;
import com.aquafx_project.controls.skin.styles.TabPaneType;

public class TabPaneStylerTest {

    @Test 
    public void testStylerCreation() {
        TabPaneStyler styler = TabPaneStyler.create();
        Assert.assertNotNull(styler);
    }

    @Test 
    public void testSetSize() {
        TabPaneStyler styler = TabPaneStyler.create();
        styler.setSizeVariant(ControlSizeVariant.MINI);
        Assert.assertNotNull(styler.getSizeVariant());
        Assert.assertEquals(ControlSizeVariant.MINI, styler.getSizeVariant());
    }
    
    @Test 
    public void testSetType() {
        TabPaneStyler styler = TabPaneStyler.create();
        styler.setType(TabPaneType.ICON_BUTTONS);
        Assert.assertNotNull(styler.getType());
        Assert.assertEquals(styler.getType(), TabPaneType.ICON_BUTTONS);
        Assert.assertTrue(styler.getAll().contains(TabPaneType.ICON_BUTTONS));
    }
    
    @Test(expected = IllegalStyleCombinationException.class) 
    public void testExceptionIsThrown() {
        TabPaneStyler styler = TabPaneStyler.create();
        styler.setType(TabPaneType.SMALL_ICON_BUTTONS).setSizeVariant(ControlSizeVariant.MINI);
    }

    @Test
    public void testExceptionMessage() {
        try {
            TabPaneStyler styler = TabPaneStyler.create();
            styler.setType(TabPaneType.SMALL_ICON_BUTTONS).setSizeVariant(ControlSizeVariant.MINI);
            Assert.fail("Exception not thrown");
         } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "The applied StyleDefinitions cannot be combined");
         } 
    }
    
    @Test
    public void testApplyMultipleStyles() {
        try {
            TabPaneStyler styler = TabPaneStyler.create();
            styler.setType(TabPaneType.ICON_BUTTONS)
                .setSizeVariant(ControlSizeVariant.REGULAR);
         } catch (Exception e) {
             Assert.fail("No Exception expected");
         } 
    }
}

package com.aquafx_project.controls.skin.styles.styler;

import org.junit.Assert;
import org.junit.Test;

import com.aquafx_project.controls.skin.styles.ButtonType;
import com.aquafx_project.controls.skin.styles.ControlSizeVariant;
import com.aquafx_project.controls.skin.styles.IllegalStyleCombinationException;
import com.aquafx_project.controls.skin.styles.MacOSDefaultIcons;

public class ButtonStylerTest {

    @Test 
    public void testStylerCreation() {
        ButtonStyler styler = ButtonStyler.create();
        Assert.assertNotNull(styler);
    }

    @Test 
    public void testSetSize() {
        ButtonStyler styler = ButtonStyler.create();
        styler.setSizeVariant(ControlSizeVariant.MINI);
        Assert.assertEquals(ControlSizeVariant.MINI, styler.getSizeVariant());
    }
    
    @Test 
    public void testSetIcon() {
        ButtonStyler styler = ButtonStyler.create();
        styler.setIcon(MacOSDefaultIcons.SHARE);
        Assert.assertNotNull(styler.getIcon());
        Assert.assertEquals(styler.getIcon(), MacOSDefaultIcons.SHARE);
    }
    
    @Test 
    public void testSetType() {
        ButtonStyler styler = ButtonStyler.create();
        styler.setType(ButtonType.ROUND_RECT);
        Assert.assertNotNull(styler.getType());
        Assert.assertEquals(styler.getType(), ButtonType.ROUND_RECT);
        Assert.assertTrue(styler.getAll().contains(ButtonType.ROUND_RECT));
    }
    
    @Test(expected = IllegalStyleCombinationException.class) 
    public void testExceptionIsThrown() {
        ButtonStyler styler = ButtonStyler.create();
        styler.setType(ButtonType.HELP).setIcon(MacOSDefaultIcons.SEARCH);
    }

    @Test
    public void testExceptionMessage() {
        try {
            ButtonStyler styler = ButtonStyler.create();
            styler.setType(ButtonType.HELP).setIcon(MacOSDefaultIcons.SEARCH);
            Assert.fail("Exception not thrown");
         } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "The applied StyleDefinitions cannot be combined");
         } 
    }
    
    @Test
    public void testApplyMultipleStyles() {
        try {
            ButtonStyler styler = ButtonStyler.create();
            styler.setType(ButtonType.ROUND_RECT)
                .setIcon(MacOSDefaultIcons.SEARCH)
                .setSizeVariant(ControlSizeVariant.SMALL);
         } catch (Exception e) {
             Assert.fail("No Exception expected");
         } 
    }
}

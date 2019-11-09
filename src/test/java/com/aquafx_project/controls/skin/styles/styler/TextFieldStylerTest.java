package com.aquafx_project.controls.skin.styles.styler;

import org.junit.Assert;
import org.junit.Test;

import com.aquafx_project.controls.skin.styles.ControlSizeVariant;
import com.aquafx_project.controls.skin.styles.IllegalStyleCombinationException;
import com.aquafx_project.controls.skin.styles.TextFieldType;

public class TextFieldStylerTest {

    @Test 
    public void testStylerCreation() {
        TextFieldStyler styler = TextFieldStyler.create();
        Assert.assertNotNull(styler);
    }

    @Test 
    public void testSetSize() {
        TextFieldStyler styler = TextFieldStyler.create();
        styler.setSizeVariant(ControlSizeVariant.MINI);
        Assert.assertEquals(ControlSizeVariant.MINI, styler.getSizeVariant());
    }

    @Test 
    public void testSetType() {
        TextFieldStyler styler = TextFieldStyler.create();
        styler.setType(TextFieldType.ROUND_RECT);
        Assert.assertNotNull(styler.getType());
        Assert.assertEquals(styler.getType(), TextFieldType.ROUND_RECT);
        Assert.assertTrue(styler.getAll().contains(TextFieldType.ROUND_RECT));
    }
    
    @Test(expected = IllegalStyleCombinationException.class) 
    public void testExceptionIsThrown() {
        TextFieldStyler styler = TextFieldStyler.create();
        styler.setType(TextFieldType.SEARCH).setSizeVariant(ControlSizeVariant.MINI);
    }

    @Test
    public void testExceptionMessage() {
        try {
            TextFieldStyler styler = TextFieldStyler.create();
            styler.setType(TextFieldType.SEARCH).setSizeVariant(ControlSizeVariant.MINI);
            Assert.fail("Exception not thrown");
         } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "The applied StyleDefinitions cannot be combined");
         } 
    }
    
    @Test
    public void testApplyMultipleStyles() {
        try {
            TextFieldStyler styler = TextFieldStyler.create();
            styler.setType(TextFieldType.ROUND_RECT)
                .setSizeVariant(ControlSizeVariant.SMALL);
         } catch (Exception e) {
             Assert.fail("No Exception expected");
         } 
    }
}

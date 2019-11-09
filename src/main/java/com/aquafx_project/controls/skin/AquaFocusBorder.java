package com.aquafx_project.controls.skin;

import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Paint;

public interface AquaFocusBorder {
    
    public ObjectProperty<Paint> innerFocusColorProperty();
    
    public void setInnerFocusColor(Paint innerFocusColor);
    
    public Paint getInnerFocusColor();
    
    public ObjectProperty<Paint> outerFocusColorProperty();
    
    public void setOuterFocusColor(Paint outerFocusColor);
    
    public Paint getOuterFocusColor();
}

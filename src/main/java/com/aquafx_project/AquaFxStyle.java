package com.aquafx_project;

import com.guigarage.stylemanager.AbstractApplicationStyle;
import com.guigarage.stylemanager.StyleType;

public class AquaFxStyle extends AbstractApplicationStyle {

	private final static String AQUAFX_CSS_FILE = "aquafx.css";

	public AquaFxStyle() {
		super(AquaFxStyle.class.getResource(AQUAFX_CSS_FILE));
	}
	
	public static String getAquafxCssFile() {
        return AQUAFX_CSS_FILE;
    }
	
	@Override
	public StyleType getType() {
		return StyleType.SYSTEM;
	}

	@Override
	public String getPreferredOsName() {
		return "MAC OS X";
	}

}

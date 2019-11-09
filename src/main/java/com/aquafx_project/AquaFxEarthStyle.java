package com.aquafx_project;

import com.guigarage.stylemanager.AbstractApplicationStyle;
import com.guigarage.stylemanager.StyleType;

public class AquaFxEarthStyle extends AbstractApplicationStyle {

    private final static String AQUAFX_EARTH_CSS_FILE = "aquafx-earth.css";

	public AquaFxEarthStyle() {
		super(AquaFxFireStyle.class.getResource(AquaFxStyle.getAquafxCssFile()), AquaFxEarthStyle.class.getResource(AQUAFX_EARTH_CSS_FILE));
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

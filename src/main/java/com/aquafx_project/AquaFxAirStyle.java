package com.aquafx_project;

import com.guigarage.stylemanager.AbstractApplicationStyle;
import com.guigarage.stylemanager.StyleType;

public class AquaFxAirStyle extends AbstractApplicationStyle {

    private final static String AQUAFX_AIR_CSS_FILE = "aquafx-air.css";

	public AquaFxAirStyle() {
		super(AquaFxFireStyle.class.getResource(AquaFxStyle.getAquafxCssFile()), AquaFxAirStyle.class.getResource(AQUAFX_AIR_CSS_FILE));
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

package com.aquafx_project;

import com.guigarage.stylemanager.AbstractApplicationStyle;
import com.guigarage.stylemanager.StyleType;

public class AquaFxFireStyle extends AbstractApplicationStyle {

    private final static String AQUAFX_FIRE_CSS_FILE = "aquafx-fire.css";

	public AquaFxFireStyle() {
		super(AquaFxFireStyle.class.getResource(AquaFxStyle.getAquafxCssFile()), AquaFxFireStyle.class.getResource(AQUAFX_FIRE_CSS_FILE));
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

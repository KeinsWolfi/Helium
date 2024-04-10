package me.helium9.screens.rise.ClickGui.modSetting;

import java.util.ArrayList;

import me.helium9.module.Module;
import me.helium9.screens.rise.ClickGui.ClickGui;
import me.helium9.screens.rise.ClickGui.comp.ModButton;


public class ModSettingManager {
	public ArrayList<ModSettingGui> modSettingRender = new ArrayList<>();
	
	public static Module mod = null;
	
	
	public ModSettingManager(int centerW, int centerH) {
		//reset
		modSettingRender.removeAll(modSettingRender);
		
		//add
		for(ModButton modButton : ClickGui.modButtonToRender) {
			this.modSettingRender.add(new ModSettingGui(centerW+205, centerH-100, 200, 200, modButton.mod));
		}
	}
	
	public void render() {
		if(mod != null) {
			for(ModSettingGui modSettingGui : modSettingRender) {
				if(modSettingGui.mod == mod) {
					modSettingGui.render();
					continue;
				}
			}
		}
		
	}
	


}

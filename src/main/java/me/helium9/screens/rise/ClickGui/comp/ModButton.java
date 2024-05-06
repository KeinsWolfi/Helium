package me.helium9.screens.rise.ClickGui.comp;

import java.awt.Color;

import me.helium9.module.Module;

import me.helium9.screens.rise.ClickGui.modSetting.ModSettingManager;
import me.helium9.util.render.RoundedUtil;
import net.minecraft.client.Minecraft;

public class ModButton {
	
	public int x,y,w,h;
	public Module mod;
	public int id;
	
	public ModButton(int x, int y, int w, int h, Module mod, int id) {
		this.x = x-85;
		this.y = y-80;
		this.w = w;
		this.h = h;
		this.mod = mod;
		this.id = id;
	}
	
	public void render() {
		
		RoundedUtil.drawRoundedRect(x , y , x+w , y+h , 8, new Color(224,214,214,255));
		RoundedUtil.drawRoundedRect((x+w)-28, (y+h)-18, (x+w)-5 , (y+h)-7, 8, getColor());
		Minecraft.getMinecraft().fontRendererObj.drawString(mod.name, x +8, y +8, new Color(0,0,0,255).getRGB());

	}
	
	private Color getColor() {
		if(mod.isToggled()) {
			return new Color(131,255,92,255);
		} else {
			return new Color(255,64,59,255);
		}

	}
	
	public void onClick(int mouseX, int mouseY, int button) {
		if(button == 0) {
			if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
				if(mod.isToggled()) {
					mod.setToggled(false);
				}
				else {
					mod.setToggled(true);
				}
	
			}
		}
		else if(button == 1) {
			if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
				if(ModSettingManager.mod != mod) {
					ModSettingManager.mod = mod;
				}
				else {
					ModSettingManager.mod = null;
				}
			}
		}

	}
}

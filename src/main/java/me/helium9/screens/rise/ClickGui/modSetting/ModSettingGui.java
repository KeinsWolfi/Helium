package me.helium9.screens.rise.ClickGui.modSetting;

import java.awt.Color;

import me.helium9.module.Module;
import me.helium9.util.render.RoundedUtil;
import net.minecraft.client.Minecraft;

public class ModSettingGui {
	public Module mod;
	public int x,y,w,h;
	
	public ModSettingGui(int x, int y, int w, int h,Module mod) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.mod = mod;
	}
	
	
	public void render() {
		RoundedUtil.drawRoundedRect(x , y , x+w , y+h, 8, new Color(245, 242, 233,255).getRGB());
		RoundedUtil.drawRoundedRect(x , y , x+w , y+10, 8, new Color(138, 66, 88,255).getRGB());
		Minecraft.getMinecraft().fontRendererObj.drawString(mod.getName() + " : " + mod.isToggled(), x + 3, y + 13, new Color(0,0,0,255).getRGB());
		Minecraft.getMinecraft().fontRendererObj.drawString(mod.description, x + 3, y + 23, new Color(0,0,0,255).getRGB());
	}
}

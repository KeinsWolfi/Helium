package me.helium9.screens.rise.ClickGui.comp;

import java.awt.Color;

import me.helium9.util.render.Animation.Animation;
import me.helium9.util.render.Animation.Animations.EaseInOutSine;
import me.helium9.util.render.Animation.Direction;
import me.helium9.util.render.RoundedUtil;
import net.minecraft.client.Minecraft;

public class ClickGuiCategoryButton extends CategoryManager{
	public int x,y,w,h,r;
	private String name;
	private boolean isOnThisPage = false;
	private int number = 0;
	CategoryManager categoryManager;

	private Animation animation = new EaseInOutSine(200, 1);

	public ClickGuiCategoryButton(int x, int y, int w, int h, String name, int number) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.name = name;
		this.number = number;

	}




	public void renderButton(){

		if(animation.getOutput() >=x+1) {
			RoundedUtil.drawRoundedRect(x, y, animation.getOutput().floatValue(), y+h, 5, new Color(191,226,246,255));
			Minecraft.getMinecraft().fontRendererObj.drawString(name, x + w/2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(name)/2,
					y + h/2 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT/2, new Color(
							20, 23, 34,255).getRGB());
		}
		else {
			Minecraft.getMinecraft().fontRendererObj.drawString(name, x + w/2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(name)/2,
					y + h/2 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT/2, new Color(
							20, 23, 34,255).getRGB());
		}


		if(CategoryManager.currentPage == number) {
			isOnThisPage = true;
		}

		else if(!isOnThisPage) {
			animation.setDirection(Direction.BACKWARDS);
		}


	}

	public void onClick(int mouseX, int mouseY, int button) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			this.isOnThisPage = true;
			CategoryManager.thisPage(number);
		}
	}

	public String getName() {
		return name;
	}
	public boolean isOnThisPage() {
		return isOnThisPage;

	}

	public void setIsOnThisPage(boolean value) {
		this.isOnThisPage = value;

	}



}
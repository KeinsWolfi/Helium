package me.helium9.screens.rise.ClickGui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import lombok.Getter;
import me.helium9.HeliumMain;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.screens.rise.ClickGui.comp.CategoryManager;
import me.helium9.screens.rise.ClickGui.comp.ClickGuiCategoryButton;
import me.helium9.screens.rise.ClickGui.comp.ModButton;
import me.helium9.screens.rise.ClickGui.modSetting.ModSettingManager;
import me.helium9.util.render.RoundedUtil;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ClickGui extends GuiScreen{

	@Getter
    public static ArrayList<ClickGuiCategoryButton> clickGuiCategoryButton = new ArrayList<>();
	
	public static ArrayList<ModButton> modButtonToRender = new ArrayList<>();
	
	ScaledResolution sr;
	private ModSettingManager msManager;
	
	int backgroundW = 200;
	int centerW;
	int centerH;
	
	
	@Override
	public void initGui() {
		sr = new ScaledResolution(mc);
		centerW = sr.getScaledWidth()/2;
		centerH = sr.getScaledHeight()/2;
		reset();
		clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 200, centerH - 65,100,20,  "Player",0));
		clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 200, centerH - 45,100,20,  "World",1));
		clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 200, centerH - 25,100,20,  "Visual",2));
		clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 200, centerH - 5,100,20,  "Util",3));
		clickGuiCategoryButton.add(new ClickGuiCategoryButton(centerW - 200, centerH + 15,100,20,  "HUD",4));
			
		int modButtonW = 260;
		int modButtonH = 25;
		int spaceBetween = 26;
		int offset = 0;
		for(Module mod : HeliumMain.INSTANCE.getMm().getModulesByCat(Category.Player)) {
			this.modButtonToRender.add(new ModButton(centerW,centerH + offset * spaceBetween, modButtonW, modButtonH, mod,0));
			offset++;
		}
		offset = 0;
		for (Module mod : HeliumMain.INSTANCE.getMm().getModulesByCat(Category.World)) {
			this.modButtonToRender.add(new ModButton(centerW,centerH + offset * spaceBetween, modButtonW, modButtonH, mod,1));
			offset++;
		}

		offset = 0;
		for (Module mod : HeliumMain.INSTANCE.getMm().getModulesByCat(Category.Visual)) {
			this.modButtonToRender.add(new ModButton(centerW,centerH + offset * spaceBetween, modButtonW, modButtonH, mod,2));
			offset++;
		}

		offset = 0;
		for(Module mod : HeliumMain.INSTANCE.getMm().getModulesByCat(Category.Misc)) {
			this.modButtonToRender.add(new ModButton(centerW,centerH + offset * spaceBetween, modButtonW, modButtonH, mod,3));
			offset++;
		}

		offset = 0;
		for(Module mod : HeliumMain.INSTANCE.getMm().getModulesByCat(Category.HUD)) {
			this.modButtonToRender.add(new ModButton(centerW,centerH + offset * spaceBetween, modButtonW, modButtonH, mod,4));
			offset++;
		}
		
		msManager = new ModSettingManager(centerW,centerH);
	}
	@Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		centerW = sr.getScaledWidth()/2;
		centerH = sr.getScaledHeight()/2;
		
		GlStateManager.pushAttrib();
		GlStateManager.pushMatrix();
		RoundedUtil.drawRoundedRect(centerW - backgroundW, centerH - 100, centerW + backgroundW, centerH + 100, 8, new Color(245, 242, 233,255));
		RoundedUtil.drawRoundedRect(centerW - backgroundW + 390, centerH - 100, centerW + backgroundW, centerH + 100, 8, new Color(138, 66, 88,255));
		GlStateManager.popMatrix();
		msManager.render();
		
		for(ClickGuiCategoryButton clickGuiCategoryButton :clickGuiCategoryButton) {
			clickGuiCategoryButton.renderButton();
		}
		
		int wheel = Mouse.getDWheel();
        for (ModButton modButton : modButtonToRender) {
        	if(modButton.id == CategoryManager.currentPage) {
	            GL11.glEnable(GL11.GL_SCISSOR_TEST);
	            this.glScissor(centerW - backgroundW, centerH - 90, centerW + backgroundW, 180);
	            modButton.render();
	            if (wheel < 0) {
	            	modButton.y -= 8;
	            } else if (wheel > 0) {
	            	modButton.y += 8;
	            }
	            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        	}
        }
        GlStateManager.popAttrib();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if(mouseX >= (centerW - backgroundW) && mouseX <= (centerW + backgroundW) && mouseY >= (centerH - 90) && mouseY <= (centerH + 90)) {
			for(ModButton modButton : modButtonToRender) {
				if(modButton.id == CategoryManager.currentPage) {
					modButton.onClick(mouseX, mouseY, mouseButton);
				}
			}
		}
		
		for(ClickGuiCategoryButton clickGuiCategoryButton :clickGuiCategoryButton) {
			clickGuiCategoryButton.onClick(mouseX, mouseY, mouseButton);
		}
		
	}

    @Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		// TODO Auto-generated method stub
		super.keyTyped(typedChar, keyCode);
	}
	
	private static void reset() {
		modButtonToRender.removeAll(modButtonToRender);
		clickGuiCategoryButton.removeAll(clickGuiCategoryButton);
		
	}
	
	private void glScissor(double x, double y, double width, double height) {

        y += height;

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        Minecraft mc = Minecraft.getMinecraft();

        GL11.glScissor((int) ((x * mc.displayWidth) / scaledResolution.getScaledWidth()),
                (int) (((scaledResolution.getScaledHeight() - y) * mc.displayHeight) / scaledResolution.getScaledHeight()),
                (int) (width * mc.displayWidth / scaledResolution.getScaledWidth()),
                (int) (height * mc.displayHeight / scaledResolution.getScaledHeight()));
    }

	
	
}
	
	
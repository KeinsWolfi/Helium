package me.helium9.screens.dropdown;

import me.helium9.HeliumMain;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.impl.visual.ClickGUI;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DropDownGui extends GuiScreen {

    private final List<Frame> frames;

    public static Module extendedModule;

    public DropDownGui(){
        frames = new ArrayList<>();

        int offset = 30;
        for(Category cat : Category.values()){
            frames.add(new Frame(cat, offset, 35, 85, 15));
            offset += 130;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(Frame frames : frames){
            frames.drawScreen(mouseX, mouseY, partialTicks);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for(Frame frame : frames){
            frame.keyTyped(typedChar, keyCode);
        }

        if(keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_RSHIFT){
            HeliumMain.INSTANCE.getMm().getModule(ClickGUI.class).toggle();
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(Frame frame : frames){
            frame.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for(Frame frame : frames){
            frame.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }


}

package me.helium9.module.impl.visual;

import me.helium9.event.impl.render.EventModelRender;
import me.helium9.module.Category;
import me.helium9.module.Module;
import me.helium9.module.ModuleInfo;
import me.helium9.settings.impl.ModeSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

import static org.lwjgl.opengl.GL11.*;

@ModuleInfo(
        name = "Chams",
        description = "Chams",
        category = Category.Visual,
        excludeArray = "false"
)
public class Chams extends Module {
    private int i = 0;

    private final ModeSetting mode = new ModeSetting("Mode", "Normal", "Color");

    public Chams(){
        addSettings(mode);
    }

    @Subscribe
    public final Listener<EventModelRender> onModelRender = new Listener<>(e -> {
        if(e.isPre()){
            glPushMatrix();
            glEnable(GL_POLYGON_OFFSET_LINE);
            glPolygonOffset(1F, 1000000F);

            if(mode.getCurrentMode().equals("Color")){
                glDisable(GL_TEXTURE_2D);
                glDisable(GL_LIGHTING);
                glColor4f(1f, 1f, 1f, 1f);
            }

            //glDisable(GL_LIGHTING);

            glDisable(GL_DEPTH_TEST);
            glDepthMask(false);
        }else {
            glEnable(GL_DEPTH_TEST);
            glDepthMask(true);

            e.drawModel();

            if(mode.getCurrentMode().equals("Color")){
                glEnable(GL_TEXTURE_2D);
                glColor3f(1f,1f,1f);
            }
            glDisable(GL_BLEND);
            glEnable(GL_LIGHTING);

            glPolygonOffset(1.0f, -1000000.0f);
            glDisable(GL_POLYGON_OFFSET_LINE);
            glPopMatrix();
        }
        i++;
    });

}

package org.moon.figura.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.moon.figura.utils.FiguraIdentifier;
import org.moon.figura.utils.ui.UIHelper;

public class SwitchButton extends TexturedButton {

    public static final ResourceLocation SWITCH_TEXTURE = new FiguraIdentifier("textures/gui/switch.png");

    protected boolean toggled = false;
    private boolean defaultTexture = false;
    private float headPos = 0f;

    //text constructor
    public SwitchButton(int x, int y, int width, int height, Component text, Component tooltip, OnPress pressAction) {
        super(x, y, width, height, text, tooltip, pressAction);
    }

    //texture constructor
    public SwitchButton(int x, int y, int width, int height, int u, int v, int interactionOffset, ResourceLocation texture, int textureWidth, int textureHeight, Component tooltip, OnPress pressAction) {
        super(x, y, width, height, u, v, interactionOffset, texture, textureWidth, textureHeight, tooltip, pressAction);
    }

    //default texture constructor
    public SwitchButton(int x, int y, int width, int height, boolean toggled) {
        super(x, y, width, height, 0, 0, 10, SWITCH_TEXTURE, 30, 40, null, button -> {});
        this.toggled = toggled;
        this.headPos = toggled ? 20f : 0f;
        defaultTexture = true;
    }

    @Override
    public void onPress() {
        this.toggled = !this.toggled;
        super.onPress();
    }

    @Override
    protected void renderTexture(PoseStack stack, float delta) {
        if (defaultTexture) {
            renderDefaultTexture(stack, delta);
        } else {
            super.renderTexture(stack, delta);
        }
    }

    @Override
    protected void renderText(PoseStack stack) {
        //draw text
        drawCenteredString(
                stack, Minecraft.getInstance().font,
                (this.toggled ? getMessage().copy().withStyle(ChatFormatting.UNDERLINE) : getMessage()),
                this.x + this.width / 2, this.y + this.height / 2 - 4,
                (!this.active ? ChatFormatting.DARK_GRAY : ChatFormatting.WHITE).getColor()
        );
    }

    protected void renderDefaultTexture(PoseStack stack, float delta) {
        //set texture
        UIHelper.setupTexture(SWITCH_TEXTURE);

        //render switch
        blit(stack, x + 5, y + 5, 20, 10, 10f, (this.toggled ? 20f : 0f) + (this.isHoveredOrFocused() ? 10f : 0f), 20, 10, 30, 40);

        //render head
        headPos = (float) Mth.lerp(1f - Math.pow(0.2f, delta), headPos, this.toggled ? 20f : 0f);
        blit(stack, Math.round(x + headPos), y, 10, 20, 0f, this.isHoveredOrFocused() ? 20f : 0f, 10, 20, 30, 40);
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }
}

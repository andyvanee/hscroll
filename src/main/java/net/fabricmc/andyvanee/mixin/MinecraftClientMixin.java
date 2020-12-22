package net.fabricmc.andyvanee.mixin;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.util.Window;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    private MinecraftClient client;
    private double eventDeltaWheel;
    private boolean mixinConfigured;

    @Inject(at = @At("RETURN"), method = "openScreen")
    private void init(CallbackInfo info) {
        if (!mixinConfigured) {
            this.client = MinecraftClient.getInstance();
            this.eventDeltaWheel = 0.0D;
            // Override MC scroll callback with our own
            GLFW.glfwSetScrollCallback(this.client.getWindow().getHandle(), this::onMouseScroll);
        }
        mixinConfigured = true;
    }

    private void onMouseScroll(long window, double xoffset, double yoffset) {
        if (window == MinecraftClient.getInstance().getWindow().getHandle()) {

            if (MinecraftClient.IS_SYSTEM_MAC && yoffset == 0) {
                yoffset = xoffset;
            }

            double delta = (this.client.options.discreteMouseScroll ? Math.signum(yoffset) : yoffset)
                    * this.client.options.mouseWheelSensitivity;

            if (this.client.overlay == null) {
                Window clientWindow = MinecraftClient.getInstance().getWindow();

                if (this.client.currentScreen != null) {
                    double xposition = this.client.mouse.getX() * (double) clientWindow.getScaledWidth()
                            / (double) clientWindow.getWidth();
                    double yposition = this.client.mouse.getY() * (double) clientWindow.getScaledHeight()
                            / (double) clientWindow.getHeight();
                    this.client.currentScreen.mouseScrolled(xposition, yposition, delta);
                } else if (this.client.player != null) {
                    if (this.eventDeltaWheel != 0.0D && Math.signum(delta) != Math.signum(this.eventDeltaWheel)) {
                        this.eventDeltaWheel = 0.0D;
                    }

                    this.eventDeltaWheel += delta;
                    float currentWheelDelta = (float) ((int) this.eventDeltaWheel);
                    if (currentWheelDelta == 0.0F) {
                        return;
                    }

                    this.eventDeltaWheel -= (double) currentWheelDelta;
                    if (this.client.player.isSpectator()) {
                        if (this.client.inGameHud.getSpectatorHud().isOpen()) {
                            this.client.inGameHud.getSpectatorHud().cycleSlot((double) (-currentWheelDelta));
                        } else {
                            float j = MathHelper.clamp(
                                    this.client.player.abilities.getFlySpeed() + currentWheelDelta * 0.005F, 0.0F,
                                    0.2F);
                            this.client.player.abilities.setFlySpeed(j);
                        }
                    } else {
                        this.client.player.inventory.scrollInHotbar((double) currentWheelDelta);
                    }
                }
            }
        }
    }
}

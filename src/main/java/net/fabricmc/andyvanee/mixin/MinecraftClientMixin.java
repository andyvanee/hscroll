package net.fabricmc.andyvanee.mixin;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	private MinecraftClient client;
	private double eventDeltaWheel;

	@Inject(at = @At("RETURN"), method = "init")
	private void init(CallbackInfo info) {
		this.client = MinecraftClient.getInstance();
		this.eventDeltaWheel = 0.0D;
		// Override MC scroll callback with our own
		GLFW.glfwSetScrollCallback(this.client.window.getHandle(), this::onMouseScroll);
	}

	private void onMouseScroll(long window, double xoffset, double yoffset) {
		// TODO: implement these as minecraft settings?
		boolean translateHorizontalScroll = true;
		double horizontalScrollSensitivity = 1.0D;

		if (window == MinecraftClient.getInstance().window.getHandle()) {
			double delta;

			if (translateHorizontalScroll) {
				double xvector = xoffset * horizontalScrollSensitivity;
				double xinput = (this.client.options.discreteMouseScroll ? Math.signum(xvector) : xvector)
						* this.client.options.mouseWheelSensitivity;
				double yinput = (this.client.options.discreteMouseScroll ? Math.signum(yoffset) : yoffset)
						* this.client.options.mouseWheelSensitivity;
				delta = Math.abs(xoffset) < Math.abs(yoffset) ? yinput : xinput;
			} else {
				delta = (this.client.options.discreteMouseScroll ? Math.signum(yoffset) : yoffset)
						* this.client.options.mouseWheelSensitivity;
			}

			if (this.client.overlay == null) {
				if (this.client.currentScreen != null) {
					double xposition = this.client.mouse.getX() * (double) this.client.window.getScaledWidth()
							/ (double) this.client.window.getWidth();
					double yposition = this.client.mouse.getY() * (double) this.client.window.getScaledHeight()
							/ (double) this.client.window.getHeight();
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
						if (this.client.inGameHud.getSpectatorWidget().method_1980()) {
							this.client.inGameHud.getSpectatorWidget().method_1976((double) (-currentWheelDelta));
						} else {
							float float_2 = MathHelper.clamp(
									this.client.player.abilities.getFlySpeed() + currentWheelDelta * 0.005F, 0.0F,
									0.2F);
							this.client.player.abilities.setFlySpeed(float_2);
						}
					} else {
						this.client.player.inventory.scrollInHotbar((double) currentWheelDelta);
					}
				}
			}
		}
	}
}

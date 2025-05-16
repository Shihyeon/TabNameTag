package kr.shihyeon.tabnametag.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

    @ModifyVariable(
            method = "renderNameTag(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private Component tabnametag$renderNameTag(Component name, AbstractClientPlayer abstractClientPlayer, Component originalName, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return name;

        return client.player.connection.getOnlinePlayers().stream()
                .filter(p -> p.getProfile().getId().equals(abstractClientPlayer.getUUID()))
                .findFirst()
                .map(p -> p.getTabListDisplayName() != null ? p.getTabListDisplayName() : Component.literal(p.getProfile().getName()))
                .orElse(abstractClientPlayer.getDisplayName());
    }
}

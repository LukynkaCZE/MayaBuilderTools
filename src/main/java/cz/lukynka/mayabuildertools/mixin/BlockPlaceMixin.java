package cz.lukynka.mayabuildertools.mixin;

import cz.lukynka.mayabuildertools.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Objects;

import static cz.lukynka.mayabuildertools.MayaBuilderTools.allowedCustomOrientationBlocks;
import static cz.lukynka.mayabuildertools.MayaBuilderTools.customOrientationDirection;

@Mixin(net.minecraft.world.item.BlockItem.class)
public class BlockPlaceMixin {
    @Inject(at = @At("HEAD"), method = "place")
    public void place(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<InteractionResult> cir) {
        assert Minecraft.getInstance().player != null;
        var player = Minecraft.getInstance().player;
        // Don't do anything if held item is not any of the allowed items
        if(!(Utils.heldItemContains(allowedCustomOrientationBlocks))) return;
        //If it's default, let it be normal minecraft behaviour
        if(!customOrientationDirection.equals("DEFAULT")) {
            // We send a packet to the server to make it think that player is looking in selected direction,
            // this will get reverted back to default (on the server) when player moves
            var packet = new ServerboundMovePlayerPacket.Rot(getRotationFromDirection(), 1F, player.isOnGround());
            Objects.requireNonNull(Minecraft.getInstance().getConnection()).send(packet);
        }
    }
    private static float getRotationFromDirection() {
        assert Minecraft.getInstance().player != null;
        return switch (customOrientationDirection) {
            case "SOUTH":
                yield 0;
            case "WEST":
                yield 90;
            case "NORTH":
                yield 180;
            case "EAST":
                yield -90;
            default:
                yield Minecraft.getInstance().player.getRotationVector().x;
        };
    }
}
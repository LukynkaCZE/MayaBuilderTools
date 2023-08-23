package cz.lukynka.mayabuildertools.UI;

import cz.lukynka.mayabuildertools.Utils;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;


import java.util.Objects;

import static cz.lukynka.mayabuildertools.MayaBuilderTools.glazedTerracottaDirection;
import static cz.lukynka.mayabuildertools.MayaBuilderTools.glazedTerracottaDirections;

public class GlazedTerracottaPicker extends BaseOwoScreen<FlowLayout> {

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        var background = Containers.verticalFlow(Sizing.fixed(110), Sizing.fixed(110));
        background.surface(Surface.VANILLA_TRANSLUCENT);
        background.horizontalAlignment(HorizontalAlignment.CENTER);
        background.verticalAlignment(VerticalAlignment.CENTER);

        var directionListContainer = Containers.verticalFlow(Sizing.fixed(100), Sizing.fixed(100));

        for (String direction : glazedTerracottaDirections) {
            directionListContainer.child(getDirectionContainer(direction));
        }

        background.child(directionListContainer);
        rootComponent.child(background);
    }

    private FlowLayout getDirectionContainer(String type) {
        var layout = Containers.horizontalFlow(Sizing.fixed(100), Sizing.fixed(20));

        assert Minecraft.getInstance().player != null;

        var rotation = switch (type) {
            case "EAST" -> Rotation.COUNTERCLOCKWISE_90;
            case "WEST" -> Rotation.CLOCKWISE_90;
            case "SOUTH" -> Rotation.NONE;
            default -> Rotation.CLOCKWISE_180;
        };
        var blockState = Block.byItem(Minecraft.getInstance().player.getMainHandItem().getItem()).defaultBlockState().rotate(rotation);
        if(type.equals("DEFAULT")) {
            blockState = Block.byItem(Items.AIR).defaultBlockState();
        }

        layout.horizontalAlignment(HorizontalAlignment.LEFT);
        layout.verticalAlignment(VerticalAlignment.TOP);
        layout.child(Components.block(blockState)
                .sizing(Sizing.fixed(16), Sizing.fixed(16)))
                .verticalAlignment(VerticalAlignment.CENTER);

        var textContainer = Containers.horizontalFlow(Sizing.fixed(84), Sizing.fixed(20));
        textContainer.verticalAlignment(VerticalAlignment.CENTER);
        textContainer.horizontalAlignment(HorizontalAlignment.RIGHT);

        var textComp = Component.literal("Facing " +Utils.toStrictProperCase(type));
        if(type.equals(glazedTerracottaDirection)) {
            textComp = Component.literal("Facing " +Utils.toStrictProperCase(type)).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.UNDERLINE);
        }

        textContainer.child(Components.label(textComp)
                .verticalTextAlignment(VerticalAlignment.CENTER)
                .horizontalTextAlignment(HorizontalAlignment.RIGHT)
        );

        layout.child(textContainer);

        return layout;
    }
}


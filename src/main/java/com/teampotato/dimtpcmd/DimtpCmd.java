package com.teampotato.dimtpcmd;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(DimtpCmd.MOD_ID)
public class DimtpCmd {
    public static final String MOD_ID = "dimtpcmd";

    public DimtpCmd() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onRegisterCmd(RegisterCommandsEvent event) {
        event.getDispatcher().register(LiteralArgumentBuilder.<CommandSourceStack>literal("dimtp")
                .requires(player -> player.hasPermission(2))
                .then(
                        RequiredArgumentBuilder.<CommandSourceStack, ResourceLocation>argument("targetDimension", DimensionArgument.dimension())
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    Vec3 pos = player.position();
                                    player.teleportTo(DimensionArgument.getDimension(context, "dimension"), pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());
                                    return 1;
                                })
                                .then(
                                        RequiredArgumentBuilder.<CommandSourceStack, Coordinates>argument("targetPosition", Vec3Argument.vec3())
                                                .executes(context -> {
                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                    Vec3 pos = Vec3Argument.getVec3(context, "position");
                                                    player.teleportTo(DimensionArgument.getDimension(context, "dimension"), pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());
                                                    return 1;
                                                })
                                )
                )
        );
    }
}
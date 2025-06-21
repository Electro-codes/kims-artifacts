package net.keb4.kims_artifacts.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.entity.capability.CapRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandRegistry {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        // Register the base command: /mycommand
        dispatcher.register(
                Commands.literal(Main.MODID)
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .then(Commands.literal("resonance")
                                .then(Commands.literal("set")
                                        .then(Commands.argument("id", StringArgumentType.string())
                                                .then(Commands.argument("float", FloatArgumentType.floatArg(0,1))
                                                        .executes(CommandRegistry::runResonanceChange)))
                                )        ));
    }


    public static int runResonanceChange(CommandContext<CommandSourceStack> context)
    {
        ServerPlayer player = context.getSource().getPlayer();
        float val = FloatArgumentType.getFloat(context, "float");
        ResourceLocation id = ResourceLocation.parse(StringArgumentType.getString(context, "id"));
        player.getCapability(CapRegistry.PLAYER_ARTIFACT_CAP).resolve().get().getResonanceValues().replace(id, val);
        return 1;
    }

}

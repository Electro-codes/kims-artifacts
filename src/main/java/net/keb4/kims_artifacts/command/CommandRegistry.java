package net.keb4.kims_artifacts.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.sun.jdi.connect.Connector;
import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.item.ItemRegistry;
import net.keb4.kims_artifacts.world.ArtifactGenData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandRegistry {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("artifactgendata")
                .requires(e -> e.hasPermission(0))
                .then(Commands.argument("resourceLocation", StringArgumentType.string())
                        .then(Commands.argument("value", BoolArgumentType.bool()).executes(CommandRegistry::artifactGenData)
                                )));

    }
    public static int artifactGenData(CommandContext<CommandSourceStack> ctx)
    {
        ResourceLocation location = ResourceLocation.parse(StringArgumentType.getString(ctx, "resourceLocation"));
        if (ForgeRegistries.ITEMS.getValue(location) == null) return 0;
        Set<RegistryObject<Item>> i = ItemRegistry.getArtifacts();
        for (RegistryObject<Item> item : i)
        {
            if (item.getId() == location)
            {
                Main.LOGGER.info("found item!");
                ArtifactGenData.get(ctx.getSource().getLevel()).setGenerated(location);
            }
        }
        return 1;
    }

}

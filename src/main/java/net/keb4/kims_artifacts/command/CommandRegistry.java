package net.keb4.kims_artifacts.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.system.artifact.potion.PotionSysUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandRegistry {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("potiontip")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                .executes(CommandRegistry::potionTip));

    }
    public static int potionTip(CommandContext<CommandSourceStack> ctx)
    {
        ServerPlayer player = ctx.getSource().getPlayer();
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();

        if (main.getItem() instanceof SwordItem && off.getItem() instanceof PotionItem)
        {
            PotionSysUtil.Craft.tipWeapon(main, off);
            Main.LOGGER.info("TIPPED!");
        }
        return 1;
    }

}

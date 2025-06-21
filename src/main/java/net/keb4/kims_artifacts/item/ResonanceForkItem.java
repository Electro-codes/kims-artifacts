package net.keb4.kims_artifacts.item;

import net.keb4.kims_artifacts.client.particle.ParticleTypes;
import net.keb4.kims_artifacts.entity.capability.CapRegistry;
import net.keb4.kims_artifacts.sound.SoundRegistry;
import net.keb4.kims_artifacts.util.RayUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.Option;
import javax.tools.Tool;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ResonanceForkItem extends Item {
    public ResonanceForkItem() {
        super(new Properties().rarity(Rarity.COMMON).stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if (pLevel.isClientSide)
        {
            return super.use(pLevel, pPlayer, pUsedHand);
        }
        Vec3 pos = RayUtils.simpleBlockRay(pPlayer, 2, true).getLocation();

        List<? extends Entity> entities = RayUtils.getEntitiesFromBox((ServerLevel) pLevel, pos, new Vec3(0.5,0.5,0.5), entity -> entity instanceof ItemEntity);
        Set<ArtifactItem> filteredList = new HashSet<>();
        for (Entity e : entities)
        {
            if (e instanceof ItemEntity itemEntity)
            {
                if (((ItemEntity) e).getItem().getItem() instanceof ArtifactItem)
                {
                    filteredList.add(((ArtifactItem) ((ItemEntity) e).getItem().getItem()));
                }
            }
        }


        if (filteredList.toArray()[0] == null) return super.use(pLevel, pPlayer, pUsedHand);
        ArtifactItem chosen = ((ArtifactItem) filteredList.toArray()[0]);

        float val = pPlayer.getCapability(CapRegistry.PLAYER_ARTIFACT_CAP).resolve().get().getImmutableResonanceValues().get(ForgeRegistries.ITEMS.getKey(chosen));


        for (ResonationFrequency freq : ResonationFrequency.values())
        {
            if (val >= freq.threshold)
            {
                pPlayer.sendSystemMessage(freq.message);
                pPlayer.getCooldowns().addCooldown(this,100);
                SoundEvent chosenSound = SoundRegistry.VIBRATION_VERY_LOW.get();
                switch(freq)
                {
                    case LOW -> chosenSound = SoundRegistry.VIBRATION_LOW.get();
                    case VERY_LOW -> chosenSound = SoundRegistry.VIBRATION_VERY_LOW.get();
                    case MEDIUM -> chosenSound = SoundRegistry.VIBRATION_MEDIUM.get();
                    case HIGH -> chosenSound = SoundRegistry.VIBRATION_HIGH.get();
                    case VERY_HIGH -> chosenSound = SoundRegistry.VIBRATION_VERY_HIGH.get();
                }
                pLevel.playSound(null, new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), chosenSound, SoundSource.PLAYERS, 1, 1);
                return super.use(pLevel,pPlayer,pUsedHand);
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public static enum ResonationFrequency
    {
        VERY_HIGH(Component.literal("The artifact vibrates violently in tune with the fork.").withStyle(ChatFormatting.AQUA), 0.85f),
        HIGH(Component.literal("The artifact pulses with the frequency.").withStyle(ChatFormatting.YELLOW), 0.79f),
        MEDIUM(Component.literal("The artifact hums gently.").withStyle(ChatFormatting.WHITE),0.49f),
        LOW(Component.literal("The artifact stirs.").withStyle(ChatFormatting.GRAY), 0.29f),
        VERY_LOW(Component.literal("The artifact does not respond.").withStyle(ChatFormatting.GRAY), 0.0f);

        public final Component message;
        public final float threshold;
        ResonationFrequency(Component message, float threshold)
        {
            this.message = message;
            this.threshold = threshold;
        }
    }
}

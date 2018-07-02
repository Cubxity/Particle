package me.chachy.particlemodifer;

import cc.hyperium.Hyperium;
import cc.hyperium.config.ConfigOpt;
import cc.hyperium.event.EventBus;
import cc.hyperium.event.InitializationEvent;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.PlayerAttackEntityEvent;
import cc.hyperium.internal.addons.IAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.EntityDamageSource;

public class ParticleMod implements IAddon {
    @ConfigOpt
    public static int multiplier = 1;

    @Override
    public void onLoad() {
        EventBus.INSTANCE.register(this);
    }

    @Override
    public void onClose() {
    }

    @Override
    public void sendDebugInfo() {

    }

    @InvokeEvent
    public void onInit(InitializationEvent e) {
        Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand(new ParticleModCommand());
        Hyperium.CONFIG.register(this);
        System.out.println("Successfully initialized ParticleMod 1.02 by dewgs");
        System.out.println("Ported by Chachy and KodingKing.");
    }

    @InvokeEvent
    public void onAttack(final PlayerAttackEntityEvent event) {
        final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null || multiplier == 1) return;
        final boolean critical = player.fallDistance > 0f && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null;
        if (!critical) return;
        final float enchantment = EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{player.getHeldItem()}, new EntityDamageSource("player", player));
        for (int i = 1; i < multiplier; ++i) {
            if (enchantment > 0f)
                Minecraft.getMinecraft().thePlayer.onEnchantmentCritical(player);
            else Minecraft.getMinecraft().thePlayer.onCriticalHit(player);
        }
    }
}



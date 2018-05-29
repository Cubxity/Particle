package me.chachy.particlemodifer;

import cc.hyperium.Hyperium;
import cc.hyperium.commands.BaseCommand;
import cc.hyperium.event.EventBus;
import cc.hyperium.event.InitializationEvent;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.PlayerAttackEntityEvent;
import net.minecraft.client.*;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

import java.io.*;

public class ParticleMod
{
    public static final String MODID = "particlemod";
    public static final String VERSION = "1.02";
    public int multiplier;

    public ParticleMod() {
        this.multiplier = 1;
    }

    @InvokeEvent
    public void init(final InitializationEvent event) {
        EventBus.INSTANCE.register((Object)this);
        Hyperium.INSTANCE.getHandlers().getHyperiumCommandHandler().registerCommand((BaseCommand) new ParticleModCommand(this));
        this.loadConfig();
        System.out.println("Successfully initialized ParticleMod 1.02 by dewgs");
    }

    @InvokeEvent
    public void onAttack(final PlayerAttackEntityEvent event) {
        final Entity entity = Minecraft.getMinecraft().thePlayer;
        if (!(entity instanceof EntityLivingBase)) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
        final boolean critical = player.fallDistance > 0.0f && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null;
        final float enchantment = EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] {player.getHeldItem()}, new EntityDamageSource("player", player));
        for (int i = 1; i < this.multiplier; ++i) {
            if (critical) {
                Minecraft.getMinecraft().thePlayer.onCriticalHit(entity);
            }
            if (enchantment > 0.0f) {
                Minecraft.getMinecraft().thePlayer.onEnchantmentCritical(entity);
            }
        }
    }

    public void saveConfig() {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "ParticleMod", "value.cfg");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            final FileWriter writer = new FileWriter(file, false);
            writer.write(this.multiplier + "");
            writer.close();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "ParticleMod", "value.cfg");
            if (!file.exists()) {
                return;
            }
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final String line = reader.readLine();
            if (line != null) {
                this.multiplier = Integer.parseInt(line);
            }
            reader.close();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}



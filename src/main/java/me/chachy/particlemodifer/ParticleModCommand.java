package me.chachy.particlemodifer;

import cc.hyperium.commands.BaseCommand;
import cc.hyperium.commands.CommandException;
import cc.hyperium.utils.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ParticleModCommand implements BaseCommand {

    private ParticleMod mod;
    private String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + ChatColor.BOLD + "ParticleAddon" + ChatColor.DARK_AQUA + "] " + ChatColor.WHITE;

    public ParticleModCommand(final ParticleMod mod) {
        this.mod = mod;
    }

    @Override
    public String getName() {
        return "particlemod";
    }

    @Override
    public String getUsage() {
        return "/particlemod <multiplier>";
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        if (args.length < 1) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentText(prefix + "Current particle multiplier: " + this.mod.multiplier));
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentText(prefix + "Change it by doing " + getUsage()));
            return;
        }
        if (!this.tryParse(args[0])) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentText(prefix + "Usage: " + getUsage()));
            return;
        }
        final Integer multiplier = Integer.parseInt(args[0]);
        if (multiplier < 1) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentText(prefix + "Usage: " + getUsage()));
            return;
        }
        this.mod.multiplier = multiplier;
        this.mod.saveConfig();
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentText(prefix + "Particle multiplier set to " + multiplier + "!"));
    }

    private boolean tryParse(final String parse) {
        try {
            Integer.parseInt(parse);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


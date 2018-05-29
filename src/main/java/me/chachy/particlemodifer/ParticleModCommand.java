package me.chachy.particlemodifer;

import net.minecraft.command.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class ParticleModCommand extends CommandBase
{
    private ParticleMod mod;

    public ParticleModCommand(final ParticleMod mod) {
        this.mod = mod;
    }

    public String getCommandName() {
        return "particlemod";
    }

    public String getCommandUsage(final ICommandSender sender) {
        return "/particlemod <multiplier>";
    }

    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length < 1) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Current particle multiplier: " + this.mod.multiplier));
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(""));
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Change it by doing " + this.getCommandUsage(sender)));
            return;
        }
        if (!this.tryParse(args[0])) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Usage: " + this.getCommandUsage(sender)));
            return;
        }
        final Integer multiplier = Integer.parseInt(args[0]);
        if (multiplier < 1) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Usage: " + this.getCommandUsage(sender)));
            return;
        }
        this.mod.multiplier = multiplier;
        this.mod.saveConfig();
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Particle multiplier set to " + multiplier + "!"));
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }

    private boolean tryParse(final String parse) {
        try {
            Integer.parseInt(parse);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}


package me.chachy.particlemodifer;

import cc.hyperium.commands.BaseCommand;
import cc.hyperium.commands.CommandException;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ParticleModCommand implements BaseCommand {

    private ParticleMod mod;

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
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentText("Current particle multiplier: " + this.mod.multiplier));
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentText(""));
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentText("Change it by doing " + getUsage()));
            return;
        }
        if (!this.tryParse(args[0])) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentText("Usage: " + getUsage()));
            return;
        }
        final Integer multiplier = Integer.parseInt(args[0]);
        if (multiplier < 1) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentText("Usage: " + getUsage()));
            return;
        }
        this.mod.multiplier = multiplier;
        this.mod.saveConfig();
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent) new ChatComponentText("Particle multiplier set to " + multiplier + "!"));
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


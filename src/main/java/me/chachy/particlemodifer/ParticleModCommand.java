package me.chachy.particlemodifer;

import cc.hyperium.Hyperium;
import cc.hyperium.commands.BaseCommand;

import static cc.hyperium.utils.ChatColor.*;

public class ParticleModCommand implements BaseCommand {

    private final String prefix = DARK_AQUA + "[" + AQUA + BOLD + "ParticleAddon" + DARK_AQUA + "] " + WHITE;

    @Override
    public String getName() {
        return "particlemod";
    }

    @Override
    public String getUsage() {
        return "/particlemod <multiplier>";
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length < 1) {
            Hyperium.INSTANCE.getHandlers().getGeneralChatHandler().sendMessage(prefix + "Current particle multiplier: " + ParticleMod.multiplier);
            Hyperium.INSTANCE.getHandlers().getGeneralChatHandler().sendMessage(prefix + "Change it by doing " + getUsage());
            return;
        }
        if (!this.tryParse(args[0])) {
            Hyperium.INSTANCE.getHandlers().getGeneralChatHandler().sendMessage(prefix + "Usage: " + getUsage());
            return;
        }
        final Integer multiplier = Integer.parseInt(args[0]);
        if (multiplier < 1) {
            Hyperium.INSTANCE.getHandlers().getGeneralChatHandler().sendMessage(prefix + "Usage: " + getUsage());
            return;
        }
        ParticleMod.multiplier = multiplier;
        Hyperium.INSTANCE.getHandlers().getGeneralChatHandler().sendMessage(prefix + "Particle multiplier set to " + multiplier + "!");
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


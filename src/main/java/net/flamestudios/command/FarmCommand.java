package net.flamestudios.command;

import net.flamestudios.FarmSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public final class FarmCommand implements CommandExecutor, TabCompleter {

    public FarmCommand() {
        Objects.requireNonNull(FarmSystem.getInstance().getCommand("farm")).setExecutor(this);
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        if (!sender.hasPermission("farm.reload")) {
            sender.sendRichMessage("<red>You don't have permission to use this command!");
            return true;
        }

        final long started = System.currentTimeMillis();

        try {
            FarmSystem.getInstance().getFarmConfig().load();
            FarmSystem.getInstance().getRegionService().invalidate();
            sender.sendRichMessage("<green>Configuration reloaded <gray>(" + (System.currentTimeMillis() - started) + "ms<gray>).");
        } catch (Exception e) {
            sender.sendRichMessage("<red>Failed to reload configuration: " + e.getMessage());
            FarmSystem.getInstance().getLogger().severe("Reload failed: " + e.getMessage());
        }

        return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String alias, final @NotNull String[] args) {
        return List.of();
    }
}
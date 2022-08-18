package me.hsgamer.bettergui.xcross.action;

import com.cryptomorin.xseries.NoteBlockMusic;
import me.hsgamer.bettergui.api.action.BaseAction;
import me.hsgamer.bettergui.builder.ActionBuilder;
import me.hsgamer.hscore.task.BatchRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MusicAction extends BaseAction {
    public MusicAction(ActionBuilder.Input input) {
        super(input);
    }

    @Override
    public void accept(UUID uuid, BatchRunnable.Process process) {
        String replacedString = getReplacedString(uuid);
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            process.next();
            return;
        }
        NoteBlockMusic.playMusic(player, player::getLocation, replacedString).thenAccept(v -> process.next());
    }
}

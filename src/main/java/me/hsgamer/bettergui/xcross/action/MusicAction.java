package me.hsgamer.bettergui.xcross.action;

import com.cryptomorin.xseries.NoteBlockMusic;
import me.hsgamer.bettergui.builder.ActionBuilder;
import me.hsgamer.hscore.action.common.Action;
import me.hsgamer.hscore.common.StringReplacer;
import me.hsgamer.hscore.task.element.TaskProcess;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MusicAction implements Action {
    private final String value;

    public MusicAction(ActionBuilder.Input input) {
        this.value = input.getValue();
    }

    @Override
    public void apply(UUID uuid, TaskProcess process, StringReplacer stringReplacer) {
        String replacedString = stringReplacer.replaceOrOriginal(value, uuid);
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            process.next();
            return;
        }
        NoteBlockMusic.playMusic(player, player::getLocation, replacedString).thenAccept(v -> process.next());
    }
}

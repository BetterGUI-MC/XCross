package me.hsgamer.bettergui.xcross.action;

import com.cryptomorin.xseries.XSound;
import me.hsgamer.bettergui.builder.ActionBuilder;
import me.hsgamer.hscore.action.common.Action;
import me.hsgamer.hscore.common.StringReplacer;
import me.hsgamer.hscore.task.element.TaskProcess;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class XSoundAction implements Action {
    private final String value;

    public XSoundAction(ActionBuilder.Input input) {
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
        Optional.ofNullable(XSound.parse(replacedString))
                .map(XSound.Record::soundPlayer)
                .map(soundPlayer -> soundPlayer.forPlayers(player))
                .ifPresent(XSound.SoundPlayer::play);
        process.next();
    }
}

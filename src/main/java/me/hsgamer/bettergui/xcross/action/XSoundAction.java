package me.hsgamer.bettergui.xcross.action;

import com.cryptomorin.xseries.XSound;
import me.hsgamer.bettergui.api.action.BaseAction;
import me.hsgamer.bettergui.builder.ActionBuilder;
import me.hsgamer.hscore.task.element.TaskProcess;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class XSoundAction extends BaseAction {
    public XSoundAction(ActionBuilder.Input input) {
        super(input);
    }

    @Override
    public void accept(UUID uuid, TaskProcess process) {
        String replacedString = getReplacedString(uuid);
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            process.next();
            return;
        }
        XSound.play(player, replacedString).thenAccept(XSound.Record::play).thenAccept(v -> process.next());
    }
}

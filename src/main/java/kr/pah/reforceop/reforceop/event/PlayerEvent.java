package kr.pah.reforceop.reforceop.event;

import kr.pah.reforceop.reforceop.ReForceOp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;


public class PlayerEvent implements Listener {
    public PlayerEvent(ReForceOp plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        getPlayer(event).sendMessage(getPlayer(event).getName() + "님이 접속하셨습니다.");
        if (getPlayer(event).getName().contains("DanO")) {
            getPlayer(event).sendMessage("[ALYac] 바이러스가 감지되어 차단되었습니다.");
            getPlayer(event).kick();
        }
    }

    @NotNull
    private static Player getPlayer(PlayerJoinEvent event) {
        return event.getPlayer();
    }
}

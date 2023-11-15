package kr.pah.reforceop.reforceop;

import org.bukkit.plugin.java.JavaPlugin;

public final class ReForceOp extends JavaPlugin {
    private SocketServer socketServer;

    @Override
    public void onEnable() {
        socketServer = new SocketServer(this, 1234, getLogger());
        getLogger().info("플러그인이 활성화 되었습니다.");
    }

    @Override
    public void onDisable() {
        if (socketServer != null) {
            socketServer.stopServer();
        }
    }
}

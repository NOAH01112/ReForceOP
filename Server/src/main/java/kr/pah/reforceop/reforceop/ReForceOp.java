package kr.pah.reforceop.reforceop;

import org.bukkit.plugin.java.JavaPlugin;

public final class ReForceOp extends JavaPlugin {
    private SocketServer socketServer;

    @Override
    public void onEnable() {
        socketServer = new SocketServer(this, 1234);
    }

    @Override
    public void onDisable() {
        if (socketServer != null) {
            socketServer.stopServer();
        }
    }
}

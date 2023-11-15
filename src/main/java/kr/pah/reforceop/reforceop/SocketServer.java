package kr.pah.reforceop.reforceop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketServer {
    private ServerSocket serverSocket;
    private boolean running;
    private JavaPlugin plugin;

    public SocketServer(JavaPlugin plugin, int port, Logger logger) {
        this.plugin = plugin;
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            logger.warning("SocketServer가 구동되었습니다!");
            startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServer() {
        new Thread(() -> {
            try {
                while (running) {
                    Socket clientSocket = serverSocket.accept();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                        String command;
                        while ((command = reader.readLine()) != null) {
                            final String cmd = command;
                            Bukkit.getScheduler().runTask(this.plugin, () ->
                                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd)
                            );
                        }
                    } finally {
                        clientSocket.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stopServer() {
        try {
            running = false;
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ServerConnectWindow {
    private JFrame frame;
    private JTextField ipTextField, portTextField;
    private JButton connectButton;

    public ServerConnectWindow() {
        frame = new JFrame("Connect to Server");
        frame.setLayout(new FlowLayout());

        ipTextField = new JTextField(15);
        portTextField = new JTextField(5);
        connectButton = new JButton("Connect");

        ActionListener connectListener = e -> connectToServer();

        ipTextField.addActionListener(connectListener);
        portTextField.addActionListener(connectListener);
        connectButton.addActionListener(connectListener);

        frame.add(new JLabel("Server IP:"));
        frame.add(ipTextField);
        frame.add(new JLabel("Port:"));
        frame.add(portTextField);
        frame.add(connectButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void connectToServer() {
        String ip = ipTextField.getText();
        int port = Integer.parseInt(portTextField.getText());

        try {
            Socket socket = new Socket(ip, port);
            new CommandWindow(socket);
            frame.dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "서버에서 ReForceOp가 실행되고 있지 않은 것 같습니다.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerConnectWindow::new);
    }
}

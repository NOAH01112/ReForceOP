import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class CommandWindow {
    private JFrame frame;
    private JTextField commandTextField;
    private JButton sendButton;
    private Socket socket;

    public CommandWindow(Socket socket) {
        this.socket = socket;

        frame = new JFrame("Send Command");
        frame.setLayout(new FlowLayout());

        commandTextField = new JTextField(20);
        sendButton = new JButton("Send");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendCommand();
            }
        });

        frame.add(commandTextField);
        frame.add(sendButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void sendCommand() {
        if (socket != null && !socket.isClosed()) {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                out.println(commandTextField.getText());
                commandTextField.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

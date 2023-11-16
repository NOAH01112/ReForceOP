import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandWindow {
    private JFrame frame;
    private JTextField commandTextField;
    private JButton sendButton;
    private JTextArea historyTextArea;
    private Socket socket;
    private PrintWriter writer;
    private SimpleDateFormat dateFormat;

    public CommandWindow(Socket socket) {
        this.socket = socket;
        if (!initializeWriter()) {
            JOptionPane.showMessageDialog(null, "서버와의 연결에 실패했습니다.");
            return;
        }

        dateFormat = new SimpleDateFormat("[HH:mm:ss]");

        frame = new JFrame("Send Command");
        frame.setLayout(new BorderLayout());

        commandTextField = new JTextField();
        commandTextField.addActionListener(e -> sendCommand());

        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendCommand());

        historyTextArea = new JTextArea(10, 30);
        historyTextArea.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(commandTextField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        frame.add(new JScrollPane(historyTextArea), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeResources();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private boolean initializeWriter() {
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void sendCommand() {
        String command = commandTextField.getText();
        if (!command.isEmpty()) {
            writer.println(command);
            updateHistory(command);
            commandTextField.setText("");
        }
    }

    private void updateHistory(String command) {
        String timestamp = dateFormat.format(new Date());
        historyTextArea.append(timestamp + " " + command + "\n");
    }

    private void closeResources() {
        if (writer != null) {
            writer.close();
        }
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

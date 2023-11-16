import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class CommandWindow {
    private JFrame frame;
    private JTextField commandTextField;
    private JTextArea historyTextArea;
    private Socket socket;
    private PrintWriter writer;

    public CommandWindow(Socket socket) {
        this.socket = socket;
        if (!initializeWriter()) {
            JOptionPane.showMessageDialog(null, "서버와의 연결에 실패했습니다.");
            return;
        }

        frame = new JFrame("Send Command");
        frame.setLayout(new BorderLayout());

        commandTextField = new JTextField();
        commandTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendCommand();
            }
        });

        historyTextArea = new JTextArea(10, 30);
        historyTextArea.setEditable(false);

        frame.add(new JScrollPane(historyTextArea), BorderLayout.CENTER);
        frame.add(commandTextField, BorderLayout.SOUTH);

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
            historyTextArea.append(command + "\n");
            commandTextField.setText("");
        }
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

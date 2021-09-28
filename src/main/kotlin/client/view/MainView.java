package dekilla.core.client.view;

import dekilla.core.client.ClientManager;
import dekilla.core.container.ClientContainer;
import dekilla.core.domain.SockDto;
import dekilla.core.util.socket.WrappedSocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainView {
    private JPanel container;
    private JTextField Tf_myToken;
    private JButton Bu_send;
    private JLabel La_myToken;
    private JTextField Tf_downPath;
    private JLabel La_downPath;
    private JLabel La_upPath;
    private JTextField Tf_upPath;
    private JButton Bu_downPath;
    private JButton Bu_upPath;
    private JLabel La_targetToken;
    private JTextField Tf_targetToken;
    private JButton Bu_connect;
    private JTextArea Ta_status;
    private JScrollPane Js_status;
    private JButton bu_statusClear;
    private JLabel La_logo;

    private ClientManager clientManager;

    public MainView() {
        this.clientManager = ClientContainer.Companion.clientManager();
    }

    public void create(String token) {
        JFrame frame = new JFrame("Dekilla");
        frame.setContentPane(container);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Tf_myToken.setText(token);

        // 프레임(자바 화면) 크기
        Dimension frameSize = frame.getSize();
        // 모니터 크기
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // (모니터화면 가로 - 프레임화면 가로) / 2, (모니터화면 세로 - 프레임화면 세로) / 2
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

        WrappedSocket requesterSocket = clientManager.getWrappedSocket();

        Bu_connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SockDto request = new SockDto(
                        "CONNECT_WITH_TOKEN",
                        "#",
                        requesterSocket.getId() + "#" + Tf_targetToken.getText(),
                        null
                );
                clientManager.sendData(request);
            }
        });

        Bu_downPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.showDialog(container, null);

                File dir = chooser.getSelectedFile();
                Tf_downPath.setText(dir.getPath());

                ClientContainer.Companion.fileController().setDownloadFolder(dir);
            }
        });

        Bu_upPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.showDialog(container, null);

                File file = chooser.getSelectedFile();
                Tf_upPath.setText(file.getPath());

                ClientContainer.Companion.fileController().setCurrentFile(file);
            }
        });

        Bu_send.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (ClientContainer.Companion.fileController().isSending() == false) {
                    ClientContainer.Companion.fileController().sendPermissionRequest();
                } else {
                    JOptionPane.showMessageDialog(null, "The file is being transferred. Please try again after sending.");
                }
            }
        });

        bu_statusClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ta_status.setText("");
            }
        });
    }

    public JTextField getTf_targetToken() {
        return Tf_targetToken;
    }

    public JTextArea getTa_status() {
        return Ta_status;
    }

    public JScrollPane getJs_status() {
        return Js_status;
    }
}

package dekilla.core.client.view;

import dekilla.core.client.ClientManager;
import dekilla.core.client.handler.exception.ClientSocketExceptionHandler;
import dekilla.core.container.ClientContainer;
import dekilla.core.container.ViewContainer;
import dekilla.core.util.socket.WrappedSocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostConnectView {

    private JPanel container;
    private JLabel La_intro;
    private JTextField Tf_host;
    private JTextField Tf_port;
    private JButton Bu_connect;
    private JLabel La_host;
    private JLabel La_port;
    private JLabel La_logo;

    ClientManager clientManager = ClientContainer.Companion.clientManager();
    private ClientSocketExceptionHandler clientSocketExceptionHandler = ClientContainer.Companion.clientSocketExceptionHandler();

    public void create() {
        JFrame frame = new JFrame("Dekilla");
        frame.setContentPane(container);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
//        ImageIcon imageIcon = new ImageIcon(this.getClass().getClassLoader().getResource("DekillaLogoSmall.png"));
//        La_logo.setIcon(imageIcon);
//        La_logo.setHorizontalTextPosition(JLabel.RIGHT);
//        La_logo.setVerticalTextPosition(JLabel.CENTER);

        // 프레임(자바 화면) 크기
        Dimension frameSize = frame.getSize();
        // 모니터 크기
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // (모니터화면 가로 - 프레임화면 가로) / 2, (모니터화면 세로 - 프레임화면 세로) / 2
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

        frame.setResizable(false);

        Bu_connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ClientManager.Companion.setIp(Tf_host.getText());
                    ClientManager.Companion.setPort(Integer.parseInt(Tf_port.getText()));

                    WrappedSocket wrappedSocket = clientManager.connect();
                    clientManager.processing();
                    MainView mainView = ViewContainer.Companion.mainView();
                    mainView.create(wrappedSocket.getId());
                    frame.dispose();
                } catch (NumberFormatException nfex) {
                    clientSocketExceptionHandler.ipInputNotNumber();
                } catch (Exception ex) {
                }
            }
        });
    }
}

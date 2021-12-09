package hotel_manage;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
 
public class ChatServer extends JFrame {
    private JTextField textField;
    JTextArea textArea;
    JLabel lblStatus;
    private ServerSocket serverSocket;
    private Socket socket;
 
    private DataInputStream dis;
    private DataOutputStream dos;
 
    private boolean connectStatus;// Ŭ���̾�Ʈ ���� ���� ����
    private boolean stopSignal;// ������ ���� ��ȣ ����
 
    public ChatServer() {
        showFrame();
        startService();// ä�� ���� ����
    }
 
    public void showFrame() {
        setTitle("1:1 ä�� [����Ʈ]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 400, 500, 300);
        getContentPane().setLayout(null);
 
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.LIGHT_GRAY);
        getContentPane().add(textArea);
 
        textField = new JTextField();
        textField.setBounds(0, 230, 390, 31);
        getContentPane().add(textField);
        textField.setColumns(10);
 
        JButton btnInput = new JButton("�Է�");
        btnInput.setBackground(Color.YELLOW);
        btnInput.setForeground(Color.BLACK);
        btnInput.setBounds(387, 230, 97, 31);
        getContentPane().add(btnInput);
 
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 484, 22);
        getContentPane().add(panel);
 
        lblStatus = new JLabel("Ŭ���̾�Ʈ ���� ���� : �������");
        lblStatus.setForeground(Color.RED);
        panel.add(lblStatus);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 23, 484, 208);
        getContentPane().add(scrollPane);
        scrollPane.setViewportView(textArea);
 
        textField.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
 
        setVisible(true);
 
        textField.requestFocus();
 
    }

    public void startService() {
        try {
            // ServerSocket ��ü�� �����Ͽ� ������ ��Ʈ(59876)�� ����
            serverSocket = new ServerSocket(59876);
            // Ŭ���̾�Ʈ�κ��� ������ ������ ������ ���� ���� ���
            connectStatus = false;
            while (!connectStatus) {
                textArea.append("Ŭ���̾�Ʈ ���� �����...\n");
                // ServerSocket ��ü�� accept()�޼��带 ȣ���Ͽ� ������
                // ���� �Ϸ� �� Socket ��ü ���ϵ�
                socket = serverSocket.accept();
                // ���ӵ� Ŭ���̾�Ʈ�� ���� IP �ּ� ���� ���
                textArea.append("Ŭ���̾�Ʈ�� ���� �Ͽ����ϴ�. (" + socket.getInetAddress() + ")\n");
 
                // DataInputStream ��ü ���� �� �ԷµǴ� �н����� �����ͼ� ���
                dis = new DataInputStream(socket.getInputStream());
 
                // DataOutStream ��ü ����
                dos = new DataOutputStream(socket.getOutputStream());
 
                connectStatus=true;
                textArea.append("================================ä��================================\n");
                lblStatus.setText("Ŭ���̾�Ʈ ���� ���� : �����");
                dos.writeBoolean(true);
            }
            
            //Runnable �������̽��� �����Ͽ� Thread ��ü�� ���� �� start() �޼��� ȣ��
            //�ٷ� ������ ������� ó����
            new Thread(new Runnable() {
                
                @Override
                public void run() {
                    //Ŭ���̾�Ʈ�κ��� ���۵Ǵ� �޼����� ó���� receiveMessage() �޼��� ȣ��
                    receiveMessage();
                }
            }).start();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
    
    public void receiveMessage() {
        //��Ƽ ���������� �޼��� ���� ó�� �۾� ����
        //boolean Ÿ�� ������� stopSignal �� false �� ���� �ݺ��ؼ� �޼��� ����
        
        try {
            while(!stopSignal) {
                //Ŭ���̾�Ʈ�� writeUTF() �޼���� ������ �޼����� �Է¹ޱ�
                textArea.append("���� : "+dis.readUTF()+"\n");
            }
            //stopSignal �� true �� �Ǹ� �޼��� ���� ����ǹǷ� dis�� socket ��ȯ
            dis.close();
            socket.close();
        }catch(EOFException e){
            //������ ���� ������ ��� ������ ���ŵǸ鼭 ȣ��Ǵ� ����
            textArea.append("Ŭ���̾�Ʈ ������ �����Ǿ����ϴ�.\n");
            lblStatus.setText("Ŭ���̾�Ʈ ���� ���� : �̿���");
            connectStatus=false;
        }catch(SocketException e) {
            textArea.append("���� ������ �����Ǿ����ϴ�.\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }       
    }
    
    public void sendMessage() {
        try {
            String text = textField.getText();
            textArea.append(">> " + text + "\n");
            
            //�Էµ� �޼����� "/exit" �� ���
            if(text.equals("/exit")) {
                //textArea �� "bye" ��� ��
                //stopSignal�� true�� ���� , ��Ʈ�� ��ȯ, ���� ��ȯ
                stopSignal=true;
                dos.close();
                socket.close();
                dispose();
                //���α׷� ����
              //  System.exit(0);
            }else {
                //�Էµ� �޼����� "/exit"�� �ƴ� ���( ������ �޼����� ���)
                //Ŭ���̾�Ʈ���� �޼��� ����
                dos.writeUTF(text);
                
                //�ʱ�ȭ �� Ŀ����û
                textField.setText("");
                textField.requestFocus();
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /*public static void main(String[] args) {
        new ChatServer();
    }*/
}
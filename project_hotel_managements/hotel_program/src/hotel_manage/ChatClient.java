package hotel_manage;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
 
public class ChatClient extends JFrame {
    private JTextField textField;
    JTextArea textArea;
    JLabel lblStatus;
    private Socket socket;
 
    private DataInputStream REQ_CHAT;
    private DataOutputStream ACK_CHAT;
 
    private boolean connectStatus;// Ŭ���̾�Ʈ ���� ���� ����
    private boolean stopSignal;// ������ ���� ��ȣ ����
 
    public ChatClient() {
        showFrame();
    }
 
    public void showFrame() {
        setTitle("1:1 ä�� [����]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(1200, 400, 500, 300);
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
        btnInput.setForeground(Color.BLACK);
        btnInput.setBackground(Color.YELLOW);
        btnInput.setBounds(387, 230, 97, 31);
        getContentPane().add(btnInput);
 
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 484, 22);
        getContentPane().add(panel);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 23, 484, 208);
        getContentPane().add(scrollPane);
        scrollPane.setViewportView(textArea);
        
 
        lblStatus = new JLabel("���� ���� ���� : �̿���");
        lblStatus.setForeground(Color.RED);
        panel.add(lblStatus);
 
        textField.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
 
        setVisible(true);
        
 
        startService();
        
        textArea.requestFocus();// �ؽ�Ʈ�ʵ忡 Ŀ����û
 
    }
    public void startService() {
//        �������ӽõ�
        String password= prepareConnect();
        boolean connectResult=connectServer(password);
        
        //�������� ��� �Ǻ��Ͽ� �н����� ��ġ�� ������ �н����� �Է� �� ���� �õ�
        while(!connectResult) {
            
            password=prepareConnect();
            connectResult=connectServer(password);
            
        }
        textArea.append("================================ä��================================\n");
        
        //��Ƽ������ �����Ͽ� receiveMessage() �޼��� ȣ��
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                receiveMessage();
            }
        }).start();
        
        
    }
    
 
   public String prepareConnect() {
        // ���������� ���� �н����� �Է�
        // �ƹ��Է¾��� Ȯ���� ������ ���("" �Է½�)
        // ���̾�α׸� ����Ͽ� "�н����� �Է� �ʼ�!" ��� �� �ٽ� �Է� �ޱ�
        // ��, password�� null�� �ƴ� ���� �Ǻ� ����
       String password = "1234";
          
        return password;
    }
 
    public boolean connectServer(String password) {
 
        try {
            textArea.append("������ ������ �õ� ���Դϴ�....\n");
 
            // socket ��ü�� �����Ͽ� IP �ּҿ� ��Ʈ��ȣ ����->���� ���ӽõ�
            socket = new Socket("localhost", 59876);
 
            // DataOutputStream ��ü ���� �� �ԷµǴ� �н����� �Ѱ��ֱ�
 
            ACK_CHAT = new DataOutputStream(socket.getOutputStream());
            ///dos.writeUTF(password);
 
            textArea.append("���� ���� �Ϸ�\n");
 
            // DataInputStream ��ü ���� �� ���޹��� ���ӿ�û ��� ���
            REQ_CHAT = new DataInputStream(socket.getInputStream());
            boolean result = REQ_CHAT.readBoolean();
            
            //���޹��� ���ӿ�û ��� �Ǻ�
            if(!result) {
                socket.close();//���� ��ȯ
                return false;
            }else {
                lblStatus.setText("���� ���� ���� : �����\n");
                connectStatus=true;
            }
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return true;
    }
    
    public void receiveMessage() {
        //��Ƽ ���������� �޼��� ���� ó�� �۾� ����
        //boolean Ÿ�� ������� stopSignal �� false �� ���� �ݺ��ؼ� �޼��� ����
        
        try {
            while(!stopSignal) {
                //Ŭ���̾�Ʈ�� writeUTF() �޼���� ������ �޼����� �Է¹ޱ�
                textArea.append("������ : "+REQ_CHAT.readUTF()+"\n");
            }
            //stopSignal �� true �� �Ǹ� �޼��� ���� ����ǹǷ� REQ_CHAT�� socket ��ȯ
            REQ_CHAT.close();
            socket.close();
        }catch(EOFException e){
            //������ ���� ������ ��� ������ ���ŵǸ鼭 ȣ��Ǵ� ����
            textArea.append("���� ������ �����Ǿ����ϴ�.\n");
            lblStatus.setText("���� ���� ���� : �̿���");
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
                //stopSignal�� true�� ���� , ��Ʈ�� ��ȯ, ���� ��ȯ
                stopSignal=true;
                ACK_CHAT.close();
                socket.close();
                
                //���α׷� ����
                System.exit(0);
            }else {
                //�Էµ� �޼����� "/exit"�� �ƴ� ���( ������ �޼����� ���)
                //Ŭ���̾�Ʈ���� �޼��� ����
               ACK_CHAT.writeUTF(text);
                
                //�ʱ�ȭ �� Ŀ����û
                textField.setText("");
                textField.requestFocus();
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
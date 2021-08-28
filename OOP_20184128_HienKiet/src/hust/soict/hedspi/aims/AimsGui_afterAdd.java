package hust.soict.hedspi.aims;
/**
* @author hienkietleog
*
*/
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import hust.soict.hedspi.aims.media.*;

public class AimsGui_afterAdd extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private JLabel lblSuccess;
    @SuppressWarnings("unused")
    private JPanel panel;
    
    public AimsGui_afterAdd(Media media) {
        setResizable(false);
        setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Information");
        
        getContentPane().setLayout(null);
        
        lblSuccess = new JLabel("Adding new item succeed!");
        lblSuccess.setHorizontalAlignment(SwingConstants.CENTER);
        lblSuccess.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblSuccess.setBounds(0, 20, 236, 31);
        getContentPane().add(lblSuccess);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 61, 246, 31);
        getContentPane().add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JButton btnOK = new JButton("OK");
        panel.add(btnOK);
        
        JPanel blankPanel = new JPanel();
        panel.add(blankPanel);
        
        JButton btnPlay = new JButton("Play CD/DVD");
        panel.add(btnPlay);
        if (media instanceof Book) {
            btnPlay.setVisible(false);
        }
        
        JPanel textPanel = new JPanel();
        textPanel.setBounds(10, 10, 214, 91);
        getContentPane().add(textPanel);
        textPanel.setLayout(null);
        textPanel.setVisible(false);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 214, 91);
        textPanel.add(scrollPane);
        
        JTextArea textArea = new JTextArea();
        scrollPane.setViewportView(textArea);
        textArea.setEditable(false);
        
        JFrame thisFrame = this;
        btnOK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                thisFrame.dispose();
            }
        });
        
        // add Listener
        btnPlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblSuccess.setVisible(false);
                panel.setVisible(false);
                textPanel.setVisible(true);
                
                if (media == null) {
                    return;
                }
                
                // play after add
                if (media instanceof DigitalVideoDisc) {
                    try {
                        textArea.setText(((DigitalVideoDisc)media).play());
                    } catch (PlayerException e1) {
                        e1.printStackTrace();
                        textArea.setText("ERROR: Invalid DVD length!\nDVD cannot be played.");
                    }
                } else { // CD
                    try {
                        textArea.setText(((CompactDisc)media).play());
                    } catch (PlayerException e2) {
                        e2.printStackTrace();
                        textArea.setText("ERROR: Invalid CD length!\nCD cannot be played.");
                    }
                }
            }
        });

        this.setBounds(365, 230, 250, 150);
        this.setVisible(true);
    }
    
}

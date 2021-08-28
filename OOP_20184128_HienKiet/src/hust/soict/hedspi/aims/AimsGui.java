package hust.soict.hedspi.aims;

/**
* @author hienkietleog
*
*/


import java.awt.*;       // Using AWT layouts
import java.awt.event.*; // Using AWT event classes and listener interfaces

import javax.naming.LimitExceededException;
import javax.swing.*;    // Using Swing components and containers
import javax.swing.border.Border;

import java.util.ArrayList;
import java.util.List;

import hust.soict.hedspi.aims.media.*;
import hust.soict.hedspi.aims.order.Order;

public class AimsGui extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    Aims aims = new Aims();
    
    // label
    JLabel lblDate = new JLabel("Date:");
    JLabel lblToday = new JLabel();
    JLabel lblOrderId = new JLabel("Order ID:");
    JLabel lblTotalCost = new JLabel("Total Cost:");
    JLabel lblMessage = new JLabel("Message:");
    
    // text area
    JTextArea txtTotalCost = new JTextArea("0.0");
    JTextArea txtMessage = new JTextArea("Welcome to Aims!");
    
    // combo box: order id list
    JComboBox<Integer> cbListOrderId = new JComboBox<Integer>();
    
    // list: item list
    List<DefaultListModel<String>> itemLists = new ArrayList<DefaultListModel<String>>(Order.MAX_LIMITED_ORDERS);
    JList<String> showedItemList = new JList<String>();

    // button: create order, add item, delete item
    JButton btnCreate = new JButton("Create order");
    JButton btnAdd = new JButton("Add item");
    JButton btnDel = new JButton("Delete item");
    JButton btnLucky = new JButton("Get lucky");
    JButton btnLuckSetting = new JButton("Luck Setting");
    
    // current order ID
    int orderId = -1;
    
    // lucky threshold
    int minItem = Order.LUCKY_MIN_NUMBER_OF_ITEMS;
    float minTotalCost = Order.LUCKY_MIN_TOTAL_COST;
    
    // constructor
    public AimsGui() {
        // 1. Init the orders
        for (int i = 0; i < Order.MAX_LIMITED_ORDERS; i++) {
            itemLists.add(new DefaultListModel<String>()); 
        }
        
        // 2. Init the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Aims");  
        Container contentPane = this.getContentPane();  
        
        // 3. Add layout
        GridBagLayout layout = new GridBagLayout();  
        GridBagConstraints gbc = new GridBagConstraints();
        contentPane.setLayout(layout);
        
        // 4. Add components
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // add date
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(lblDate, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        this.add(lblToday, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        this.add(lblOrderId, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.ipadx = 10;
        this.add(cbListOrderId, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(lblTotalCost, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(txtTotalCost, gbc);
        txtTotalCost.setEditable(false);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        txtTotalCost.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        this.add(btnCreate, gbc);
        
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JPanel(), gbc); 
        
        // add the JList into a JScrollPane, the JScrollPane into a JPanel
        showedItemList.setFont(new Font("Arial", Font.PLAIN, 14));
        showedItemList.setVisibleRowCount(10);
        showedItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        showedItemList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane scrollList = new JScrollPane(showedItemList);
        scrollList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollList.setPreferredSize(new Dimension(350, 250));
        JPanel listPanel = new JPanel();
        listPanel.add(scrollList);
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipadx = 350;
        gbc.ipady = 250;
        gbc.gridwidth = 4;
        gbc.gridheight = 3;
        this.add(listPanel, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(btnAdd, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 4;
        gbc.gridy = 4;
        this.add(btnDel, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 4;
        gbc.gridy = 5;
        this.add(btnLucky, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 4;
        gbc.gridy = 6;
        this.add(btnLuckSetting, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        this.add(lblMessage, gbc);
        
        txtMessage.setEditable(false);
        JScrollPane scrollMessage = new JScrollPane(txtMessage);
        scrollMessage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollMessage.setPreferredSize(new Dimension(270, 50));
        JPanel messagePanel = new JPanel();
        messagePanel.add(scrollMessage);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.ipadx = 270;
        gbc.ipady = 50;
        this.add(messagePanel, gbc);
        
        // 5. Add listeners
        cbListOrderId.addActionListener (new ActionListener() {
            // change order displayed when changing order id
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbListOrderId.getSelectedIndex() != -1) {
                    orderId = cbListOrderId.getSelectedIndex();
                    showedItemList.setModel(itemLists.get(orderId));
                    updateTotalCost();
                }
            }
        });
        
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (cbListOrderId.getItemCount() == Order.MAX_LIMITED_ORDERS) {
                    notificate("Warning: The number of order reached max!\n" +
                               "Cannot create new order.");
                }
                
                if (aims.getOrders().size() < Order.MAX_LIMITED_ORDERS) {
                    // create new order
                    try {
                        aims.createOrder();
                    } catch (LimitExceededException e) {
                        e.printStackTrace();
                        notificate("Something wrong happended!\n" +
                                   "We will try to fix this in the latest verion!\n");
                    }
                    cbListOrderId.addItem(aims.getOrders().size() - 1);
                    
                    // display the date
                    lblToday.setText(aims.getOrders().get(orderId).getDateOrdered().toString());
                    
                    // noti
                    notificate("Creating new order with id = " + (aims.getOrders().size() - 1) + " succeeded!");
                }
            }
        });
        
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (cbListOrderId.getSelectedIndex() == -1) {
                    notificate("Warning: Please create a new order first");
                    return;
                }
                
                if (itemLists.get(orderId).size() == Order.MAX_NUMBER_ORDERED) {
                    notificate("Warning: The number of items in order " + orderId + " reached max!\n" +
                               "Please purchase or remove an item before adding more item to Order.");
                    return;
                }
                
                // add item to the list
                @SuppressWarnings("unused")
                AimsGui_addItem addItemFrame = new AimsGui_addItem(aims,
                                                                     itemLists.get(orderId),
                                                                     orderId,
                                                                     txtTotalCost,
                                                                     txtMessage);
                // noti in AimsGuiAddItem
            }
        });
        
        btnDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (cbListOrderId.getSelectedIndex() == -1) {
                    notificate("Warning: Please create a new order first");
                    return;
                }
                
                if (itemLists.get(orderId).size() == 0) {
                    notificate("Warning: Order " + orderId + " is empty!\n" +
                               "Please add an item first");
                    return;
                }
                
                if (showedItemList.getSelectedIndex() == -1) {
                    notificate("Warning: Please select an item first");
                    return;
                }
                
                // delete the item
                Media removedMedia = null;
                try {
                    removedMedia = aims.deleteItemById(orderId, showedItemList.getSelectedIndex());
                    itemLists.get(orderId).remove(showedItemList.getSelectedIndex());
                    
                    // update total cost
                    updateTotalCost();
                    
                    // noti
                    notificate("Item \"" + removedMedia.getTitle() + "\" has been removed from Order " + orderId + ".\n" +
                               "The number of items in the order now is " + aims.getOrders().get(orderId).getItemsOrdered().size() + ".");
                } catch (IllegalStateException | IllegalArgumentException e) {
                    e.printStackTrace();
                    notificate("Something wrong happended!\n" +
                               "We will try to fix this in the latest verion!\n");
                }
            }
        });
        
        btnLucky.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (cbListOrderId.getSelectedIndex() == -1) {
                    notificate("Warning: Please create a new order first");
                    return;
                }
                
                if (aims.getOrders().get(orderId).totalCost() < minTotalCost) {
                    notificate("The Total Cost of your order needs to have at least " + minTotalCost + 
                            "$ to get a lucky item!");
                    return;
                }
                
                if (aims.getOrders().get(orderId).getItemsOrdered().size() < minItem) {
                    notificate("Your order needs to have at least " + minItem + 
                            " items to get a lucky item!");
                    return;
                }
                
                // init the Lucky Item frame
                JFrame luckyFrame = new JFrame();
                luckyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
                JTextArea txtLucky = new JTextArea();
                txtLucky.setEditable(false);
                txtLucky.setAlignmentX(CENTER_ALIGNMENT);
                txtLucky.setAlignmentY(CENTER_ALIGNMENT);
                float luckyItemValue = aims.getOrders().get(orderId).getALuckyItem(minItem, minTotalCost);
                if (luckyItemValue == 0.00f) {
                    luckyFrame.setTitle("Next time then!");
                    txtLucky.setText(" Better luck next time!");
                } else {
                    luckyFrame.setTitle("Congratulation!");
                    txtLucky.setText(" You won a lucky item that costs " + luckyItemValue + "$ !");
                }
                
                luckyFrame.getContentPane().add(txtLucky);
                luckyFrame.setBounds(365, 230, 275, 75);
                luckyFrame.setVisible(true);
            }
        });
        
        btnLuckSetting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // init frame
                JFrame settingFrame = new JFrame();
                settingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                settingFrame.setAlwaysOnTop(true);
                settingFrame.setTitle("Luck Setting");
                 
                // add layout
                Container contentPane1 = settingFrame.getContentPane(); 
                GridBagLayout layout1 = new GridBagLayout();  
                GridBagConstraints gbc1 = new GridBagConstraints();
                contentPane1.setLayout(layout1);
                
                // add components
                gbc1.insets = new Insets(5, 5, 5, 5);
                
                gbc1.fill = GridBagConstraints.HORIZONTAL;
                gbc1.gridx = 0;
                gbc1.gridy = 0;
                gbc1.ipady = 0;
                settingFrame.add(new JLabel("Min number of item:"), gbc1);
                
                JTextField txtMinItem = new JTextField();
                txtMinItem.setText(String.valueOf(minItem));
                gbc1.fill = GridBagConstraints.HORIZONTAL;
                gbc1.gridx = 1;
                gbc1.gridy = 0;
                gbc1.ipady = 20;
                settingFrame.add(txtMinItem, gbc1);
                
                gbc1.fill = GridBagConstraints.HORIZONTAL;
                gbc1.gridx = 0;
                gbc1.gridy = 1;
                gbc1.ipady = 0;
                settingFrame.add(new JLabel("Min total cost:"), gbc1);
                
                JTextField txtMinTotalCost = new JTextField();
                txtMinTotalCost.setText(String.valueOf(minTotalCost));
                gbc1.fill = GridBagConstraints.HORIZONTAL;
                gbc1.gridx = 1;
                gbc1.gridy = 1;
                gbc1.ipady = 20;
                settingFrame.add(txtMinTotalCost, gbc1);
                
                JLabel lblWarning = new JLabel(" ");
                gbc1.fill = GridBagConstraints.HORIZONTAL;
                gbc1.gridx = 0;
                gbc1.gridy = 2;
                gbc1.ipady = 0;
                gbc1.gridwidth = 2;
                settingFrame.add(lblWarning, gbc1);
                
                JButton btnSave = new JButton("Save");
                gbc1.fill = GridBagConstraints.HORIZONTAL;
                gbc1.gridx = 0;
                gbc1.gridy = 3;
                gbc1.ipady = 0;
                settingFrame.add(btnSave, gbc1);
                
                settingFrame.setBounds(365, 230, 320, 200);
                settingFrame.setVisible(true);
                
                btnSave.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        int inputedMinItem = Integer.valueOf(txtMinItem.getText().trim());
                        float inputedMinTotalCost = Float.valueOf(txtMinTotalCost.getText().trim());
                        
                        if (inputedMinTotalCost < Order.LUCKY_MIN_TOTAL_COST) {
                            lblWarning.setText("The min Total Cost must be greater than " + Order.LUCKY_MIN_TOTAL_COST);
                            return;
                        }
                        
                        minItem = inputedMinItem;
                        minTotalCost = inputedMinTotalCost;
                        
                        // close luck setting window
                        settingFrame.dispose();
                        
                        // noti after saving
                        notificate("Luck Setting saved!");
                    }
                });
            }
        });
        
        // 6. Display the window 
        this.setBounds(250, 50, 620, 540);
        this.setVisible(true);
    }
    
    public void updateTotalCost() {
        if (aims.getOrders().get(orderId).getItemsOrdered().size() == 0) {
            txtTotalCost.setText("0.0");
        } else {
            txtTotalCost.setText(String.valueOf(aims.getOrders().get(orderId).totalCost()));
        }
    }
    
    public void notificate(String msg) {
        txtMessage.setText(msg);
    }
    
    // main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AimsGui(); // Let the constructor do the job
            }
        });
    }

}

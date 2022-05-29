import GetLinkPackaces.GetLinks;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import org.jsoup.Jsoup;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class main_screen extends JFrame{
    public JPanel main_panel;
    private JTextField enterURLTextField;
    private JButton clickButton;
    private JLabel errorMessageLabel;
    private JList<String> list1;
    private JComboBox comboBox1;
    private JScrollPane main_scroll_pane;
    private JFileChooser jFileChooser;

    public main_screen(){
        GetLinks getLinks = new GetLinks();
        String[] defaultComboBoxList = {"Trendyol"};
        DefaultComboBoxModel<String> comboBoxList = new DefaultComboBoxModel<String>(defaultComboBoxList);

        add(main_panel);
        setSize(400 , 400);
        setTitle("Read Link In Site");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        errorMessageLabel.setVisible(false);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        comboBox1.setModel(comboBoxList);
        list1.setModel(listModel);

        JScrollBar jScrollPane =  main_scroll_pane.getVerticalScrollBar();
        jScrollPane.setValue(jScrollPane.getMaximum());

        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enterUrlGetText = enterURLTextField.getText();
                if(enterUrlGetText.equals("")){
                    errorMessageManagement("Marka girilmedi" ,  true);
                }else{
                    errorMessageLabel.setVisible(false);
                    jFileChooser = new JFileChooser();
                    jFileChooser.setDialogTitle("Select File");
                    jFileChooser.setCurrentDirectory(new java.io.File("."));
                    jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    jFileChooser.setAcceptAllFileFilterUsed(false);
                    if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        if(!jFileChooser.getSelectedFile().isFile()){
                            getLinks.getLinkInSites(comboBox1.getSelectedItem().toString().trim() , enterUrlGetText.toLowerCase().trim() , listModel , main_scroll_pane , jFileChooser);
                        }
                    }
                }
            }
        });
    }

    public void errorMessageManagement(String text , boolean visibility){
        if(visibility){
            errorMessageLabel.setVisible(visibility);
            errorMessageLabel.setText(text);
        }else{
            errorMessageLabel.setVisible(visibility);
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

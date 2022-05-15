import GetLinkPackaces.GetLinks;
import org.jsoup.Jsoup;

import javax.swing.*;
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

    public main_screen(){
        GetLinks getLinks = new GetLinks();
        ArrayList<String> checkCertificate = new ArrayList<>(Arrays.asList("https://" , "http://"));
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
                    getLinks.getLinkInSites(comboBox1.getSelectedItem().toString() , enterUrlGetText.toLowerCase() , listModel , main_scroll_pane);
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

}

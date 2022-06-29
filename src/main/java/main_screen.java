import GetLinkPackaces.GetLinks;
import Util.ExcelTitles;
import Util.FileNameGenerator;
import Util.WriteExcelFile;
import org.apache.poi.ss.usermodel.Cell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main_screen extends JFrame{
    public JPanel main_panel;
    private JTextField enterURLTextField;
    private JButton clickButton;
    private JLabel errorMessageLabel;
    private JList<String> list1;
    private JComboBox comboBox1;
    private JScrollPane main_scroll_pane;
    private JButton read_button;
    private JFileChooser jFileChooser;

    public main_screen(){
        Pattern pattern = Pattern.compile("[A-Z+a-z]");
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
                Matcher matcher = pattern.matcher(enterUrlGetText);
                if(enterUrlGetText.equals("")){
                    errorMessageManagement("Marka girilmedi" ,  true);
                }else if(!matcher.find()){
                    errorMessageManagement("Yalnızca [A-Z] [a-z] girişi yapılabilir", true);
                } else{
                    errorMessageLabel.setVisible(false);
                    jFileChooser = new JFileChooser();
                    jFileChooser.setDialogTitle("Select File");
                    jFileChooser.setCurrentDirectory(new java.io.File("."));
                    jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    jFileChooser.setAcceptAllFileFilterUsed(false);
                    if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        if(!jFileChooser.getSelectedFile().isFile()){
                            File files = new FileNameGenerator().writeFileSelectedLocation(enterUrlGetText+".xlsx" , jFileChooser);
                            if(!files.exists()) files.exists();
                            getLinks.getLinkInSites(comboBox1.getSelectedItem().toString().trim() , enterUrlGetText.toLowerCase().trim() , listModel , main_scroll_pane , jFileChooser , enterUrlGetText);
                        }
                    }
                }
            }
        });

        WriteExcelFile writeExcelFile = new WriteExcelFile();
        Map<String , Object[]> stringMap = new TreeMap<>();
        stringMap.put("1" , new ExcelTitles().TRENDYOL_LARGE_TITLE);
        stringMap.put("2" , new Object[]{"product_name1" , "product_code" , "product_barcode" , "product_selling_price" , "product_discounted_price" , "product_original_price" , "product_color" ,"product_body" , "product_body_barcode","product_body_code" , "product_body_price" , "product_stock_status" , "product_description" , "product_attributes"});
        stringMap.put("3" , new Object[]{"product_name2" , "product_code" , "product_barcode" , "product_selling_price" , "product_discounted_price" , "product_original_price" , "product_color" ,"product_body" , "product_body_barcode","product_body_code" , "product_body_price" , "product_stock_status" , "product_description" , "product_attributes"});
        stringMap.put("4" , new Object[]{"product_name3" , "product_code" , "product_barcode" , "product_selling_price" , "product_discounted_price" , "product_original_price" , "product_color" ,"product_body" , "product_body_barcode","product_body_code" , "product_body_price" , "product_stock_status" , "product_description" , "product_attributes"});
        stringMap.put("5" , new Object[]{"product_name4" , "product_code" , "product_barcode" , "product_selling_price" , "product_discounted_price" , "product_original_price" , "product_color" ,"product_body" , "product_body_barcode","product_body_code" , "product_body_price" , "product_stock_status" , "product_description" , "product_attributes"});
        stringMap.put("6" , new Object[]{"product_name5" , "product_code" , "product_barcode" , "product_selling_price" , "product_discounted_price" , "product_original_price" , "product_color" ,"product_body" , "product_body_barcode","product_body_code" , "product_body_price" , "product_stock_status" , "product_description" , "product_attributes"});
        stringMap.put("7" , new Object[]{"product_name6" , "product_code" , "product_barcode" , "product_selling_price" , "product_discounted_price" , "product_original_price" , "product_color" ,"product_body" , "product_body_barcode","product_body_code" , "product_body_price" , "product_stock_status" , "product_description" , "product_attributes"});

        List<String> stringList = new ArrayList<>();
        final File[] file = {null};
        List<String> o = new ArrayList<>();
        read_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle("Select File");
                jFileChooser.setCurrentDirectory(new java.io.File("."));
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jFileChooser.setAcceptAllFileFilterUsed(false);
                if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    if(!jFileChooser.getSelectedFile().isFile()){
                        File files = new FileNameGenerator().writeFileSelectedLocation(enterURLTextField.getText()+".xlsx" , jFileChooser);
                        if(!files.exists()) {
                            files.exists();
                        }
                        file[0] = files;

                        int size = writeExcelFile.getExcelFileRowSize(files) ;
                        int columnSize = writeExcelFile.getExcelFileColumnSize(files);
                        Map<String , Object[]> stringList = writeExcelFile.readExcelFile(files);
                        if(stringList.isEmpty() || stringList == null){
                            writeExcelFile.writeExcelFile(files , enterURLTextField.getText() , stringMap);
                        }else{
                            stringList.forEach((s, objects) -> {
                                System.out.println(s);
                                for ( Object o1: objects) {
                                    System.out.println(o1);
                                }
                            });
                        }
                    }
                }
               // writeExcelFile.writeExcelFileCustom(file[0] , 10 ,o);
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

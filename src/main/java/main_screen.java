import GetLinkPackaces.GetLinks;
import Util.ExcelTitles;
import Util.FileNameGenerator;
import Util.WriteExcelFile;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.Cell;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main_screen extends JFrame {
    public JPanel main_panel;
    private JTextField enterURLTextField;
    private JButton clickButton;
    private JLabel errorMessageLabel;
    private JList<String> list1;
    private JComboBox comboBox1;
    private JScrollPane main_scroll_pane;
    private JButton read_button;
    private JLabel counter;
    private JFileChooser jFileChooser;

    public main_screen() {
        WebDriverManager.chromedriver().useBetaVersions().setup();
        Pattern pattern = Pattern.compile("[A-Z+a-z]");
        GetLinks getLinks = new GetLinks();
        String[] defaultComboBoxList = {"Trendyol"};
        DefaultComboBoxModel<String> comboBoxList = new DefaultComboBoxModel<String>(defaultComboBoxList);

        add(main_panel);
        setSize(400, 400);
        setTitle("Read Link In Site");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        errorMessageLabel.setVisible(false);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        comboBox1.setModel(comboBoxList);
        list1.setModel(listModel);

        JScrollBar jScrollPane = main_scroll_pane.getVerticalScrollBar();
        jScrollPane.setValue(jScrollPane.getMaximum());

        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enterUrlGetText = enterURLTextField.getText();
                Matcher matcher = pattern.matcher(enterUrlGetText);
                if (enterUrlGetText.equals("")) {
                    errorMessageManagement("Marka girilmedi", true);
                } else if (!matcher.find()) {
                    errorMessageManagement("Yalnızca [A-Z] [a-z] girişi yapılabilir", true);
                } else {
                    errorMessageLabel.setVisible(false);
                    jFileChooser = new JFileChooser();
                    jFileChooser.setDialogTitle("Select File");
                    jFileChooser.setCurrentDirectory(new File("."));
                    jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    jFileChooser.setAcceptAllFileFilterUsed(false);
                    if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        if (!jFileChooser.getSelectedFile().isFile()) {
                            File files = new FileNameGenerator().writeFileSelectedLocation(enterUrlGetText + ".xlsx", jFileChooser);
                            if (!files.exists()) files.exists();
                            getLinks.getLinkInSites(comboBox1.getSelectedItem().toString().trim(), enterUrlGetText.toLowerCase().trim(), listModel, main_scroll_pane, jFileChooser, enterUrlGetText, counter);
                        }
                    }
                }
            }
        });
    }

    public void errorMessageManagement(String text, boolean visibility) {
        if (visibility) {
            errorMessageLabel.setVisible(visibility);
            errorMessageLabel.setText(text);
        } else {
            errorMessageLabel.setVisible(visibility);
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        main_panel = new JPanel();
        main_panel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        enterURLTextField = new JTextField();
        enterURLTextField.setFocusAccelerator('A');
        enterURLTextField.setText("");
        enterURLTextField.setToolTipText("");
        main_panel.add(enterURLTextField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        clickButton = new JButton();
        clickButton.setText("Read");
        main_panel.add(clickButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        errorMessageLabel = new JLabel();
        Font errorMessageLabelFont = this.$$$getFont$$$(null, -1, 20, errorMessageLabel.getFont());
        if (errorMessageLabelFont != null) errorMessageLabel.setFont(errorMessageLabelFont);
        errorMessageLabel.setForeground(new Color(-4521728));
        errorMessageLabel.setText("Error message");
        main_panel.add(errorMessageLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        comboBox1.setModel(defaultComboBoxModel1);
        main_panel.add(comboBox1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        main_scroll_pane = new JScrollPane();
        main_panel.add(main_scroll_pane, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        list1 = new JList();
        list1.setSelectionMode(0);
        main_scroll_pane.setViewportView(list1);
        counter = new JLabel();
        counter.setHorizontalTextPosition(0);
        counter.setText("0");
        main_panel.add(counter, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return main_panel;
    }

}

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NorthPanelOfMainPage extends JPanel implements ActionListener {
    JTextField fileLocation;
    JComboBox elementsName;
    private JButton loadButton, drawButton;
    private JLabel filePathLabel, elementNameLabel;
    private JFileChooser jFileChooser;
    private JPanel centerPanel, eastPanel, westPanel, southPanel;
    public NorthPanelOfMainPage(){
        this.setLayout(new BorderLayout());
        initialEastPanel();
        this.add(eastPanel, BorderLayout.EAST);
        initialSouthPanel();
        this.add(southPanel, BorderLayout.SOUTH);
        initialWestPanel();
        this.add(westPanel, BorderLayout.WEST);
        initialCenterPanel();
        this.add(centerPanel, BorderLayout.CENTER);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == loadButton){
            loadButtonAction();
            return;
        }
        if(actionEvent.getSource() == drawButton){
            return;
        }
    }
    private void loadButtonAction(){
        jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop" + System.getProperty("file.separator") + "Test"));
        jFileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
        jFileChooser.addChoosableFileFilter(restrict);
        if(jFileChooser.showDialog(null, "Load") == JFileChooser.APPROVE_OPTION){
            fileLocation.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            Program.getInstance().mainPage.centerPanelOfMainPage.setFileEditorText(getInputFileText(fileLocation.getText()));
            Program.getInstance().mainPage.northPanelOfMainPage.disableDrawButton();
            Program.getInstance().mainPage.centerPanelOfMainPage.disableCalculateButton();
            Program.getInstance().mainPage.southPanelOfMainPage.disableDrawCircuit();
        }
    }
    private String getInputFileText(String path){
        Path filePath = Paths.get(path);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e){
            System.out.println("path of the selection file is incorrect");
        }
        String fileText = "";
        for(String string : lines){
            fileText = fileText + string + "\n";
        }
        return fileText;
    }
    private void initialCenterPanel(){
        centerPanel = new JPanel(new BorderLayout());
        fileLocation = new JTextField("click Load to choose file");
        fileLocation.setEnabled(false);
        centerPanel.add(fileLocation, BorderLayout.CENTER);

    }
    private void initialEastPanel(){
        eastPanel = new JPanel(new BorderLayout());
        loadButton = new JButton("Load");
        loadButton.setPreferredSize(new Dimension(80, 150));
        loadButton.addActionListener(this);
        eastPanel.add(loadButton, BorderLayout.CENTER);
    }
    private void initialWestPanel(){
        westPanel = new JPanel(new BorderLayout());
        filePathLabel = new JLabel("File Path:");
        filePathLabel.setHorizontalAlignment(JTextField.CENTER);
        filePathLabel.setPreferredSize(new Dimension(90, 100));
        westPanel.add(filePathLabel, BorderLayout.CENTER);
    }
    private void initialSouthPanel(){
        southPanel = new JPanel(new BorderLayout());
        drawButton = new JButton("Draw Plot");
        drawButton.setPreferredSize(new Dimension(80, 35));
        drawButton.addActionListener(this);
        drawButton.setEnabled(false);
        southPanel.add(drawButton, BorderLayout.EAST);
        elementNameLabel = new JLabel("Choose Element:");
        elementNameLabel.setPreferredSize(new Dimension(130, 35));
        elementNameLabel.setHorizontalAlignment(JLabel.CENTER);
        southPanel.add(elementNameLabel, BorderLayout.WEST);
        elementsName = new JComboBox();
        southPanel.add(elementsName, BorderLayout.CENTER);
    }
    public void disableLoadButton(){
        loadButton.setEnabled(false);
    }
    public void disableDrawButton(){
        drawButton.setEnabled(false);
    }
    public void enableDrawButton(){
        drawButton.setEnabled(true);
    }
    public void addElementsNameToComboBox(){
        elementsName.removeAllItems();
        for(Element element : Initial.getInstance().elementMap.values()){
            elementsName.addItem(element.name);
        }
    }
}

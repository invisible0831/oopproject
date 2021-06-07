import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SouthPanelOfMainPage extends JPanel implements ActionListener {
    private JFileChooser jFileChooser;
    private JButton runButton, drawCircuit;
    JTextField folderName;
    private JLabel resultFolder;
    private JPanel centerPanel, eastPanel, westPanel, southPanel;
    SouthPanelOfMainPage(){
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
        if(actionEvent.getSource() == runButton){
            if(Program.getInstance().mainPage.northPanelOfMainPage.fileLocation.getText().equals("click Load to choose file")){
                Program.getInstance().mainPage.showErrorMessage("choose file");
                return;
            }
            addResultFolderAndRun();
            return;
        }
        if(actionEvent.getSource() == drawCircuit){
            drawCircuitAction();
            return;
        }
    }
    private void runButtonAction(){
        Initial.getInstance().resetAllParameters();
        Program.getInstance().mainPage.centerPanelOfMainPage.enableCalculateButton();
        Program.getInstance().mainPage.northPanelOfMainPage.enableDrawButton();
        Program.getInstance().mainPage.southPanelOfMainPage.enableDrawCircuit();
        try {
            Files.writeString(Paths.get(Program.getInstance().mainPage.northPanelOfMainPage.fileLocation.getText()), Program.getInstance().mainPage.centerPanelOfMainPage.getFileEditorText());
        } catch (IOException e) {

        }
        Initial.getInstance().initial(Paths.get(Program.getInstance().mainPage.northPanelOfMainPage.fileLocation.getText()));
        if(!Solve.getInstance().hasError){
            Solve.getInstance().solve();
            if(!Solve.getInstance().hasError){
                Program.getInstance().mainPage.northPanelOfMainPage.addElementsNameToComboBox();
                Initial.getInstance().addResultsToFile();
            }
        }
    }
    private void addResultFolderAndRun(){
        jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop" + System.getProperty("file.separator") + "Test"));
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        if(jFileChooser.showDialog(null, "Create") == JFileChooser.APPROVE_OPTION) {
            String folderPath = jFileChooser.getCurrentDirectory().getAbsolutePath() + "/" + Program.getInstance().mainPage.southPanelOfMainPage.folderName.getText();
            File resultFolder = new File(folderPath);
            resultFolder.mkdir();
            String resultFilePath = folderPath + "/" + Program.getInstance().mainPage.southPanelOfMainPage.folderName.getText() + ".txt";
            File textResultFile = new File(resultFilePath);
            try {
                textResultFile.createNewFile();
            } catch (IOException e) {
                System.out.println("File is incorrect");
            }
            Initial.getInstance().setOutputFilePath(Paths.get(resultFilePath));
            runButtonAction();
        }
    }
    private void drawCircuitAction(){

    }
    private void initialCenterPanel(){
        centerPanel = new JPanel(new BorderLayout());
        folderName = new JTextField("write folder name");
        centerPanel.add(folderName, BorderLayout.CENTER);
    }
    private void initialEastPanel(){
        eastPanel = new JPanel(new BorderLayout());
        runButton = new JButton("Run");
        runButton.addActionListener(this);
        runButton.setPreferredSize(new Dimension(80, 100));
        eastPanel.add(runButton, BorderLayout.CENTER);
    }
    private void initialWestPanel(){
        westPanel = new JPanel(new BorderLayout());
        resultFolder = new JLabel("Result folder:");
        resultFolder.setPreferredSize(new Dimension(90, 100));
        resultFolder.setHorizontalAlignment(JLabel.CENTER);
        westPanel.add(resultFolder, BorderLayout.CENTER);
    }
    private void initialSouthPanel(){
        southPanel = new JPanel(new BorderLayout());
        drawCircuit = new JButton("Draw Circuit");
        drawCircuit.addActionListener(this);
        drawCircuit.setPreferredSize(new Dimension(100, 35));
        drawCircuit.setEnabled(false);
        southPanel.add(drawCircuit, BorderLayout.CENTER);
    }
    public void disableRunButton(){
        runButton.setEnabled(false);
    }
    public void disableDrawCircuit(){
        drawCircuit.setEnabled(false);
    }
    public void enableDrawCircuit(){
        drawCircuit.setEnabled(true);
    }
}

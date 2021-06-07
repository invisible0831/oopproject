import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CenterPanelOfMainPage extends JPanel implements ActionListener {
    private JTextArea fileEditor;
    private JScrollPane scrollPaneFileEditor;
    private JPanel centerPanel, eastPanel;
    private JTextField voltage, positiveNode, negativeNode, time;
    private JButton calculateButton;
    CenterPanelOfMainPage(){
        this.setLayout(new BorderLayout());
        initialCenterPanel();
        this.add(centerPanel, BorderLayout.CENTER);
        initialEastPanel();
        this.add(eastPanel, BorderLayout.EAST);
    }
    private void initialCenterPanel(){
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
        fileEditor = new JTextArea();
        scrollPaneFileEditor = new JScrollPane(fileEditor);
        scrollPaneFileEditor.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneFileEditor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        centerPanel.add(scrollPaneFileEditor, BorderLayout.CENTER);
    }
    private void initialEastPanel(){
        eastPanel = new JPanel(new BorderLayout());
        JPanel upPanel = new JPanel(new GridLayout(3, 3));
        eastPanel.add(upPanel, BorderLayout.SOUTH);
        JLabel positiveNodeLabel = new JLabel("Positive Node");
        positiveNodeLabel.setHorizontalAlignment(JLabel.CENTER);
        positiveNodeLabel.setPreferredSize(new Dimension(110, 35));
        upPanel.add(positiveNodeLabel);
        JLabel negativeNodeLabel = new JLabel("Negative Node");
        negativeNodeLabel.setHorizontalAlignment(JLabel.CENTER);
        negativeNodeLabel.setPreferredSize(new Dimension(110, 35));
        upPanel.add(negativeNodeLabel);
        JLabel timeLabel = new JLabel("Time");
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setPreferredSize(new Dimension(110, 35));
        upPanel.add(timeLabel);
        positiveNode = new JTextField("");
        positiveNode.setPreferredSize(new Dimension(110, 35));
        upPanel.add(positiveNode);
        negativeNode = new JTextField("");
        negativeNode.setPreferredSize(new Dimension(110, 35));
        upPanel.add(negativeNode);
        time = new JTextField("");
        time.setPreferredSize(new Dimension(110, 40));
        upPanel.add(time);
        JLabel voltageLabel = new JLabel("Voltage:");
        voltageLabel.setHorizontalAlignment(JLabel.CENTER);
        voltageLabel.setPreferredSize(new Dimension(110,40));
        upPanel.add(voltageLabel, BorderLayout.WEST);
        voltage = new JTextField("");
        voltage.setEditable(false);
        voltage.setPreferredSize(new Dimension(110, 40));
        upPanel.add(voltage, BorderLayout.CENTER);
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        calculateButton.setPreferredSize(new Dimension(110, 35));
        calculateButton.setEnabled(false);
        upPanel.add(calculateButton, BorderLayout.EAST);
        JTextField i = new JTextField("");
        i.setEnabled(false);
        //i.setPreferredSize(new Dimension(110, 520));
        eastPanel.add(i, BorderLayout.CENTER);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == calculateButton){
            calculateButtonAction();
            return;
        }
    }
    private void calculateButtonAction(){
        //error haro yadet nare benevisi k namayehs bede baraye click kardan roye in
        int numberOfInputTimeSteps = getNumberOfInputTimeSteps();
        String[] positiveNodeStrings = getNodeResultsString(positiveNode.getText()).split(" ");
        String[] negativeNodeStrings = getNodeResultsString(negativeNode.getText()).split(" ");
        double differentialVoltage = Double.parseDouble(positiveNodeStrings[numberOfInputTimeSteps]) - Double.parseDouble(negativeNodeStrings[numberOfInputTimeSteps]);
        voltage.setText(String.format("%.5f", differentialVoltage));
    }
    private int getNumberOfInputTimeSteps(){
        // bayad dorost koni k voltage lahze akhararo doorst bede qalat mide chon double e v taqsimesh dastane kolan
        double inputTime = 0;
        try {
            inputTime = Initial.getInstance().getValue(time.getText());
        } catch (Exception e) {

        }
        int numberOfInputTimeSteps = (int) (inputTime / Solve.getInstance().getDt());
        if(((numberOfInputTimeSteps + 1) * Solve.getInstance().getDt() - inputTime) < (inputTime - numberOfInputTimeSteps * Solve.getInstance().getDt())){
            numberOfInputTimeSteps++;
        }
        return numberOfInputTimeSteps;
    }
    private String getNodeResultsString(String nodeNumber) {
        List<String> outputFileLines = new ArrayList<>();
        try {
            outputFileLines = Files.readAllLines(Initial.getInstance().getOutputFilePath());
        } catch (IOException e) {
        }
        for (int i = 0; i < outputFileLines.size(); i++) {
            String[] strings = outputFileLines.get(i).split(" ");
            if ((strings[0].equals("Node")) && (strings[1].equals(nodeNumber))) {
                return outputFileLines.get(i + 1);
            }
        }
        return "";
    }
    public void setFileEditorText(String fileText){
        fileEditor.setText(fileText);
    }
    public String getFileEditorText(){
        return fileEditor.getText();
    }
    public void disableCalculateButton(){
        calculateButton.setEnabled(false);
    }
    public void enableCalculateButton(){
        calculateButton.setEnabled(true);
    }
}

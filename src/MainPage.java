import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame {
    CenterPanelOfMainPage centerPanelOfMainPage;
    SouthPanelOfMainPage southPanelOfMainPage;
    NorthPanelOfMainPage northPanelOfMainPage;
    Dimension frameSize;
    public MainPage(){
        frameSize = new Dimension(1000,800);
        this.setSize(frameSize);
        this.setLocation(100, 100);
        this.setTitle("Main");
        this.setLayout(new BorderLayout());
        northPanelOfMainPage = new NorthPanelOfMainPage();
        northPanelOfMainPage.setPreferredSize(new Dimension(frameSize.width - 100, 70));
        this.add(northPanelOfMainPage, BorderLayout.NORTH);
        centerPanelOfMainPage = new CenterPanelOfMainPage();
        this.add(centerPanelOfMainPage, BorderLayout.CENTER);
        southPanelOfMainPage = new SouthPanelOfMainPage();
        southPanelOfMainPage.setPreferredSize(new Dimension(200, 70));
        this.add(southPanelOfMainPage, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public void disableButtons(){
        Program.getInstance().mainPage.centerPanelOfMainPage.disableCalculateButton();
        Program.getInstance().mainPage.northPanelOfMainPage.disableDrawButton();
        Program.getInstance().mainPage.southPanelOfMainPage.disableDrawCircuit();
    }
    public void showErrorMessage(String error){
        JOptionPane.showMessageDialog(Program.getInstance().mainPage, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

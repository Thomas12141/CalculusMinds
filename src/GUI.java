import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.border.*;

public class GUI implements ActionListener {
    JFrame frame;
    JPanel panel;
    JLabel labelInput, labelOutput;
    JTextField funcOutput, funcInput;
    JButton buttonCalc, buttonClear;
    Font font = new Font("Open Sans Pro", Font.ITALIC, 20);
    Font labelFont = new Font("Open Sans Pro", Font.ITALIC, 15);

    //Konstruktor
    public GUI(){
        frame = new JFrame("CalculusMinds                     Derivation and Integration Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 420);
        frame.setResizable(false);
        frame.setLayout(null); //null: Position und Größe der Komponenten vollständig dem Entwickler überlassen

        //labels für textfelder
        labelInput = new JLabel("Enter what you would like to calculate");
        labelInput.setBounds(50, 20, 430, 20);
        labelInput.setForeground(Color.WHITE);
        labelInput.setFont(labelFont);

        labelOutput = new JLabel("Here is your solution");
        labelOutput.setBounds(50, 250, 430, 20);
        labelOutput.setForeground(Color.WHITE);
        labelOutput.setFont(labelFont);

        //Textfelder
        funcInput = createCustomTextfield(50, 40, 430, 85);
        funcOutput = createCustomTextfield(50, 270, 430, 85);
        funcOutput.setEditable(false);

        //Panel mit Buttons
        panel = new JPanel();
        panel.setBounds(50, 130, 430, 125);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        panel.setBackground(Color.DARK_GRAY);

        //Buttons
        buttonCalc = createCustomButton("Calculate");
        buttonClear = createCustomButton("Clear");

        panel.setLayout(new GridLayout(0, 1, 7, 7));
        panel.add(buttonCalc);
        panel.add(buttonClear);

        frame.add(panel);
        frame.add(labelInput);
        frame.add(labelOutput);
        frame.add(funcInput);
        frame.add(funcOutput);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setLocation(400, 200);
        frame.setVisible(true);
    }

    private JTextField createCustomTextfield(int x, int y, int width, int height){
        JTextField textField = new JTextField();

        textField.setBounds(x, y, width, height);
        textField.setForeground(Color.WHITE);
        textField.setBackground(Color.DARK_GRAY);

        //Setze die Cursorfarbe auf weiß
        textField.setCaretColor(Color.WHITE);

        //graue Umrandung und gerundete kanten
        Border compoundBorder = BorderFactory.createCompoundBorder(new GUIRoundedBorder(10, Color.GRAY),
                null);
        textField.setBorder(compoundBorder);

        //graue Umrandung
        //Border greyLineBorder = BorderFactory.createLineBorder(Color.GRAY);
        //textField.setBorder(greyLineBorder);
        textField.setFont(font);
        return textField;
    }

    private JButton createCustomButton(String text) {
        JButton button = new JButton(text);

        if(Objects.equals(text, "Clear")){
            button.setBackground(Color.LIGHT_GRAY);
        } else {
            button.setBackground(Color.GRAY);
        }
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        //Kombiniere abgerundete Ecken und graue Umrandung
        Border compoundBorder = BorderFactory.createCompoundBorder(new GUIRoundedBorder(10, Color.DARK_GRAY),
                null);
        button.setBorder(compoundBorder);

        button.setFont(font);
        button.addActionListener(this);
        return button;
    }

    public static void main(String[] args) {
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == buttonCalc){
            String inputText = funcInput.getText();

            //TODO: implement calculateFunction
            String result = calculateFunction(inputText);
            funcOutput.setText(result);

        } else if(e.getSource() == buttonClear){

            funcInput.setText("");
            funcOutput.setText("");
        }
    }

    //TODO
    private String calculateFunction(String inputText) {

        return inputText;
    }
}

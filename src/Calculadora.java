import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Calculadora extends JFrame implements ActionListener {
    JTextField field =  new JTextField();
    String[] normalCaracteres = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"};
    ArrayList<History> histories = new ArrayList<>();

    public Calculadora() {
        JPanel contentPane = new JPanel(new BorderLayout());

        field.setPreferredSize(new Dimension(0, 50));
        field.setBackground(Color.decode("#0d1b2a"));
        field.setForeground(Color.decode("#e0e1dd"));
        field.setEnabled(false);

        JPanel buttonPane = new JPanel(new GridLayout(4, 4, 5, 5));
        addButtonsInComponent(normalCaracteres.length, buttonPane, normalCaracteres, 16);

        JPanel toolsPane = new JPanel(new GridLayout(1,3,5,5));
        toolsPane.setPreferredSize(new Dimension(0, 50));
        String[] toolsText = {"Redo", "Clear", "History", "Exit"};
        addButtonsInComponent(toolsText.length, toolsPane, toolsText, 12);

        contentPane.add(field, BorderLayout.NORTH);
        contentPane.add(buttonPane, BorderLayout.CENTER);
        contentPane.add(toolsPane, BorderLayout.SOUTH);
        contentPane.setBackground(Color.decode("#415a77"));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 400);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setTitle("Calculadora");
        setResizable(false);
        setVisible(true);
    }

    public void addButtonsInComponent(Integer quantity, JComponent component, String[] textOfButtons, int size) {
        for (int index = 0; index < quantity; index++) {
            JButton button = new JButton();
            button.addActionListener(this);
            button.setBackground(Color.decode("#1b263b"));
            button.setFont(new Font("Arial", Font.PLAIN, size));
            button.setForeground(Color.decode("#e0e1dd"));
            button.setText(textOfButtons[index]);
            component.add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(field.getText().equals("NaN")) field.setText("");

        String textButton = ((JButton) (e.getSource())).getText();
        String textBeforeChange = field.getText();
        String textAfterChange = field.getText() + textButton;

        boolean isNormalCaracter = Arrays.asList(normalCaracteres).contains(textButton);

        if(isNormalCaracter) {
            if(textButton.equals("=")) {
                String expression = field.getText();
                String result = Calcs.calculateExpression(expression);
                histories.add(new History(expression, result));
                field.setText(result);
            } else if (isValidInput(textAfterChange)) {
                field.setText(textAfterChange);
            } else {
                field.setText(textBeforeChange);
            }
        } else{
            if(textButton.equals("Redo")){
                if(textBeforeChange.length() > 0){
                    field.setText(textBeforeChange.substring(0, textBeforeChange.length()-1));
                }
            }
            if(textButton.equals("Clear")) {
                field.setText("");
            }
            if(textButton.equals("History")) {
                new HistoryPane(new String[]{"Expressão", "Resultado"}, histories);
            }
            if(textButton.equals("Exit")) {
                System.exit(0);
            }
        }
    }

    private boolean isValidInput(String input) {
        String regex = "^[()\\d-+/*.]+$";
        return input.matches(regex);
    }
}

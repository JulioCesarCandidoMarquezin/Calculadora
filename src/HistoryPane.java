import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class HistoryPane extends JFrame {

    public HistoryPane(String[] tableColumns, ArrayList<History> histories) {
        DefaultTableModel tableModel = initializeDefaultTableModel(tableColumns, histories);

        JTable table = new JTable(tableModel);
        table.setEnabled(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#0d1b2a"));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setTitle("History");
        setResizable(false);
        setVisible(true);
        pack();
    }

    private DefaultTableModel initializeDefaultTableModel(String[] tableColumns, ArrayList<History> histories) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(tableColumns, 0);
        for (int index = histories.size() - 1; index >= 0; index--) {
            History history = (History) histories.toArray()[index];
            defaultTableModel.addRow(new Object[]{history.expression(), history.result()});
        }
        return defaultTableModel;
    }
}

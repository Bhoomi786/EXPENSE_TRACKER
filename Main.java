import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Main extends JFrame {

    JPanel sidebar;

    JTextField titleField;
    JTextField amountField;

    JComboBox<String> categoryBox;

    JTable table;
    DefaultTableModel model;

    JLabel totalLabel;

    double totalExpense = 0;

    boolean darkMode = true;

    Color darkBg = new Color(30, 30, 30);
    Color darkPanel = new Color(45, 45, 45);
    Color darkText = Color.WHITE;

    Color lightBg = Color.WHITE;
    Color lightPanel = new Color(240, 240, 240);
    Color lightText = Color.BLACK;

    public Main() {

        // Window
        setTitle("Expense Tracker Pro");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar
        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 600));
        sidebar.setBackground(new Color(25, 25, 25));
        sidebar.setLayout(new GridLayout(8, 1, 10, 10));

        JLabel logo = new JLabel("Expense Tracker");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 22));

        JButton dashboardBtn = new JButton("Dashboard");
        JButton addBtn = new JButton("Add Expense");
        JButton analyticsBtn = new JButton("Analytics");
        JButton themeButton = new JButton("Toggle Theme");

        styleButton(dashboardBtn);
        styleButton(addBtn);
        styleButton(analyticsBtn);

        sidebar.add(logo);
        sidebar.add(dashboardBtn);
        sidebar.add(addBtn);
        sidebar.add(analyticsBtn);

        // Content Panel
        JPanel content = new JPanel();
        content.setBackground(new Color(245, 245, 245));
        content.setLayout(null);

        // Heading
        JLabel heading = new JLabel("Expense Manager");
        heading.setForeground(Color.BLACK);
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setBounds(40, 20, 400, 40);

        // Title
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(40, 100, 100, 30);

        titleField = new JTextField();
        titleField.setBounds(140, 100, 220, 35);

        // Amount
        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setBounds(40, 160, 100, 30);

        amountField = new JTextField();
        amountField.setBounds(140, 160, 220, 35);

        // Category
        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setBounds(40, 220, 100, 30);

        String categories[] = {
            "Food",
            "Travel",
            "Shopping",
            "Bills",
            "Other"
        };

        categoryBox = new JComboBox<>(categories);
        categoryBox.setBounds(140, 220, 220, 35);

        JLabel themeLabel = new JLabel("Theme");

        themeLabel.setBounds(760, 40, 100, 30);

        themeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        String[] themes = {"Dark", "Light"};

        JComboBox<String> themeBox =
        new JComboBox<>(themes);
        themeBox.setSelectedItem("Light");

        themeBox.setBounds(840, 40, 140, 35);

        // Buttons
        JButton saveBtn = new JButton("Save Expense");
        saveBtn.setBounds(140, 300, 220, 45);

        JButton deleteBtn = new JButton("Delete Selected");
        deleteBtn.setBounds(700, 470, 180, 40);

        saveBtn.setBackground(new Color(0, 120, 215));
        saveBtn.setForeground(Color.WHITE);

        deleteBtn.setBackground(new Color(200, 50, 50));
        deleteBtn.setForeground(Color.WHITE);

        // Table
        String columns[] = {
            "Title",
            "Amount",
            "Category"
        };

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBounds(400, 100, 500, 350);

        // Total Label
        totalLabel = new JLabel("Total Expense: ₹0");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalLabel.setBounds(400, 470, 250, 40);

        // Add Components
        content.add(heading);

        content.add(titleLabel);
        content.add(titleField);

        content.add(amountLabel);
        content.add(amountField);

        content.add(categoryLabel);
        content.add(categoryBox);

        content.add(themeLabel);
        content.add(themeBox);

        content.add(saveBtn);

        content.add(scrollPane);

        content.add(totalLabel);

        content.add(deleteBtn);

        // Add Panels
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(content, BorderLayout.CENTER);

        add(mainPanel);

        // Load Data
        loadExpenses();

        // Save Expense
        saveBtn.addActionListener(e -> {

            String title = titleField.getText();

            String amountText = amountField.getText();

            String category =
                    categoryBox.getSelectedItem().toString();

            if(title.isEmpty() || amountText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        null,
                        "Please Fill All Fields"
                );

                return;
            }

            double amount =
                    Double.parseDouble(amountText);

            model.addRow(new Object[]{
                    title,
                    amount,
                    category
            });

            totalExpense += amount;

            updateTotal();

            saveExpense(title, amount, category);

            titleField.setText("");
            amountField.setText("");
        });

        // Delete Expense
        deleteBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if(row != -1) {

                double amount =
                        Double.parseDouble(
                                model.getValueAt(row, 1).toString()
                        );

                totalExpense -= amount;

                model.removeRow(row);

                updateTotal();

                rewriteFile();

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "Select Row First"
                );
            }
        });

        //add theme
        themeBox.addActionListener(e -> {

            String selectedTheme =
            (String) themeBox.getSelectedItem();

            if(selectedTheme.equals("Dark")) {

                content.setBackground(new Color(30,30,30));

                sidebar.setBackground(new Color(15,15,15));

                table.setBackground(new Color(45,45,45));
                table.setForeground(Color.WHITE);
                totalLabel.setForeground(Color.WHITE);

                heading.setForeground(Color.WHITE);

                titleLabel.setForeground(Color.WHITE);
                amountLabel.setForeground(Color.WHITE);
                categoryLabel.setForeground(Color.WHITE);
                themeLabel.setForeground(Color.WHITE);        

                table.getTableHeader().setBackground(
                    new Color(60,60,60)
                );

                table.getTableHeader().setForeground(
                    Color.WHITE
                );

            } else {

                content.setBackground(Color.WHITE);

                sidebar.setBackground(new Color(20,20,20));

                table.setBackground(Color.WHITE);
                table.setForeground(Color.BLACK);
                totalLabel.setForeground(Color.BLACK);

                heading.setForeground(Color.BLACK);

                titleLabel.setForeground(Color.BLACK);
                amountLabel.setForeground(Color.BLACK);
                categoryLabel.setForeground(Color.BLACK);
                themeLabel.setForeground(Color.BLACK);

                table.getTableHeader().setBackground(
                    Color.WHITE
                );

                table.getTableHeader().setForeground(
                    Color.BLACK
                );
            }
        });
        
        setVisible(true);
    }

    // Button Style
    void styleButton(JButton button) {

        button.setBackground(new Color(45, 45, 45));

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    // Update Total
    void updateTotal() {

        totalLabel.setText(
                "Total Expense: ₹" + totalExpense
        );
    }

    // Save Expense
    void saveExpense(
            String title,
            double amount,
            String category
    ) {

        try {

            FileWriter fw =
                    new FileWriter("expenses.txt", true);

            fw.write(
                    title + "," +
                            amount + "," +
                            category + "\n"
            );

            fw.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // Load Expenses
    void loadExpenses() {

        try {

            File file = new File("expenses.txt");

            if(!file.exists()) {

                file.createNewFile();
            }

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while((line = br.readLine()) != null) {

                String data[] = line.split(",");

                String title = data[0];

                double amount =
                        Double.parseDouble(data[1]);

                String category = data[2];

                model.addRow(new Object[]{
                        title,
                        amount,
                        category
                });

                totalExpense += amount;
            }

            br.close();

            updateTotal();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // Rewrite File
    void rewriteFile() {

        try {

            FileWriter fw =
                    new FileWriter("expenses.txt");

            for(int i = 0;
                i < model.getRowCount();
                i++) {

                fw.write(

                        model.getValueAt(i,0) + "," +

                        model.getValueAt(i,1) + "," +

                        model.getValueAt(i,2) + "\n"
                );
            }

            fw.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new Main();
    }
}
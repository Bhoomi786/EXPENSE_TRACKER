import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.ChartFactory;

import org.jfree.chart.ChartPanel;

import org.jfree.chart.JFreeChart;

import org.jfree.data.general.DefaultPieDataset;

public class Main extends JFrame {

    JPanel sidebar;

    JTextField titleField;
    JTextField amountField;

    JComboBox<String> categoryBox;

    JTable table;
    DefaultTableModel model;
    DefaultPieDataset dataset;

    JLabel totalLabel;
    JLabel foodLabel;
    JLabel travelLabel;
    JLabel shoppingLabel;
    JLabel billsLabel;
    JLabel otherLabel;

    double totalExpense = 0;
    double foodTotal = 0;
    double travelTotal = 0;
    double shoppingTotal = 0;
    double billsTotal = 0;
    double otherTotal = 0;

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
        getContentPane().setBackground(Color.WHITE);
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
        heading.setFont(
        new Font("Segoe UI", Font.BOLD, 30));

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

        titleField.setFont(
            new Font("Segoe UI", Font.PLAIN, 15));

        amountField.setFont(
            new Font("Segoe UI", Font.PLAIN, 15));

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

        themeLabel.setBounds(800, 40, 80, 30);

        themeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel searchLabel = new JLabel("Search");

        searchLabel.setBounds(500, 40, 80, 30);

        searchLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 18));

        String[] themes = {"Dark", "Light"};

        JComboBox<String> themeBox =
        new JComboBox<>(themes);
        themeBox.setSelectedItem("Light");

        themeBox.setBounds(880, 40, 140, 35);

        JTextField searchField = new JTextField();

        searchField.setBounds(580, 40, 180, 35);

        searchField.setFont(
             new Font("Segoe UI", Font.PLAIN, 15));

            searchField.setBorder(
                BorderFactory.createLineBorder(
                         new Color(0,191,255),
                             2
                )
            );

        categoryBox.setFont(
            new Font("Segoe UI", Font.PLAIN, 15));

        themeBox.setFont(
            new Font("Segoe UI", Font.PLAIN, 15));

        // Buttons
        JButton saveBtn = new JButton("Save Expense");
        saveBtn.setBounds(140, 300, 220, 45);

        JButton deleteBtn = new JButton("Delete Selected");
        deleteBtn.setBounds(700, 470, 180, 40);

        saveBtn.setBackground(
        new Color(0,170,255));
        saveBtn.setForeground(Color.WHITE);

       deleteBtn.setBackground(
        new Color(220,70,70));
        deleteBtn.setForeground(Color.WHITE);

        saveBtn.setFont(
            new Font("Segoe UI", Font.BOLD, 16));

        deleteBtn.setFont(
            new Font("Segoe UI", Font.BOLD, 16));

        // Table
        String columns[] = {
            "Title",
            "Amount",
            "Category"
        };

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        table.getTableHeader().setFont(
            new Font("Segoe UI", Font.BOLD, 18));

        TableRowSorter<DefaultTableModel> sorter =
             new TableRowSorter<>(model);

        table.setRowSorter(sorter);

        table.setRowHeight(35);

        table.setBackground(new Color(35,35,50));

        table.setForeground(Color.WHITE);

        table.setGridColor(new Color(60,60,80));

        table.setSelectionBackground(
                new Color(0,170,255)
        );

       table.setFont(
            new Font("Segoe UI", Font.PLAIN, 15)
        );

        table.getTableHeader().setBackground(
                new Color(0,170,255)
        );

        table.getTableHeader().setForeground(
                Color.WHITE
        );

        table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 15)
        );

        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBounds(400, 100, 500, 350);

        // Total Label
        totalLabel = new JLabel("Total Expense: ₹0");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalLabel.setBounds(400, 470, 250, 40);

        foodLabel = new JLabel("Food: ₹0");
        foodLabel.setBounds(400, 500, 200, 30);

        travelLabel = new JLabel("Travel: ₹0");
        travelLabel.setBounds(400, 530, 200, 30);

        shoppingLabel = new JLabel("Shopping: ₹0");
        shoppingLabel.setBounds(400, 560, 200, 30);

        billsLabel = new JLabel("Bills: ₹0");
        billsLabel.setBounds(400, 590, 200, 30);

        otherLabel = new JLabel("Others: ₹0.0");
        otherLabel.setBounds(400, 620, 200, 30);

        // Add Components
        heading.setForeground(
        new Color(0,170,255));

        content.add(heading);

        content.add(titleLabel);
        content.add(titleField);

        content.add(amountLabel);
        content.add(amountField);

        content.add(categoryLabel);
        content.add(categoryBox);

        dataset =
            new DefaultPieDataset();

        dataset.setValue("Food", foodTotal);

        dataset.setValue("Travel", travelTotal);

        dataset.setValue("Shopping", shoppingTotal);

        dataset.setValue("Bills", billsTotal);

        dataset.setValue("Other", otherTotal);

        JFreeChart pieChart =
            ChartFactory.createPieChart(
                "Expense Distribution",
                dataset,
                true,
                true,
                false
            );
            pieChart.getTitle().setPaint(Color.WHITE);
            pieChart.setBackgroundPaint(
                    new Color(45,45,45));
                    

        ChartPanel chartPanel =
            new ChartPanel(pieChart);

        chartPanel.setBounds(220, 420, 360, 220);
        chartPanel.setBackground(
                new Color(45,45,45));

        add(chartPanel);

        content.add(themeLabel);
        content.add(themeBox);

        content.add(searchLabel);

        content.add(searchField);

        content.add(saveBtn);

        content.add(scrollPane);

        content.add(totalLabel);

        content.add(deleteBtn);

        content.add(foodLabel);
        content.add(travelLabel);
        content.add(shoppingLabel);
        content.add(billsLabel);
        content.add(otherLabel);

        // Add Panels
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(content, BorderLayout.CENTER);

        add(mainPanel);

        // Load Data
        loadExpenses();
        updateTotal();

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

            if(category.equals("Food")) {
                    foodTotal += amount;
            }

            else if(category.equals("Travel")) {
                    travelTotal += amount;
            }

            else if(category.equals("Shopping")) {
                    shoppingTotal += amount;
            }

            else if(category.equals("Bills")) {
                     billsTotal += amount;
            }

            else {
                    otherTotal += amount;
            }

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

                content.setBackground(new Color(24,24,36));

                sidebar.setBackground(new Color(18,18,28));

                table.setBackground(new Color(35,35,50));
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
                getContentPane().setBackground(
                    new Color(24,24,36));

                searchLabel.setForeground(Color.WHITE);

                searchField.setBackground(
                        new Color(45,45,60));

                searchField.setForeground(Color.WHITE);

                foodLabel.setForeground(Color.WHITE);
                travelLabel.setForeground(Color.WHITE);
                shoppingLabel.setForeground(Color.WHITE);
                billsLabel.setForeground(Color.WHITE);
                otherLabel.setForeground(Color.WHITE);

            } else {

                content.setBackground(Color.WHITE);

                sidebar.setBackground(new Color(240,240,240));

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

                getContentPane().setBackground(Color.WHITE);

                searchLabel.setForeground(Color.BLACK);

                searchField.setBackground(Color.WHITE);

                searchField.setForeground(Color.BLACK);

                foodLabel.setForeground(Color.BLACK);
                travelLabel.setForeground(Color.BLACK);
                shoppingLabel.setForeground(Color.BLACK);
                billsLabel.setForeground(Color.BLACK);
                otherLabel.setForeground(Color.BLACK);
            }
        });

        searchField.addKeyListener(
                new java.awt.event.KeyAdapter() {

            public void keyReleased(
                java.awt.event.KeyEvent e
            ) {

                String text =
                    searchField.getText();

                if(text.length() == 0) {

                    sorter.setRowFilter(null);

                } else {

                    sorter.setRowFilter(
                        RowFilter.regexFilter(
                            "(?i)" + text
                        )
                    );
                }
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

        foodLabel.setText("Food: ₹" + foodTotal);

        travelLabel.setText("Travel: ₹" + travelTotal);

        shoppingLabel.setText("Shopping: ₹" + shoppingTotal);

        billsLabel.setText("Bills: ₹" + billsTotal);

        otherLabel.setText("Other: ₹" + otherTotal);

        dataset.setValue("Food", foodTotal);

        dataset.setValue("Travel", travelTotal);

        dataset.setValue("Shopping", shoppingTotal);

        dataset.setValue("Bills", billsTotal);

        dataset.setValue("Other", otherTotal);
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
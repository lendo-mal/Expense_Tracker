package myPackage;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;

public class Expense_Tracker extends JFrame {

    JTextField txtReceipt, txtStore, txtCost;
    JButton btnRecord, btnView, btnTotal, btnDelete;

    static final String FILE_NAME = "data.txt";
    static final double TAX_RATE = 0.12;

    DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        new Expense_Tracker();
    }

    Expense_Tracker() {

        // LABELS
        JLabel lblReceipt = new JLabel("Receipt Number");
        lblReceipt.setBounds(30, 30, 150, 25);
        add(lblReceipt);

        JLabel lblStore = new JLabel("Store Name");
        lblStore.setBounds(30, 80, 150, 25);
        add(lblStore);

        JLabel lblCost = new JLabel("Total Cost");
        lblCost.setBounds(30, 130, 150, 25);
        add(lblCost);

        // TEXTFIELDS
        txtReceipt = new JTextField();
        txtReceipt.setBounds(30, 55, 200, 25);
        add(txtReceipt);

        txtStore = new JTextField();
        txtStore.setBounds(30, 105, 200, 25);
        add(txtStore);

        txtCost = new JTextField();
        txtCost.setBounds(30, 155, 200, 25);
        add(txtCost);

        // BUTTONS
        btnRecord = new JButton("Record");
        btnRecord.setBounds(30, 200, 200, 30);
        add(btnRecord);

        btnView = new JButton("View All");
        btnView.setBounds(30, 240, 200, 30);
        add(btnView);

        btnTotal = new JButton("Calculate Total Expenses");
        btnTotal.setBounds(30, 280, 200, 30);
        add(btnTotal);

        btnDelete = new JButton("Delete All");
        btnDelete.setBounds(30, 320, 200, 30);
        add(btnDelete);

        // RECORD BUTTON
        btnRecord.addActionListener(e -> recordExpense());

        // VIEW BUTTON
        btnView.addActionListener(e -> viewAll());

        // TOTAL BUTTON
        btnTotal.addActionListener(e -> calculateTotal());

        // DELETE BUTTON
        btnDelete.addActionListener(e -> deleteAll());

        setTitle("Expense Tracker");
        setSize(300, 420);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // RECORD FUNCTION
   private void recordExpense() {

        try {

            String receipt = txtReceipt.getText();
            String store = txtStore.getText();
            double totalCost = Double.parseDouble(txtCost.getText());

            double tax = totalCost * TAX_RATE;
            double finalAmount = totalCost + tax;

            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));

            bw.write(receipt + "#" + store + "#" + df.format(totalCost)
                    + "#" + df.format(tax) + "#" + df.format(finalAmount));

            bw.newLine();
            bw.close();

            JOptionPane.showMessageDialog(this,
                    "Tax: " + df.format(tax) +
                            "\nFinal Amount: " + df.format(finalAmount));

            txtReceipt.setText("");
            txtStore.setText("");
            txtCost.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter valid data.");
        }
    }

    // VIEW ALL RECORDS
   private void viewAll() {

        try {

            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            String output = "";

            while ((line = br.readLine()) != null) {

                String[] data = line.split("#");

                output +=
                        "Receipt: " + data[0] +
                        "\nStore: " + data[1] +
                        "\nTotal Cost: " + data[2] +
                        "\nTax: " + data[3] +
                        "\nFinal Amount: " + data[4] +
                        "\n\n";
            }

            br.close();

            JOptionPane.showMessageDialog(this, output);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No records found.");
        }
    }

    // CALCULATE TOTAL EXPENSES
   private void calculateTotal() {

        double totalExpenses = 0;

        try {

            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split("#");
                totalExpenses += Double.parseDouble(data[4]);

            }

            br.close();

            JOptionPane.showMessageDialog(this,
                    "Total Expenses: " + df.format(totalExpenses));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No data found.");
        }
    }

    // DELETE ALL RECORDS
   private void deleteAll() {

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete all records?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            try {

                BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));
                bw.write("");
                bw.close();

                JOptionPane.showMessageDialog(this, "All records deleted.");

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error deleting file.");
            }
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class BusinessScenarioSimulator extends JFrame {

    private JTextField investmentField, demandGrowthField, costGrowthField, riskFactorField, yearsField;
    private JTextArea resultArea;
    
    public BusinessScenarioSimulator() {
        super("Business Scenario Simulator");
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        investmentField = new JTextField("500000");
        demandGrowthField = new JTextField("5");
        costGrowthField = new JTextField("2");
        riskFactorField = new JTextField("10");
        yearsField = new JTextField("5");

        inputPanel.add(new JLabel("Initial Investment ($):")); inputPanel.add(investmentField);
        inputPanel.add(new JLabel("Demand Growth (% per year):")); inputPanel.add(demandGrowthField);
        inputPanel.add(new JLabel("Cost Growth (% per year):")); inputPanel.add(costGrowthField);
        inputPanel.add(new JLabel("Risk Factor (%):")); inputPanel.add(riskFactorField);
        inputPanel.add(new JLabel("Simulation Years:")); inputPanel.add(yearsField);

        JButton simulateBtn = new JButton("Simulate");
        inputPanel.add(simulateBtn);

        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea(15, 50);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        simulateBtn.addActionListener(e -> runSimulation());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void runSimulation() {
        try {
            double investment = Double.parseDouble(investmentField.getText());
            double demandGrowth = Double.parseDouble(demandGrowthField.getText());
            double costGrowth = Double.parseDouble(costGrowthField.getText());
            double riskFactor = Double.parseDouble(riskFactorField.getText());
            int years = Integer.parseInt(yearsField.getText());

            resultArea.setText("Year\tRevenue\tCost\tProfit\n");
            double revenue = investment * 0.8; // start with 80% return
            double cost = investment * 0.5; // start with 50% cost
            
            Random rand = new Random();

            for (int year = 1; year <= years; year++) {
                double randomRisk = (rand.nextDouble() * riskFactor) - (riskFactor / 2);
                revenue *= 1 + (demandGrowth + randomRisk) / 100;
                cost *= 1 + costGrowth / 100;
                double profit = revenue - cost;
                resultArea.append(String.format("%d\t$%.2f\t$%.2f\t$%.2f\n", year, revenue, cost, profit));
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BusinessScenarioSimulator::new);
    }
}

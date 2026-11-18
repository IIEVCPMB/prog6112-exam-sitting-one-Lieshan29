package za.co.emeris.sales;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Mattress sales form (Q.2.1 - Q.2.4).
 *
 * <p>The form captures the customer name, the selected mattress and the price.
 * Pressing PROCESS calculates the VAT, the commission and the total sale amount,
 * adds the sale to data.txt and shows the result in a message box.</p>
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public class ProductSalesForm extends JFrame {

    /** The file every processed sale is appended to. */
    private static final Path LOG_FILE = Path.of("data.txt");

    /** The mattresses sold by the company. */
    private static final String[] MATTRESSES = {
        "Cozy Nights Monarch",
        "Restonic Barbados",
        "Sealy Phuket"
    };

    private static final String LINE_BREAK = System.lineSeparator();
    private static final String LOG_HEADER = "DATA LOG" + LINE_BREAK
            + "*************************" + LINE_BREAK;
    private static final String LOG_FOOTER = "*************************" + LINE_BREAK;

    private final JTextField nameInput = new JTextField(16);
    private final JComboBox<String> mattressInput = new JComboBox<>(MATTRESSES);
    private final JTextField priceInput = new JTextField(16);

    /**
     * Builds the sales form.
     */
    public ProductSalesForm() {
        super("Product App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar());
        setContentPane(createForm());
        setSize(430, 250);
        setLocationRelativeTo(null);
    }

    /**
     * Builds the File and Tools menus (Q.2.4).
     *
     * @return the menu bar of the form
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem processItem = new JMenuItem("Process");
        processItem.addActionListener(event -> processSale());
        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(event -> clearForm());
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        return menuBar;
    }

    /**
     * Lays out the labels, input fields and the PROCESS button (Q.2.2).
     *
     * @return the panel that holds the form
     */
    private JPanel createForm() {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints place = new GridBagConstraints();
        place.insets = new Insets(10, 12, 10, 12);
        place.anchor = GridBagConstraints.WEST;

        place.gridx = 0;
        place.gridy = 0;
        form.add(new JLabel("Customer Name:"), place);
        place.gridx = 1;
        place.gridwidth = 2;
        form.add(nameInput, place);

        place.gridwidth = 1;
        place.gridx = 0;
        place.gridy = 1;
        form.add(new JLabel("Select Product"), place);
        place.gridx = 1;
        place.gridwidth = 2;
        form.add(mattressInput, place);

        place.gridwidth = 1;
        place.gridx = 0;
        place.gridy = 2;
        form.add(new JLabel("Enter Price:"), place);
        place.gridx = 1;
        place.gridwidth = 2;
        form.add(priceInput, place);

        JButton processButton = new JButton("PROCESS");
        processButton.addActionListener(event -> processSale());
        place.gridx = 1;
        place.gridy = 3;
        place.gridwidth = 1;
        place.fill = GridBagConstraints.HORIZONTAL;
        form.add(processButton, place);

        return form;
    }

    /**
     * Calculates and stores one sale (Q.2.3).
     */
    private void processSale() {
        String customerName = nameInput.getText().trim();
        if (customerName.isEmpty()) {
            warn("Please enter the customer name.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceInput.getText().trim().replace(',', '.'));
        } catch (NumberFormatException invalidPrice) {
            warn("The price must be a number, for example 3500.");
            return;
        }
        if (price < 0) {
            warn("The price cannot be negative.");
            return;
        }

        String productName = String.valueOf(mattressInput.getSelectedItem());
        ProductCalculations calculation = new ProductCalculations(price);

        String sale = String.format(
                "Customer Name: %s%nProduct: %s%nVAT: R %.1f%nCOMMISSION: R %.1f%nTOTAL: R %.1f",
                customerName, productName,
                calculation.GetVat(), calculation.GetCommission(), calculation.GetTotal());

        appendToLog(sale);
        JOptionPane.showMessageDialog(this, sale, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Appends one sale to data.txt, writing the DATA LOG heading only the first
     * time the file is used.
     *
     * @param sale the lines describing the sale
     */
    private void appendToLog(String sale) {
        try {
            if (!Files.exists(LOG_FILE) || Files.size(LOG_FILE) == 0) {
                Files.writeString(LOG_FILE, LOG_HEADER, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
            Files.writeString(LOG_FILE, sale + LINE_BREAK + LOG_FOOTER,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException writeFailure) {
            warn("The sale could not be written to data.txt: " + writeFailure.getMessage());
        }
    }

    /**
     * Clears the inputs and resets the product list (Q.2.4).
     */
    private void clearForm() {
        nameInput.setText("");
        priceInput.setText("");
        mattressInput.setSelectedIndex(0);
        priceInput.requestFocusInWindow();
    }

    /**
     * Shows a message when the input cannot be processed.
     *
     * @param message the message for the user
     */
    private void warn(String message) {
        JOptionPane.showMessageDialog(this, message, "Check your input",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Starts the form.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductSalesForm().setVisible(true));
    }
}

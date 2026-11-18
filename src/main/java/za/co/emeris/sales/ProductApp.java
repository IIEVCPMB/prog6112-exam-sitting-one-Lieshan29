package za.co.emeris.sales;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.swing.BorderFactory;
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
 * <p>The form captures the customer name, the chosen mattress and the price.
 * PROCESS works out the VAT, the commission and the total sale amount, appends
 * the sale to {@code data.txt} and shows the same five lines in a message
 * dialogue.</p>
 *
 * <p>Design decisions: the button and the Tools menu both call
 * {@link #processSale()}, so the calculation and saving logic exists only once.
 * The rule and the money format come from {@link SaleFormat} so the dialogue and
 * the file cannot look different.</p>
 *
 * @author Lieshan Valliadum (ST10439494)
 */
public class ProductApp extends JFrame {

    /** The mattresses the company sells, in the order of the brief. */
    private static final String[] MATTRESSES = {
        "Cozy Nights Monarch",
        "Restonic Barbados",
        "Sealy Phuket"
    };

    /** The log file every processed sale is appended to. */
    private static final Path DATA_FILE = Path.of("data.txt");

    /** Heading written once, the first time a sale is saved. */
    private static final String DATA_LOG_HEADING = "DATA LOG" + System.lineSeparator()
            + SaleFormat.RULE + System.lineSeparator();

    private final JTextField customerField = new JTextField();
    private final JComboBox<String> productBox = new JComboBox<>(MATTRESSES);
    private final JTextField priceField = new JTextField();

    /**
     * Builds and lays out the sales form.
     */
    public ProductApp() {
        super("Product App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(buildMenuBar());
        setContentPane(buildForm());
        setSize(430, 260);
        setLocationRelativeTo(null);
    }

    /**
     * Builds the File and Tools menus (Q.2.4).
     *
     * @return the menu bar of the form
     */
    private JMenuBar buildMenuBar() {
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> System.exit(0));

        JMenuItem processItem = new JMenuItem("Process");
        processItem.addActionListener(event -> processSale());

        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(event -> clearForm());

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(exitItem);

        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        return menuBar;
    }

    /**
     * Lays out the labels, the input objects and the PROCESS button (Q.2.2).
     *
     * <p>A three-by-two grid keeps the labels and inputs aligned without a
     * position being set by hand, so the form still looks right when it is
     * resized.</p>
     *
     * @return the panel that holds the form
     */
    private JPanel buildForm() {
        JPanel inputs = new JPanel(new GridLayout(3, 2, 12, 12));
        inputs.setBorder(BorderFactory.createEmptyBorder(18, 18, 6, 18));
        inputs.add(new JLabel("Customer Name:"));
        inputs.add(customerField);
        inputs.add(new JLabel("Select Product"));
        inputs.add(productBox);
        inputs.add(new JLabel("Enter Price:"));
        inputs.add(priceField);

        JButton processButton = new JButton("PROCESS");
        processButton.addActionListener(event -> processSale());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(processButton);

        JPanel form = new JPanel(new BorderLayout());
        form.add(inputs, BorderLayout.CENTER);
        form.add(buttons, BorderLayout.SOUTH);
        return form;
    }

    /**
     * Validates the input, works out the sale and saves it (Q.2.3).
     */
    private void processSale() {
        String customerName = customerField.getText().trim();
        if (customerName.isEmpty()) {
            showError("Please enter the customer name.");
            return;
        }

        Double price = readPrice();
        if (price == null) {
            return;
        }

        ProductCalculations calculation = new ProductCalculations(price);
        String productName = String.valueOf(productBox.getSelectedItem());
        String sale = SaleFormat.saleLines(customerName, productName, calculation);

        saveSale(sale);
        JOptionPane.showMessageDialog(this, sale, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Reads the price, showing an error dialogue when it cannot be used.
     *
     * @return the price, or {@code null} when the input was rejected
     */
    private Double readPrice() {
        String entered = priceField.getText().trim();
        if (entered.isEmpty()) {
            showError("Please enter the price of the mattress.");
            return null;
        }
        double price;
        try {
            price = Double.parseDouble(entered.replace(',', '.'));
        } catch (NumberFormatException notANumber) {
            showError("The price must be a number, for example 3500.");
            return null;
        }
        if (price < 0) {
            showError("The price cannot be negative.");
            return null;
        }
        return price;
    }

    /**
     * Appends one sale to {@code data.txt}, writing the DATA LOG heading only
     * the first time so the file stays a log instead of being overwritten.
     *
     * @param sale the five lines describing the sale
     */
    private void saveSale(String sale) {
        String separator = System.lineSeparator();
        String entry = sale + separator + SaleFormat.RULE + separator;
        try {
            boolean firstSale = !Files.exists(DATA_FILE) || Files.size(DATA_FILE) == 0;
            String text = firstSale ? DATA_LOG_HEADING + entry : entry;
            Files.writeString(DATA_FILE, text, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException writeFailure) {
            showError("The sale could not be saved to data.txt: " + writeFailure.getMessage());
        }
    }

    /**
     * Clears the inputs and resets the product list (Q.2.4).
     */
    private void clearForm() {
        customerField.setText("");
        priceField.setText("");
        productBox.setSelectedIndex(0);
        customerField.requestFocusInWindow();
    }

    /**
     * Shows an error dialogue so the user knows why nothing was saved.
     *
     * @param message the message for the user
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Check your input",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Starts the form.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductApp().setVisible(true));
    }
}

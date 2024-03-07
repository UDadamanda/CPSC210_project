package ui;

import model.Event;
import model.EventLog;
import model.Spending;
import model.SpendingList;
import persistence.JsonReader;
import ui.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;

// Graphic User Interface for accounting app
public class AccountingAppGUI extends JFrame implements WindowListener {

    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 700;
    public static final int IMAGE_WIDTH = 900;
    public static final int IMAGE_HEIGHT = 300;
    private static final String JSON_STORE = "./data/spendingList.json";
    private static final String DEFAULT_NAME = "Default Spending List";

    private SpendingList spendingList;
    private JsonReader jsonReader;
    private ArrayList<Tool> tools;

    private ImageIcon welcomeImage;
    private ImageIcon addedSpendingImage;
    private ImageIcon removedSpendingImage;
    private ImageIcon fileSavedImage;
    private ImageIcon fileLoadedImage;
    private JPanel imagePanel;
    private JLabel imageAsLabel;
    private JTextArea text;

    private String status;

    // EFFECTS: initialize the window, instantiate spending list, and jsonReader,
    // and runs the accounting application
    public AccountingAppGUI() {
        super("AccountingApp");
        initializeFields();
        askLoadFile();
        initializeWindow();
        addWindowListener(this);
    }

    // MODIFIES: this
    // EFFECTS: instantiate spendingList and tools with ArrayList,
    //          this method is called by the Accounting App constructor
    private void initializeFields() {
        spendingList = new SpendingList();
        jsonReader = new JsonReader(JSON_STORE);
        tools = new ArrayList<Tool>();
    }

    // EFFECTS: ask if the user want to load previous file, load if yes and otherwise not
    private void askLoadFile() {
        JPanel popUpWindow = new JPanel();

        int result = JOptionPane.showConfirmDialog(popUpWindow,"Do you want to load your previous file?",
                "Load", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            try {
                spendingList = jsonReader.read();

                // show loaded message
                popUpWindow = new JPanel();
                JOptionPane.showConfirmDialog(popUpWindow,"Loaded " + spendingList.getName() + " from "
                                + JSON_STORE, "Loaded file", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
            } catch (IOException e) { // show error message
                popUpWindow = new JPanel();
                JOptionPane.showConfirmDialog(popUpWindow,"Unable to read from file: " + JSON_STORE,
                        "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
            }
        } else if (result == JOptionPane.NO_OPTION) {
            askNameOfSpendingList(popUpWindow);
        }
    }

    // EFFECTS: instantiate new popup window ask for name of spending list, set name of spending list to user's input,
    //          if user did not input value or click "Cancel", set name of spending list to default name
    private void askNameOfSpendingList(JPanel myPanel) {
        JTextField name = new JTextField(10);
        myPanel.add(new JLabel("Enter the Name of Your new Spending List"));
        myPanel.add(name);

        int nameResult = JOptionPane.showConfirmDialog(null, myPanel,
                "Name", JOptionPane.OK_CANCEL_OPTION);
        String nameInput = name.getText();
        if (nameResult == JOptionPane.OK_OPTION && !nameInput.equals("")) {
            spendingList.setName(nameInput);
        } else {
            spendingList.setName(DEFAULT_NAME);

            // show set name message
            myPanel = new JPanel();
            JOptionPane.showConfirmDialog(myPanel,"SpendingList have been set to default name: "
                    + DEFAULT_NAME, "Set name", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
    }

    // EFFECTS: initialization of main window, set dimension, create tools, set image and set location of window to
    //          center, set visible to true
    private void initializeWindow() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        createTools();
        setImage();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all tools
    private void createTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0,1));
        toolArea.setSize(new Dimension(0, 0));
        add(toolArea, BorderLayout.SOUTH);

        Tool addSpendingTool = new AddSpendingTool(this, toolArea, spendingList);
        tools.add(addSpendingTool);

        Tool removeSpendingTool = new RemoveSpendingTool(this, toolArea, spendingList);
        tools.add(removeSpendingTool);

        Tool viewSummaryTool = new ViewSummaryTool(this, toolArea, spendingList);
        tools.add(viewSummaryTool);

        Tool loadTool = new LoadTool(this, toolArea, spendingList);
        tools.add(loadTool);

        Tool saveTool = new SaveTool(this, toolArea, spendingList);
        tools.add(saveTool);

        Tool quitTool = new QuitTool(this, toolArea, spendingList);
        tools.add(quitTool);
    }

    // MODIFIES: imagePanel
    // EFFECTS: instantiate and add image panel, load image, set initial image to welcome image.
    private void setImage() {
        imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        add(imagePanel);

        loadImages();
        setWelcomeImage();
    }

    // MODIFIES: this
    // EFFECTS: load images
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        welcomeImage = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "images" + sep
                + "Welcome.png").getImage().getScaledInstance(900, 300, Image.SCALE_DEFAULT));
        addedSpendingImage = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "images"
                + sep + "Added_Spending.png").getImage().getScaledInstance(900, 300, Image.SCALE_DEFAULT));
        removedSpendingImage = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep + "images"
                + sep + "Removed_Spending.png").getImage().getScaledInstance(900, 300,
                Image.SCALE_DEFAULT));
        fileLoadedImage = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "File_Loaded.png").getImage().getScaledInstance(900, 300,
                Image.SCALE_DEFAULT));
        fileSavedImage = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "File_Saved.png").getImage().getScaledInstance(900, 300,
                Image.SCALE_DEFAULT));

    }

    // MODIFIES: imagePanel
    // EFFECTS: set image panel to welcome image
    public void setWelcomeImage() {
        removeExistingImage();
        imageAsLabel = new JLabel(welcomeImage);
        imagePanel.add(imageAsLabel);
    }

    // MODIFIES: imagePanel
    // EFFECTS: set image panel to add spending image
    public void setAddedSpendingImage() {
        removeExistingImage();
        imageAsLabel = new JLabel(addedSpendingImage);
        imagePanel.add(imageAsLabel);
    }

    // MODIFIES: imagePanel
    // EFFECTS: set image panel to remove image
    public void setRemove() {
        removeExistingImage();
        imageAsLabel = new JLabel(removedSpendingImage);
        imagePanel.add(imageAsLabel);
    }

    // MODIFIES: imagePanel
    // EFFECTS: set image panel to file saved image
    public void setFileSavedImage() {
        removeExistingImage();
        imageAsLabel = new JLabel(fileSavedImage);
        imagePanel.add(imageAsLabel);
    }

    // MODIFIES: imagePanel
    // EFFECTS: set image panel to file loaded image
    public void setFileLoadedImage() {
        removeExistingImage();
        imageAsLabel = new JLabel(fileLoadedImage);
        imagePanel.add(imageAsLabel);
    }


    // EFFECTS: set image panel to summary text for all spending
    private void setAllSummary() {
        summaryTemplate();
        ArrayList<Spending> summaryList = spendingList.getAllSummary();
        summary(summaryList);
    }

    // MODIFIES: this
    // EFFECTS: remove existing image or text and print "Summary:" in the image panel
    private void summaryTemplate() {
        removeExistingImage();
        text = new JTextArea("Summary: \n");
        imagePanel.add(text);
    }

    // MODIFIES: this
    // EFFECTS: helper function for setSummaryText
    private void summary(ArrayList<Spending> list) {
        double currentBalance = 0;
        int numSpending = 0;
        for (Spending spending : list) {
            text = new JTextArea(spending.displaySpending() + "\n");
            imagePanel.add(text);
            currentBalance = currentBalance + spending.getAmount();
            numSpending++;
        }
        text = new JTextArea("There are " + numSpending + " spending, your total amount spent is " + currentBalance
                + "\n");
        imagePanel.add(text);
    }

    // MODIFIES: this
    // EFFECTS: remove image or text if there is image in imagePanel
    private void removeExistingImage() {
        imagePanel.removeAll();
    }

    // MODIFIES: this
    // EFFECTS: set current spending list to given
    public void setSpendingList(SpendingList spendingList) {
        this.spendingList = spendingList;
    }

    // MODIFIES: this
    // EFFECTS: set image panel to fail to save message
    public void failToSaveMessage() {
        removeExistingImage();
        JTextArea text = new JTextArea("Unable to write to file: " + JSON_STORE);
        imagePanel.add(text);
    }

    // MODIFIES: this
    // EFFECTS: set image panel to fail to load message
    public void failToLoadMessage() {
        removeExistingImage();
        JTextArea text = new JTextArea("Unable to read from file: " + JSON_STORE);
        imagePanel.add(text);
    }

    // MODIFIES: this
    // EFFECTS: set current status of image panel given
    public void setStatus(String status) {
        this.status = status;
        processStatus();
        validate();
        repaint();
    }

    // EFFECTS: process current status, change image panel to iamge correspond to current status
    private void processStatus() {
        if (status.equals("added")) {
            setAddedSpendingImage();
        } else if (status.equals("removed")) {
            setRemove();
        } else if (status.equals("loaded")) {
            setFileLoadedImage();
        } else if (status.equals("saved")) {
            setFileSavedImage();
        } else if (status.equals("all")) {
            setAllSummary();
        } else {
            setWelcomeImage();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        EventLog eventLog = EventLog.getInstance();
        for (Event event : eventLog) {
            System.out.println(event.toString());
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


}
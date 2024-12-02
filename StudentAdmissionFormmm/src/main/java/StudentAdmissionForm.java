import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.toedter.calendar.JDateChooser;

public class StudentAdmissionForm extends JFrame {

    private JTextField studentNameField, fatherNameField, motherNameField, presentAddressField, permanentAddressField;
    private JDateChooser dob;
    private JComboBox<String> bloodGroupCombo, courseCombo, semesterCombo, yearCombo;
    private JRadioButton maleRadio, femaleRadio;
    private JCheckBox sameAsPresent;
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<Student> students = new ArrayList<>();
    private static final String FILE_PATH = "students.ser";  // Serialized data file

    public StudentAdmissionForm() {
        setTitle("Student Admission Form");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the panel with a light blue background
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(173, 216, 230));  // Light Blue color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        Font titleFont = new Font("Arial", Font.BOLD | Font.ITALIC, 20);
        Font subtitleFont = new Font("Arial", Font.PLAIN, 16);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Metropolitan University", JLabel.CENTER);
        titleLabel.setFont(titleFont);
        panel.add(titleLabel, gbc);

        // Subtitle
        gbc.gridy = 1;
        JLabel subtitleLabel = new JLabel("Registration Form", JLabel.CENTER);
        subtitleLabel.setFont(subtitleFont);
        panel.add(subtitleLabel, gbc);

        // Reset grid width for other components
        gbc.gridwidth = 1;

        // Add form fields
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Student's Name:"), gbc);

        gbc.gridx = 1;
        studentNameField = new JTextField(20);
        panel.add(studentNameField, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Gender:"), gbc);

        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        gbc.gridx = 1;
        panel.add(genderPanel, gbc);

        // Date of Birth
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Date of Birth:"), gbc);
        dob = new JDateChooser();
        dob.setDateFormatString("dd/MM/yyyy");
        gbc.gridx = 1;
        panel.add(dob, gbc);

        // Blood Group
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Blood Group:"), gbc);

        String[] bloodGroups = {"", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        bloodGroupCombo = new JComboBox<>(bloodGroups);
        gbc.gridx = 1;
        panel.add(bloodGroupCombo, gbc);

        // Father's Name
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Father's Name:"), gbc);

        gbc.gridx = 1;
        fatherNameField = new JTextField(20);
        panel.add(fatherNameField, gbc);

        // Mother's Name
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Mother's Name:"), gbc);

        gbc.gridx = 1;
        motherNameField = new JTextField(20);
        panel.add(motherNameField, gbc);

        // Course
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Course:"), gbc);

        String[] courses = {"", "CSE", "EEE", "BBA", "SWE", "English"};
        courseCombo = new JComboBox<>(courses);
        gbc.gridx = 1;
        panel.add(courseCombo, gbc);

        // Semester
        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(new JLabel("Semester:"), gbc);

        String[] semesters = {"", "Spring", "Fall"};
        semesterCombo = new JComboBox<>(semesters);
        gbc.gridx = 1;
        panel.add(semesterCombo, gbc);

        // Year ComboBox
        gbc.gridx = 0;
        gbc.gridy = 10;
        panel.add(new JLabel("Year:"), gbc);

        String[] years = {"2024", "2025", "2026", "2027", "2028"};
        yearCombo = new JComboBox<>(years);
        yearCombo.setPreferredSize(new Dimension(200, 25));  // Make dropdown visible
        gbc.gridx = 1;
        panel.add(yearCombo, gbc);

        // Present Address
        gbc.gridx = 0;
        gbc.gridy = 11;
        panel.add(new JLabel("Present Address:"), gbc);

        gbc.gridx = 1;
        presentAddressField = new JTextField(20);
        panel.add(presentAddressField, gbc);

        // Permanent Address
        gbc.gridx = 0;
        gbc.gridy = 12;
        panel.add(new JLabel("Permanent Address:"), gbc);

        gbc.gridx = 1;
        permanentAddressField = new JTextField(20);
        sameAsPresent = new JCheckBox("Same as Present Address");
        JPanel permanentAddressPanel = new JPanel(new BorderLayout());
        permanentAddressPanel.add(permanentAddressField, BorderLayout.CENTER);
        permanentAddressPanel.add(sameAsPresent, BorderLayout.SOUTH);
        panel.add(permanentAddressPanel, gbc);

        // Action listener for "Same as Present Address"
        sameAsPresent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sameAsPresent.isSelected()) {
                    permanentAddressField.setText(presentAddressField.getText());
                } else {
                    permanentAddressField.setText("");
                }
            }
        });

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton submitButton = new JButton("Submit");
        JButton resetButton = new JButton("Reset");
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, gbc);

        // Table
        gbc.gridy = 14;
        String[] columnNames = {"Name", "Gender", "DOB", "Blood Group", "Course", "Semester", "Year", "Present Address", "Permanent Address"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(700, 150));
        panel.add(tableScrollPane, gbc);

        // Add action listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRow();
            }
        });

        // Load saved data from file
        loadData();

        add(panel);
        setVisible(true);
    }

    // Handle Submit button
    private void submitForm() {
        String name = studentNameField.getText();
        String gender = maleRadio.isSelected() ? "Male" : "Female";
        String bloodGroup = (String) bloodGroupCombo.getSelectedItem();
        String course = (String) courseCombo.getSelectedItem();
        String semester = (String) semesterCombo.getSelectedItem();
        String year = (String) yearCombo.getSelectedItem();
        String presentAddress = presentAddressField.getText();
        String permanentAddress = sameAsPresent.isSelected() ? presentAddress : permanentAddressField.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dobStr = (dob.getDate() != null) ? dateFormat.format(dob.getDate()) : "N/A";

        tableModel.addRow(new Object[]{name, gender, dobStr, bloodGroup, course, semester, year, presentAddress, permanentAddress});
    }

    // Handle Reset button
    private void resetForm() {
        studentNameField.setText("");
        dob.setDate(null);
        fatherNameField.setText("");
        motherNameField.setText("");
        presentAddressField.setText("");
        permanentAddressField.setText("");
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
        bloodGroupCombo.setSelectedIndex(0);
        courseCombo.setSelectedIndex(0);
        semesterCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(-1);  // Clear the Year combo box
        sameAsPresent.setSelected(false);
    }

    // Save data to file
    private void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            students.clear();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String name = (String) tableModel.getValueAt(i, 0);
                String gender = (String) tableModel.getValueAt(i, 1);
                String dob = (String) tableModel.getValueAt(i, 2);
                String bloodGroup = (String) tableModel.getValueAt(i, 3);
                String course = (String) tableModel.getValueAt(i, 4);
                String semester = (String) tableModel.getValueAt(i, 5);
                String year = (String) tableModel.getValueAt(i, 6);
                String presentAddress = (String) tableModel.getValueAt(i, 7);
                String permanentAddress = (String) tableModel.getValueAt(i, 8);
                students.add(new Student(name, gender, dob, bloodGroup, course, semester, year, presentAddress, permanentAddress));
            }
            out.writeObject(students);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }

    // Load data from file
    private void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            students = (ArrayList<Student>) in.readObject();
            for (Student student : students) {
                tableModel.addRow(new Object[]{
                    student.getName(), student.getGender(), student.getDob(), student.getBloodGroup(),
                    student.getCourse(), student.getSemester(), student.getYear(), student.getPresentAddress(), student.getPermanentAddress()
                });
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading data. The file may be missing or corrupted.");
        }
    }

    // Delete a selected row
    private void deleteRow() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            tableModel.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }
    }

    public static void main(String[] args) {
        new StudentAdmissionForm();
    }
}


package TP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

public class GeigerMullerDialog extends JDialog {
    static JTextField txtTubeLength, txtTubeDiameter, txtWallThickness, txtWindowThickness;
    static JTextField txtGasPressure, txtQuenchingGasConcentration;
    static JTextField txtVoltage, txtVoltageSeuil, txtK1, txtImpedance, txtTemperatureReference, txtLeakageCurrent, txtLoadResistance,
    txtGasConcentration, txtGasConcentrationReference,txtPressureReference, txtK2, txtK3, txtK4, txtK5, txtK6;
    static JTextField txtDetectionThreshold, txtEfficiency, txtCountRate, txtDeadTime;
    static JTextField txtTemperature, txtHumidity, txtPressure;
    static JComboBox<String> comboBoxTubeMaterial, comboBoxWindowMaterial, comboBoxGasType;
    private JButton btnOK, btnCancel, btnInitialize;
    String pathFile = "DetectorParametres\\DetectorParametres.txt";

    public GeigerMullerDialog(JFrame parent) {
        super(parent, "Geiger-Müller Detector Parameters", true);
        setSize(820, 590);
        setLayout(null);

        
        // Paramètres géométriques
        //String Ln=  traveauPratique.textField_3.getText();
        createLabel("Detector length", 10, 10);
        txtTubeLength = createTextField("", 170+15, 10);
        createLabel("cm", 280, 10);
        //String ry=  traveauPratique.textField_1.getText();
        createLabel("Detector radius", 10, 40);
        txtTubeDiameter = createTextField("", 170+15, 40);
        createLabel("cm", 280, 40);

        createLabel("Tube material", 10, 70);
        String[] materials = {"Stainless steel", "Glass"};
        comboBoxTubeMaterial = new JComboBox<>(materials);
        comboBoxTubeMaterial.setBounds(185, 70, 80, 25);
        add(comboBoxTubeMaterial);

        createLabel("Wall thickness", 10, 100);
        txtWallThickness = createTextField("0.5", 170+15, 100);
        createLabel("mm", 280, 100);

        createLabel("Window material", 10, 130);
        String[] windowMaterials = {"Mica", "Glass"};
        comboBoxWindowMaterial = new JComboBox<>(windowMaterials);
        comboBoxWindowMaterial.setBounds(185, 130, 80, 25);
        add(comboBoxWindowMaterial);

        createLabel("Window thickness", 10, 160);
        txtWindowThickness = createTextField("1", 170+15, 160);
        createLabel("µm", 280, 160);

        // Paramètres du gaz
        createLabel("Gas type", 10, 190);
        String[] gasTypes = {"Xénon", "Argon", "Néon", "Hélium", "krypton"};
        comboBoxGasType = new JComboBox<>(gasTypes);
        comboBoxGasType.setBounds(190, 190, 80, 25);
        add(comboBoxGasType);

        createLabel("Gas pressure", 10, 220);
        txtGasPressure = createTextField("0.9", 170+15, 220);
        createLabel("atm", 280, 220);

        createLabel("Coefficient k1", 610, 220);
        txtK1 = createTextField("-1.5", 700, 220);

        // Paramètres électriques - Alignés sur la même ligne avec positions absolues
        createLabel("Voltage (V1)", 310, 250);
        txtVoltage = createTextField("220", 470, 250);
        createLabel("V", 580, 250);   // Un label à côté de Tension d'alimentation
        
        createLabel("Voltage threshold (Vp)", 10, 250);
        txtVoltageSeuil = createTextField("120", 170+15, 250);
        createLabel("V", 280, 250);   // Un label à côté de Référence Tension

        createLabel("Voltage  (V2)", 610, 250);
        txtK2 = createTextField("660", 700, 250);
        
     // Paramètres de concentration - Alignés sur la même ligne avec positions absolues
        createLabel("Gas concentration", 10, 280);
        txtGasConcentration = createTextField("2.8", 170+15, 280);  // Valeur par défaut pour la concentration du gaz
        createLabel(" mol/m³", 260, 280);   // Un label à côté de la concentration

        createLabel("Ref_concentration (C_0)", 310, 280);
        txtGasConcentrationReference = createTextField("3", 470, 280);  // Valeur par défaut pour la concentration de référence
        createLabel(" mol/m³", 555, 280);   // Un label à côté de la concentration de référence

        createLabel("Coefficient k3", 610, 280);
        txtK3 = createTextField("1", 700, 280);  // Valeur par défaut pour le coefficient k2

        
     // Paramètres électriques - Alignés sur la même ligne avec positions absolues
        createLabel("Load resistance (R_L)", 10, 310);
        txtLoadResistance = createTextField("1e7", 170+15, 310);  // Valeur par défaut pour la résistance de charge
        createLabel("MΩ", 280, 310);   // Un label à côté de Résistance de charge

        createLabel("Impedance (Z_f)", 310, 310);
        txtImpedance = createTextField("1e9", 470, 310);  // Valeur par défaut pour l'impédance
        createLabel("Ω", 580, 310);   // Un label à côté de l'impédance

        createLabel("Coefficient k4", 610, 310);
        txtK4 = createTextField("0.005", 700, 310);  // Valeur par défaut pour le coefficient k4

        // Paramètres de détection      

        createLabel("Dead time", 10, 400);
        txtDeadTime = createTextField("200", 170+15, 400);
        createLabel("µs", 280, 400);

        // Paramètres environnementaux     
        createLabel("Temperature", 10, 340);
        txtTemperature = createTextField("25", 170+15, 340);  // Valeur par défaut pour la température
        createLabel("°C", 280, 340);   // Un label à côté de la température

        createLabel("Reference temperature (T_0)", 310, 340);
        txtTemperatureReference = createTextField("50", 470, 340);  // Valeur par défaut pour la température de référence
        createLabel("°C", 580, 340);   // Un label à côté de la température de référence

        createLabel("Coefficient k5", 610, 340);
        txtK5 = createTextField("0.2", 700, 340);  // Valeur par défaut pour le coefficient k5


       
     // Pression atmosphérique - Alignée sur la même ligne avec positions absolues
        createLabel("Atmosph pressure (P0)", 10, 370);
        txtPressure = createTextField("1", 170+15, 370);  // Valeur par défaut pour la pression atmosphérique (1 atm)
        createLabel("atm", 280, 370);   // Un label à côté de la pression atmosphérique

        createLabel("Ref pressure(P0_ref)", 310, 370);
        txtPressureReference = createTextField("1", 470, 370);  // Valeur par défaut pour la pression de référence (1 atm)
        createLabel("atm", 580, 370);   // Un label à côté de la pression de référence

        createLabel("Coefficient k6", 610, 370);
        txtK6 = createTextField("0.05", 700, 370);  // Valeur par défaut pour le coefficient k6
        loadDataFromFile();
        
        // Boutons
        btnOK = new JButton("OK");
        btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	double VV1 = Double.parseDouble(txtVoltage.getText());
            	double VV2 = Double.parseDouble(txtK2.getText());
            	int V_fonct =(int) (VV1+(VV2-VV1)/2);
            	traveauPratique.textField_10.setText(String.valueOf(V_fonct));
            	
            	saveDataToFile();
            	
            	traveauPratique.textField_1.setText(txtTubeDiameter.getText());
            	traveauPratique.textField_3.setText(txtTubeLength.getText());
            	
            	
            	dispose();
            }
        });
            
        btnOK.setBounds(50, 510, 100, 30);
        add(btnOK);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            
            	dispose();
            }
        });
           
        
        btnCancel.setBounds(180, 510, 100, 30);
        add(btnCancel);

        btnInitialize = new JButton("Initialize");
        btnInitialize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	pathFile = "initialDetectorGM\\DetectorParametres.txt";
            	loadDataFromFile();
            	pathFile = "DetectorParametres\\DetectorParametres.txt";	
            	
        
      }
    });
        btnInitialize.setBounds(310, 510, 100, 30);
        add(btnInitialize);
    
    
    
    }

    private JTextField createTextField(String text, int x, int y) {
        JTextField textField = new JTextField(text);
        textField.setBounds(x, y, 80, 25);
        add(textField);
        return textField;
    }

    private void createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 160, 25);
        add(label);
    }
    
    //--------------------lire le contenu de la soucre dejà enregistré----------
    private void loadDataFromFile() {
        try {
            Path path = Paths.get(pathFile);
            if (!Files.exists(path)) {
                return; // Ne rien faire si le fichier n'existe pas
            }

            java.util.List<String> lines = Files.readAllLines(path);
            if (lines.size() < 23) {
                throw new IOException("File incorrectly formatted!");
            }

            // Remplir les champs avec les données du fichier
            txtTubeLength.setText(lines.get(0));
            txtTubeDiameter.setText(lines.get(1));
            comboBoxTubeMaterial.setSelectedItem(lines.get(2));
            txtWallThickness.setText(lines.get(3));
            comboBoxWindowMaterial.setSelectedItem(lines.get(4));
            txtWindowThickness.setText(lines.get(5));
            comboBoxGasType.setSelectedItem(lines.get(6));
            txtGasPressure.setText(lines.get(7));
            txtK1.setText(lines.get(8));
            txtVoltageSeuil.setText(lines.get(9));
            txtVoltage.setText(lines.get(10));
            txtK2.setText(lines.get(11));
            txtGasConcentration.setText(lines.get(12));
            txtGasConcentrationReference.setText(lines.get(13));
            txtK3.setText(lines.get(14));
            txtLoadResistance.setText(lines.get(15));
            txtImpedance.setText(lines.get(16));
            txtK4.setText(lines.get(17));
            txtTemperature.setText(lines.get(18));
            txtTemperatureReference.setText(lines.get(19));
            txtK5.setText(lines.get(20));
            txtPressure.setText(lines.get(21));
            txtPressureReference.setText(lines.get(22));
            txtK6.setText(lines.get(23));
            txtDeadTime.setText(lines.get(24));           

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile))) {
        	writer.write(txtTubeLength.getText() + "\n");
        	writer.write(txtTubeDiameter.getText() + "\n");
        	writer.write(comboBoxTubeMaterial.getSelectedItem().toString() + "\n");
            writer.write(txtWallThickness.getText() + "\n");
            writer.write(comboBoxWindowMaterial.getSelectedItem().toString() + "\n");
            writer.write(txtWindowThickness.getText() + "\n");
            writer.write(comboBoxGasType.getSelectedItem().toString() + "\n");
            writer.write(txtGasPressure.getText() + "\n");
            writer.write(txtK1.getText() + "\n");
            writer.write(txtVoltageSeuil.getText() + "\n");
            writer.write(txtVoltage.getText() + "\n");
            writer.write(txtK2.getText() + "\n");
            writer.write(txtGasConcentration.getText() + "\n");
            writer.write(txtGasConcentrationReference.getText() + "\n");
            writer.write(txtK3.getText() + "\n");
            writer.write(txtLoadResistance.getText() + "\n");
            writer.write(txtImpedance.getText() + "\n");
            writer.write(txtK4.getText() + "\n");
            writer.write(txtTemperature.getText() + "\n");
            writer.write(txtTemperatureReference.getText() + "\n");
            writer.write(txtK5.getText() + "\n");
            writer.write(txtPressure.getText() + "\n");
            writer.write(txtPressureReference.getText() + "\n");
            writer.write(txtK6.getText() + "\n");
            writer.write(txtDeadTime.getText() + "\n");

            JOptionPane.showMessageDialog(this, "Data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GeigerMullerDialog dialog = new GeigerMullerDialog(frame);
        dialog.setVisible(true);
    }
}

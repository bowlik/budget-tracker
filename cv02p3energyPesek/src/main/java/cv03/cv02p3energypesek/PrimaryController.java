package cv03.cv02p3energypesek;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;

public class PrimaryController {

    @FXML private Slider sliderMonths;
    @FXML private CheckBox checkVysokyTarif, checkNizkyTarif;
    @FXML private TextField txtSpotrebaVT, txtCenaDodavkaVT, txtCenaDistribuceVT;
    @FXML private TextField txtSpotrebaNT, txtCenaDodavkaNT, txtCenaDistribuceNT;
    @FXML private TextField txtDan, txtMesicniPoplatek, txtPoplatekRezervace, txtSystemoveSluzby, txtKVET, txtOTE;
    @FXML private Slider sliderPocetZaloh;
    @FXML private TextField txtVyseZalohy;
    @FXML private Text txtVysledek, txtNedoplatekPřeplatek;
    @FXML private GridPane panelVT, panelNT;

    @FXML
    private void calculate() {
        int months = (int) sliderMonths.getValue();

        double spotrebaVT = 0, cenaDodavkaVT = 0, cenaDistribuceVT = 0;
        if (checkVysokyTarif.isSelected()) {
            try {
                spotrebaVT = Double.parseDouble(txtSpotrebaVT.getText());
                cenaDodavkaVT = Double.parseDouble(txtCenaDodavkaVT.getText());
                cenaDistribuceVT = Double.parseDouble(txtCenaDistribuceVT.getText());
            } catch (NumberFormatException e) {
                showAlert("Chyba", "Neplatný formát hodnoty pro vysoký tarif.");
                return;
            }
        }

        double spotrebaNT = 0, cenaDodavkaNT = 0, cenaDistribuceNT = 0;
        if (checkNizkyTarif.isSelected()) {
            try {
                spotrebaNT = Double.parseDouble(txtSpotrebaNT.getText());
                cenaDodavkaNT = Double.parseDouble(txtCenaDodavkaNT.getText());
                cenaDistribuceNT = Double.parseDouble(txtCenaDistribuceNT.getText());
            } catch (NumberFormatException e) {
                showAlert("Chyba", "Neplatný formát hodnoty pro nízký tarif.");
                return;
            }
        }

        double dan = parseDouble(txtDan);
        double mesicniPoplatek = parseDouble(txtMesicniPoplatek);
        double poplatekRezervace = parseDouble(txtPoplatekRezervace);
        double systemoveSluzby = parseDouble(txtSystemoveSluzby);
        double kvetOzeDz = parseDouble(txtKVET);
        double ote = parseDouble(txtOTE);

        double cenaVT = (spotrebaVT * (cenaDodavkaVT + cenaDistribuceVT)) * months;
        double cenaNT = (spotrebaNT * (cenaDodavkaNT + cenaDistribuceNT)) * months;
        double celkovaCena = cenaVT + cenaNT;

        double celkovePoplatky = dan + mesicniPoplatek * months + poplatekRezervace + systemoveSluzby + kvetOzeDz + ote;

        celkovaCena += celkovePoplatky;
        celkovaCena = Math.round(celkovaCena); // Zaokrouhlení na celé koruny

        double zaplaceneZalohy = sliderPocetZaloh.getValue() * parseDouble(txtVyseZalohy);
        double nedoplatek = zaplaceneZalohy - celkovaCena;

        txtVysledek.setText("Celková cena: " + celkovaCena + " Kč");
        if (nedoplatek < 0) {
            txtNedoplatekPřeplatek.setText("Nedoplatek: " + Math.abs(nedoplatek) + " Kč");
            txtNedoplatekPřeplatek.setFill(Color.RED);
        } else {
            txtNedoplatekPřeplatek.setText("Přeplatek: " + Math.abs(nedoplatek) + " Kč");
            txtNedoplatekPřeplatek.setFill(Color.GREEN);
        }
    }

    private double parseDouble(TextField field) {
        try {
            return Double.parseDouble(field.getText());
        } catch (NumberFormatException e) {
            showAlert("Chyba", "Neplatný formát čísla.");
            return 0;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleCheckBoxVT() {
        toggleVysokyTarifFields();
    }

    @FXML
    public void handleCheckBoxNT() {
        toggleNizkyTarifFields();
    }

    private void toggleVysokyTarifFields() {
        boolean isChecked = checkVysokyTarif.isSelected();
        panelVT.setDisable(!isChecked);
        txtSpotrebaVT.setDisable(!isChecked);
        txtCenaDodavkaVT.setDisable(!isChecked);
        txtCenaDistribuceVT.setDisable(!isChecked);
    }

    private void toggleNizkyTarifFields() {
        boolean isChecked = checkNizkyTarif.isSelected();
        panelNT.setDisable(!isChecked);
        txtSpotrebaNT.setDisable(!isChecked);
        txtCenaDodavkaNT.setDisable(!isChecked);
        txtCenaDistribuceNT.setDisable(!isChecked);
    }
    
    @FXML
private void handleCalculate(ActionEvent event) {
    int months = (int) sliderMonths.getValue();

    double spotrebaVT = 0, cenaDodavkaVT = 0, cenaDistribuceVT = 0;
    if (checkVysokyTarif.isSelected()) {
        try {
            spotrebaVT = Double.parseDouble(txtSpotrebaVT.getText());
            cenaDodavkaVT = Double.parseDouble(txtCenaDodavkaVT.getText());
            cenaDistribuceVT = Double.parseDouble(txtCenaDistribuceVT.getText());
        } catch (NumberFormatException e) {
            showAlert("Chyba", "Neplatný formát hodnoty pro vysoký tarif.");
            return;
        }
    }

    double spotrebaNT = 0, cenaDodavkaNT = 0, cenaDistribuceNT = 0;
    if (checkNizkyTarif.isSelected()) {
        try {
            spotrebaNT = Double.parseDouble(txtSpotrebaNT.getText());
            cenaDodavkaNT = Double.parseDouble(txtCenaDodavkaNT.getText());
            cenaDistribuceNT = Double.parseDouble(txtCenaDistribuceNT.getText());
        } catch (NumberFormatException e) {
            showAlert("Chyba", "Neplatný formát hodnoty pro nízký tarif.");
            return;
        }
    }

    double dan = parseDouble(txtDan);
    double mesicniPoplatek = parseDouble(txtMesicniPoplatek);
    double poplatekRezervace = parseDouble(txtPoplatekRezervace);
    double systemoveSluzby = parseDouble(txtSystemoveSluzby);
    double kvetOzeDz = parseDouble(txtKVET);
    double ote = parseDouble(txtOTE);

    double cenaVT = (spotrebaVT * (cenaDodavkaVT + cenaDistribuceVT)) * months;
    double cenaNT = (spotrebaNT * (cenaDodavkaNT + cenaDistribuceNT)) * months;
    double celkovaCena = cenaVT + cenaNT;

    double celkovePoplatky = dan + mesicniPoplatek * months + poplatekRezervace + systemoveSluzby + kvetOzeDz + ote;

    celkovaCena += celkovePoplatky;
    celkovaCena = Math.round(celkovaCena); // Zaokrouhlení na celé koruny

    double zaplaceneZalohy = sliderPocetZaloh.getValue() * parseDouble(txtVyseZalohy);
    double nedoplatek = zaplaceneZalohy - celkovaCena;

    txtVysledek.setText("Celková cena: " + celkovaCena + " Kč");
    if (nedoplatek < 0) {
        txtNedoplatekPřeplatek.setText("Nedoplatek: " + Math.abs(nedoplatek) + " Kč");
        txtNedoplatekPřeplatek.setFill(Color.RED);
    } else {
        txtNedoplatekPřeplatek.setText("Přeplatek: " + Math.abs(nedoplatek) + " Kč");
        txtNedoplatekPřeplatek.setFill(Color.GREEN);
    }
}


    @FXML
    public void handleClose(ActionEvent event) {
        Platform.exit();
    }

    public void initialize() {
        checkVysokyTarif.setOnAction(e -> toggleVysokyTarifFields());
        checkNizkyTarif.setOnAction(e -> toggleNizkyTarifFields());

        toggleVysokyTarifFields();
        toggleNizkyTarifFields();
    }
}

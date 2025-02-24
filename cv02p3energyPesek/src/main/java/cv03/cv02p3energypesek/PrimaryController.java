package cv03.cv02p3energypesek;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.application.Platform;

public class PrimaryController {

    @FXML private Slider sliderMonths;
    @FXML private CheckBox checkVysokyTarif, checkNizkyTarif;
    @FXML private TextField txtSpotrebaVT, txtCenaDodavkaVT, txtCenaDistribuceVT;
    @FXML private TextField txtSpotrebaNT, txtCenaDodavkaNT, txtCenaDistribuceNT;
    @FXML private TextField txtDan, txtMesicniPoplatek, txtPoplatekRezervace, txtSystemoveSluzby, txtKVET, txtOTE;
    @FXML private Slider sliderPocetZaloh;
    @FXML private TextField txtVyseZalohy;
    @FXML private Text txtVysledek, txtNedoplatekPřeplatek;

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
        if (checkVysokyTarif.isSelected()) {
            txtSpotrebaVT.setDisable(false);
            txtCenaDodavkaVT.setDisable(false);
            txtCenaDistribuceVT.setDisable(false);
        } else {
            txtSpotrebaVT.setDisable(true);
            txtCenaDodavkaVT.setDisable(true);
            txtCenaDistribuceVT.setDisable(true);
        }
    }

    @FXML
    public void handleCheckBoxNT() {
        if (checkNizkyTarif.isSelected()) {
            txtSpotrebaNT.setDisable(false);
            txtCenaDodavkaNT.setDisable(false);
            txtCenaDistribuceNT.setDisable(false);
        } else {
            txtSpotrebaNT.setDisable(true);
            txtCenaDodavkaNT.setDisable(true);
            txtCenaDistribuceNT.setDisable(true);
        }
    }

    @FXML
    public void handleCalculate(ActionEvent event) {
        try {
            double pocetMesicu = sliderMonths.getValue();
            double spotrebaVT = Double.parseDouble(txtSpotrebaVT.getText());
            double cenaDodavkaVT = Double.parseDouble(txtCenaDodavkaVT.getText());
            double cenaDistribuceVT = Double.parseDouble(txtCenaDistribuceVT.getText());
            double spotrebaNT = Double.parseDouble(txtSpotrebaNT.getText());
            double cenaDodavkaNT = Double.parseDouble(txtCenaDodavkaNT.getText());
            double cenaDistribuceNT = Double.parseDouble(txtCenaDistribuceNT.getText());

            double celkovaCenaVT = (spotrebaVT * cenaDodavkaVT + spotrebaVT * cenaDistribuceVT) * pocetMesicu;
            double celkovaCenaNT = (spotrebaNT * cenaDodavkaNT + spotrebaNT * cenaDistribuceNT) * pocetMesicu;

            double celkovaCena = 0;
            if (checkVysokyTarif.isSelected()) {
                celkovaCena += celkovaCenaVT;
            }
            if (checkNizkyTarif.isSelected()) {
                celkovaCena += celkovaCenaNT;
            }

            double dan = Double.parseDouble(txtDan.getText());
            double mesicniPoplatek = Double.parseDouble(txtMesicniPoplatek.getText());
            double rezervace = Double.parseDouble(txtPoplatekRezervace.getText());
            double systemoveSluzby = Double.parseDouble(txtSystemoveSluzby.getText());
            double kvet = Double.parseDouble(txtKVET.getText());
            double ote = Double.parseDouble(txtOTE.getText());

            celkovaCena += dan + mesicniPoplatek * pocetMesicu + rezervace + systemoveSluzby + kvet + ote;

            double zaplaceno = Double.parseDouble(txtVyseZalohy.getText()) * sliderPocetZaloh.getValue();
            double nedoplatek = zaplaceno - celkovaCena;

            celkovaCena = Math.round(celkovaCena);
            nedoplatek = Math.round(nedoplatek);

            if (nedoplatek < 0) {
                txtVysledek.setText("Nedoplatek: " + (-nedoplatek) + " Kč");
                txtVysledek.setStyle("-fx-text-fill: red;");
            } else {
                txtVysledek.setText("Přeplatek: " + nedoplatek + " Kč");
                txtVysledek.setStyle("-fx-text-fill: green;");
            }
        } catch (NumberFormatException e) {
            txtVysledek.setText("Chyba ve vstupu");
            txtVysledek.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void handleClose(ActionEvent event) {
        Platform.exit();
    }
}

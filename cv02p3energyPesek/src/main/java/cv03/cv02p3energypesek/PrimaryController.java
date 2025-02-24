package cv03.cv02p3energypesek;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class PrimaryController {

    @FXML
    private TextField txtSpotrebaVT, txtCenaDodavkaVT, txtCenaDistribuceVT;
    @FXML
    private TextField txtSpotrebaNT, txtCenaDodavkaNT, txtCenaDistribuceNT;
    @FXML
    private TextField txtDan, txtMesicniPoplatek, txtPoplatekRezervace, txtSystemoveSluzby, txtKVET, txtOTE;
    @FXML
    private TextField txtVyseZalohy;
    @FXML
    private Label lblCelkovaCena, lblNedoplatek;
    @FXML
    private Slider sliderPocetMesicu, sliderPocetZaloh;

    @FXML
    private void vypocet() {
        try {
            double spotrebaVT = Double.parseDouble(txtSpotrebaVT.getText());
            double cenaDodavkaVT = Double.parseDouble(txtCenaDodavkaVT.getText());
            double cenaDistribuceVT = Double.parseDouble(txtCenaDistribuceVT.getText());

            double spotrebaNT = Double.parseDouble(txtSpotrebaNT.getText());
            double cenaDodavkaNT = Double.parseDouble(txtCenaDodavkaNT.getText());
            double cenaDistribuceNT = Double.parseDouble(txtCenaDistribuceNT.getText());

            double dan = Double.parseDouble(txtDan.getText());
            double mesicniPoplatek = Double.parseDouble(txtMesicniPoplatek.getText());
            double poplatekRezervace = Double.parseDouble(txtPoplatekRezervace.getText());
            double systemoveSluzby = Double.parseDouble(txtSystemoveSluzby.getText());
            double kvet = Double.parseDouble(txtKVET.getText());
            double ote = Double.parseDouble(txtOTE.getText());

            int pocetMesicu = (int) sliderPocetMesicu.getValue();
            double vyseZalohy = Double.parseDouble(txtVyseZalohy.getText());
            int pocetZaloh = (int) sliderPocetZaloh.getValue();

            double celkovaCena = (spotrebaVT * (cenaDodavkaVT + cenaDistribuceVT)) +
                                 (spotrebaNT * (cenaDodavkaNT + cenaDistribuceNT)) +
                                 (dan + mesicniPoplatek + poplatekRezervace + systemoveSluzby + kvet + ote) * pocetMesicu;

            celkovaCena = Math.round(celkovaCena);

            double zaplaceno = vyseZalohy * pocetZaloh;
            double rozdil = celkovaCena - zaplaceno;

            lblCelkovaCena.setText(String.format("%.0f Kč", celkovaCena));

            if (rozdil < 0) {
                lblNedoplatek.setText(String.format("Přeplatek: %.0f Kč", -rozdil));
                lblNedoplatek.setTextFill(Color.GREEN);
            } else if (rozdil > 0) {
                lblNedoplatek.setText(String.format("Nedoplatek: %.0f Kč", rozdil));
                lblNedoplatek.setTextFill(Color.RED);
            } else {
                lblNedoplatek.setText("Vyrovnáno");
                lblNedoplatek.setTextFill(Color.BLACK);
            }

        } catch (NumberFormatException e) {
            lblCelkovaCena.setText("Chyba");
            lblNedoplatek.setText("Chyba");
            lblNedoplatek.setTextFill(Color.RED);
        }
    }

    @FXML
    private void ukoncit() {
        Platform.exit();
    }
}
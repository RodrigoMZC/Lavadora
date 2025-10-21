package org.rmr.lavadora.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.rmr.lavadora.controller.FuzzyController;

import java.util.Map;

public class LavadoraViewController {
    // --- Cargar los componentes FXML de la vista --- \\d
    @FXML
    private Slider sliderSuciedad;
    @FXML
    private Slider sliderTejido;
    @FXML
    private Slider sliderPeso;
    @FXML
    private Button calcularButton;
    @FXML
    private TextField textResultadoAgua;
    @FXML
    private TextField textResultadoTiempo;
    @FXML
    private TextField textResultadoVelocidad;

        // -- Controlador -- \\
    private FuzzyController fuzzyController;

    // --- Inicializa la logica al ejecutar la app --- \\
    @FXML
    public void initialize() {
        this.fuzzyController = new FuzzyController();
    }

    // --- Metodo para calcular --- \\
    @FXML
    protected void onCalcularButtonClick() {
        double suciedad = sliderSuciedad.getValue();
        double tejido = sliderTejido.getValue();
        double peso = sliderPeso.getValue();

        Map<String, Double> resultados = fuzzyController.calculateOutputs(suciedad, tejido, peso);

        textResultadoAgua.setText(String.format("%.1f Litros", resultados.get("agua")));
        textResultadoTiempo.setText(String.format("%.1f Minutos", resultados.get("tiempo")));
        textResultadoVelocidad.setText(String.format("%.0f RPM", resultados.get("velocidad")));
    }
}
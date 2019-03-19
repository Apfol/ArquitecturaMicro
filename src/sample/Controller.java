package sample;

import entidades.Operacion;
import entidades.Registro;
import entidades.RegistroMemoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static Registro SEPARADOR = new Registro("-------------");

    @FXML private TableView<Operacion> decodificadorTab;
    @FXML private TableColumn<Operacion, String> instruccionesCol;
    @FXML private TableColumn<Operacion, String> operacionesCol;
    @FXML private TableColumn<Operacion, String> comentariosCol;

    @FXML private TableView<RegistroMemoria> memoriaTab;
    @FXML private TableColumn<RegistroMemoria, String> direccionesCol;
    @FXML private TableColumn<RegistroMemoria, String> contenidoCol;

    @FXML private TableView<Registro> contadorTab;
    @FXML private TableColumn<Registro, String> contadorCol;

    @FXML private TableView<Registro> acumuladorTab;
    @FXML private TableColumn<Registro, String> acumuladorCol;

    @FXML private TableView<Registro> entradaTab;
    @FXML private TableColumn<Registro, String> entradaCol;

    @FXML private TableView<Registro> registroDatosTab;
    @FXML private TableColumn<Registro, String> registroDatosCol;

    @FXML private TableView<Registro> registroDireccionesTab;
    @FXML private TableColumn<Registro, String> registroDireccionesCol;

    @FXML private TableView<Registro> registroInstruccionesTab;
    @FXML private TableColumn<Registro, String> registroInstruccionesCol;

    final ObservableList<RegistroMemoria> registrosMemoriaList() {
        ObservableList<RegistroMemoria> registroMemoria = FXCollections.observableArrayList();
        for (int i = 0; i < 63; i++) {
            registroMemoria.add(new RegistroMemoria(binarioSeisBits(i), ""));
        }
        return registroMemoria;
    }
    private final ObservableList<Registro> registrosContador = FXCollections.observableArrayList();
    private final ObservableList<Registro> registrosDirecciones = FXCollections.observableArrayList();
    private final ObservableList<Registro> registrosInstrucciones = FXCollections.observableArrayList();
    private final ObservableList<Registro> registrosDatos = FXCollections.observableArrayList();
    private final ObservableList<Registro> registrosEntrada = FXCollections.observableArrayList(
            new Registro("000000000000")
    );
    private final ObservableList<Registro> registrosAcumulador = FXCollections.observableArrayList(
            new Registro("000000000000")
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Decodificador
        instruccionesCol.setCellValueFactory(new PropertyValueFactory<Operacion, String>("Instruccion"));
        operacionesCol.setCellValueFactory(new PropertyValueFactory<Operacion, String>("Operacion"));
        comentariosCol.setCellValueFactory(new PropertyValueFactory<Operacion, String>("Comentario"));
        decodificadorTab.setItems(operacionesList);

        //Memoria
        direccionesCol.setCellValueFactory(new PropertyValueFactory<RegistroMemoria, String>("Direccion"));
        contenidoCol.setCellValueFactory(new PropertyValueFactory<RegistroMemoria, String>("Contenido"));
        memoriaTab.setItems(registrosMemoriaList());

        //Contador
        contadorCol.setCellValueFactory(new PropertyValueFactory<Registro, String>("Registro"));
        contadorTab.setItems(registrosContador);

        //Registro direcciones
        registroDireccionesCol.setCellValueFactory(new PropertyValueFactory<Registro, String>("Registro"));
        registroDireccionesTab.setItems(registrosDirecciones);

        //Registro instrucciones
        registroInstruccionesCol.setCellValueFactory(new PropertyValueFactory<Registro, String>("Registro"));
        registroInstruccionesTab.setItems(registrosInstrucciones);

        //Registro datos
        registroDatosCol.setCellValueFactory(new PropertyValueFactory<Registro, String>("Registro"));
        registroDatosTab.setItems(registrosDatos);

        //Entrada
        entradaCol.setCellValueFactory(new PropertyValueFactory<Registro, String>("Registro"));
        entradaTab.setItems(registrosEntrada);

        //Acumulador
        acumuladorCol.setCellValueFactory(new PropertyValueFactory<Registro, String>("Registro"));
        acumuladorTab.setItems(registrosAcumulador);

    }

    final ObservableList<Operacion> operacionesList = FXCollections.observableArrayList(
            new Operacion("000000", "+", "suma"),
            new Operacion("000001", "-", "Resta"),
            new Operacion("000010", "/", "división"),
            new Operacion("000011", "^", "Potencia"),
            new Operacion("000100", "OR", "Operación OR"),
            new Operacion("000101", "XOR", "Operación XOR"),
            new Operacion("000110", "M", "Mover a memoria"),
            new Operacion("000111", "F", "Finalizar")
    );

    public void onClickContador() {

        int cantidadContador = 0;

        for (Registro registro : registrosContador) {
            if (registro.getRegistro().equals(SEPARADOR.getRegistro())) {
                cantidadContador++;
            }
        }

        registrosContador.add(new Registro(binarioSeisBits(cantidadContador)));
        String contadorActual = registrosContador.get(registrosContador.size() - 1).getRegistro();

        registrosDirecciones.add(new Registro(contadorActual));
        String direccionActual = registrosDirecciones.get(registrosDirecciones.size() - 1).getRegistro();


        String contenidoMemoria = getContenidoMemoria(direccionActual);
        String instruccionEnMemoria = contenidoMemoria.substring(0, (contenidoMemoria.length()/2));
        String direccionEnMemoria = contenidoMemoria.substring((contenidoMemoria.length()/2));

        registrosInstrucciones.add(new Registro(instruccionEnMemoria));

        registrosDatos.add(new Registro(contenidoMemoria));

        registrosDirecciones.add(new Registro(direccionEnMemoria));

        contenidoMemoria = getContenidoMemoria(direccionEnMemoria);

        registrosEntrada.add(new Registro(contenidoMemoria));

        registrosDatos.add(new Registro(contenidoMemoria));

        realizarOperacion(instruccionEnMemoria);

        registrosDatos.add(SEPARADOR);
        registrosInstrucciones.add(SEPARADOR);
        registrosDirecciones.add(SEPARADOR);
        registrosContador.add(SEPARADOR);
        registrosEntrada.add(SEPARADOR);
        registrosAcumulador.add(SEPARADOR);
    }

    String binarioSeisBits(int decimal) {
        String cadenaCeros = "";
        String binario = Integer.toBinaryString(decimal);
        if (binario.length() < 7) {
            for (int j = 0; j < 6 - binario.length(); j++) {
                cadenaCeros += "0";
            }
        }
        String binarioSeis = cadenaCeros + binario;
        cadenaCeros = "";
        return binarioSeis;
    }

    private String getContenidoMemoria(String direccion) {
        for (int i = 0; i < registrosMemoriaList().size(); i++) {
            if (memoriaTab.getItems().get(i).getDireccion().equals(direccion)) {
                return memoriaTab.getItems().get(i).getContenido().getText();
            }
        }
        return null;
    }

    private void realizarOperacion(String instruccion) {
        switch (instruccion) {
            case "000000":
                suma();
                break;
            case "000001":
                resta();
                break;
            case "000010":
                division();
                break;
            case "000011":
                potencia();
                break;
            case "000100":
                or();
                break;
            case "000101":
                xor();
                break;
            case "000110":
                moverMemoria();
                break;
            case "000111":
                finalizar();
                break;
        }
    }

    private void finalizar() {

    }

    private void moverMemoria() {
        
    }

    private void xor() {

    }

    private void or() {

    }

    private void potencia() {
        String ultimaEntrada = registrosEntrada.get(registrosEntrada.size() - 1).getRegistro();
        String utlimaAcumulador = ultimoAcmuladorSinSeparador(registrosAcumulador);

        int decimalEntrada = Integer.parseInt(ultimaEntrada, 2);
        int decimalAcumulador = Integer.parseInt(utlimaAcumulador, 2);

        registrosAcumulador.add(new Registro(Integer.toBinaryString(decimalAcumulador ^ decimalEntrada)));
    }

    private void division() {
        String ultimaEntrada = registrosEntrada.get(registrosEntrada.size() - 1).getRegistro();
        String utlimaAcumulador = ultimoAcmuladorSinSeparador(registrosAcumulador);

        int decimalEntrada = Integer.parseInt(ultimaEntrada, 2);
        int decimalAcumulador = Integer.parseInt(utlimaAcumulador, 2);

        registrosAcumulador.add(new Registro(Integer.toBinaryString(decimalAcumulador / decimalEntrada)));
    }

    private void resta() {
        String ultimaEntrada = registrosEntrada.get(registrosEntrada.size() - 1).getRegistro();
        String utlimaAcumulador = ultimoAcmuladorSinSeparador(registrosAcumulador);

        int decimalEntrada = Integer.parseInt(ultimaEntrada, 2);
        int decimalAcumulador = Integer.parseInt(utlimaAcumulador, 2);

        registrosAcumulador.add(new Registro(Integer.toBinaryString(decimalAcumulador - decimalEntrada)));
    }

    private void suma() {
        String ultimaEntrada = registrosEntrada.get(registrosEntrada.size() - 1).getRegistro();
        String utlimaAcumulador = ultimoAcmuladorSinSeparador(registrosAcumulador);

        int decimalEntrada = Integer.parseInt(ultimaEntrada, 2);
        int decimalAcumulador = Integer.parseInt(utlimaAcumulador, 2);

        registrosAcumulador.add(new Registro(Integer.toBinaryString(decimalAcumulador + decimalEntrada)));
    }

    public String ultimoAcmuladorSinSeparador(ObservableList<Registro> acumulados) {
        ObservableList<Registro> acumuladosSinSeparador = FXCollections.observableArrayList();
        for(Registro r: acumulados) {
            if(!r.getRegistro().equals(SEPARADOR.getRegistro())) {
                acumuladosSinSeparador.add(r);
            }
        }
        return acumuladosSinSeparador.get(acumuladosSinSeparador.size() - 1).getRegistro();
    }

}

package com.alvarorf.vehiculosmvc.gui;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

import java.awt.Color;

public class Ventana {

    private JPanel panel1;
    public JRadioButton kitRadioButton;
    public JRadioButton productoQuimicoRadioButton;
    public JRadioButton componenteRadioButton;
    public JRadioButton moduloRadioButton;
    public JRadioButton otroProductoRadioButton;
    public JTextField codigoBarrasTxt;
    public JTextField fabricanteTxt;
    public JTextField modeloTxt;
    public JTextField precioTxt;
    public JRadioButton enStockRadioButton;
    public JRadioButton descatalogadoRadioButton;
    public JButton nuevoButton;
    public JButton exportarButton;
    public JButton importarButton;
    public JList list1;

    // Campos ProductoQuimico
    public JLabel precaucionesLbl;
    public JTextField precaucionesTxt;
    public JLabel fechaCaducidadLbl;
    public DatePicker fechaCaducidadDPicker;

    // Campos Kit
    public JLabel edadMinimaLbl;
    public JTextField edadMinimaTxt;
    public JLabel nivelLbl;
    public JComboBox nivelCombo;

    // Campos Componente
    public JLabel valorLbl;
    public JTextField valorTxt;
    public JLabel valor2Lbl;
    public JTextField valor2Txt;

    // Campos Modulo
    public JLabel aplicacionLbl;
    public JTextField aplicacionTxt;

    // Campos OtroProducto
    public JLabel notasLbl;
    public JTextField notasTxt;

    //declarado por mi
    public JFrame frame;

    //para poner los datos de productos en la lista
    public DefaultListModel dlmProducto;

    // Área de texto para mostrar detalles del producto
    public JTextArea detallesArea;

    public Ventana() {
        frame = new JFrame("RoboShop");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.setSize(800, 450);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        initComponents();
    }

    public void initComponents() {
        dlmProducto = new DefaultListModel();
        list1.setModel(dlmProducto);

        nivelCombo.addItem("principiante");
        nivelCombo.addItem("intermedio");
        nivelCombo.addItem("avanzado");

        Color colorFondoCampos = new Color(255, 255, 230);

        // Aplicar color a campos comunes
        codigoBarrasTxt.setBackground(colorFondoCampos);
        fabricanteTxt.setBackground(colorFondoCampos);
        modeloTxt.setBackground(colorFondoCampos);
        precioTxt.setBackground(colorFondoCampos);

        // ProductoQuimico
        precaucionesTxt.setBackground(colorFondoCampos);

        // Kit
        edadMinimaTxt.setBackground(colorFondoCampos);

        // Componente
        valorTxt.setBackground(colorFondoCampos);
        valor2Txt.setBackground(colorFondoCampos);

        // Modulo
        aplicacionTxt.setBackground(colorFondoCampos);

        // OtroProducto
        notasTxt.setBackground(colorFondoCampos);

        // Colores de botones
        nuevoButton.setBackground(new Color(100, 150, 255));
        nuevoButton.setForeground(Color.WHITE);
        importarButton.setBackground(new Color(100, 150, 255));
        importarButton.setForeground(Color.WHITE);
        exportarButton.setBackground(new Color(100, 150, 255));
        exportarButton.setForeground(Color.WHITE);

        // Configurar área de detalles
        if (detallesArea != null) {
            detallesArea.setEditable(false);
            detallesArea.setLineWrap(true);
            detallesArea.setWrapStyleWord(true);
        }

        // Ocultar todos los campos específicos al inicio
        ocultarTodosCamposEspecificos();
    }

    public void ocultarTodosCamposEspecificos() {
        // ProductoQuimico
        precaucionesLbl.setVisible(false);
        precaucionesTxt.setVisible(false);
        fechaCaducidadLbl.setVisible(false);
        fechaCaducidadDPicker.setVisible(false);

        // Kit
        edadMinimaLbl.setVisible(false);
        edadMinimaTxt.setVisible(false);
        nivelLbl.setVisible(false);
        nivelCombo.setVisible(false);

        // Componente
        valorLbl.setVisible(false);
        valorTxt.setVisible(false);
        valor2Lbl.setVisible(false);
        valor2Txt.setVisible(false);

        // Modulo
        aplicacionLbl.setVisible(false);
        aplicacionTxt.setVisible(false);

        // OtroProducto
        notasLbl.setVisible(false);
        notasTxt.setVisible(false);
    }

    public void mostrarCamposProductoQuimico() {
        ocultarTodosCamposEspecificos();
        precaucionesLbl.setVisible(true);
        precaucionesTxt.setVisible(true);
        fechaCaducidadLbl.setVisible(true);
        fechaCaducidadDPicker.setVisible(true);
    }

    public void mostrarCamposKit() {
        ocultarTodosCamposEspecificos();
        edadMinimaLbl.setVisible(true);
        edadMinimaTxt.setVisible(true);
        nivelLbl.setVisible(true);
        nivelCombo.setVisible(true);
    }

    public void mostrarCamposComponente() {
        ocultarTodosCamposEspecificos();
        valorLbl.setVisible(true);
        valorTxt.setVisible(true);
        valor2Lbl.setVisible(true);
        valor2Txt.setVisible(true);
    }

    public void mostrarCamposModulo() {
        ocultarTodosCamposEspecificos();
        aplicacionLbl.setVisible(true);
        aplicacionTxt.setVisible(true);
    }

    public void mostrarCamposOtroProducto() {
        ocultarTodosCamposEspecificos();
        notasLbl.setVisible(true);
        notasTxt.setVisible(true);
    }
}

package com.alvarorf.vehiculosmvc.gui;

import com.alvarorf.vehiculosmvc.base.*;
import com.alvarorf.vehiculosmvc.util.Util;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.Properties;

public class ProductosControlador implements ActionListener, ListSelectionListener, WindowListener {

    private Ventana vista;
    private ProductosModelo modelo;
    private File ultimaRutaExportada;
    private File ultimaRutaImportada;

    public ProductosControlador(Ventana vista, ProductosModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        try {
            cargarDatosConfiguracion();
        } catch (IOException e) {
            System.out.println("No existe fichero de configuracion");
            // Inicializar con ruta por defecto
            ultimaRutaExportada = new File(System.getProperty("user.home"));
            ultimaRutaImportada = new File(System.getProperty("user.home"));
        }

        //listener al arrancar el controlador
        addActionListener(this);
        addWindowListener(this);
        addListSelectionListener(this);
        // Seleccionar Kit por defecto
        vista.kitRadioButton.setSelected(true);
        vista.mostrarCamposKit();
    }

    private boolean hayCamposVacios() {
        if (vista.codigoBarrasTxt.getText().isEmpty() ||
                vista.modeloTxt.getText().isEmpty() ||
                vista.fabricanteTxt.getText().isEmpty() ||
                vista.precioTxt.getText().isEmpty()) {
            return true;
        }

        // Verificar campos específicos según tipo
        if (vista.productoQuimicoRadioButton.isSelected()) {
            if (vista.precaucionesTxt.getText().isEmpty() ||
                    vista.fechaCaducidadDPicker.getText().isEmpty()) {
                return true;
            }
        } else if (vista.kitRadioButton.isSelected()) {
            if (vista.edadMinimaTxt.getText().isEmpty()) {
                return true;
            }
        } else if (vista.componenteRadioButton.isSelected()) {
            if (vista.valorTxt.getText().isEmpty() ||
                    vista.valor2Txt.getText().isEmpty()) {
                return true;
            }
        } else if (vista.moduloRadioButton.isSelected()) {
            if (vista.aplicacionTxt.getText().isEmpty()) {
                return true;
            }
        } else if (vista.otroProductoRadioButton.isSelected()) {
            if (vista.notasTxt.getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void limpiarCampos() {
        vista.codigoBarrasTxt.setText(null);
        vista.fabricanteTxt.setText(null);
        vista.modeloTxt.setText(null);
        vista.precioTxt.setText(null);
        vista.enStockRadioButton.setSelected(false);
        vista.descatalogadoRadioButton.setSelected(false);
        vista.precaucionesTxt.setText(null);
        vista.fechaCaducidadDPicker.setText(null);
        vista.edadMinimaTxt.setText(null);
        vista.nivelCombo.setSelectedIndex(0);
        vista.valorTxt.setText(null);
        vista.valor2Txt.setText(null);
        vista.aplicacionTxt.setText(null);
        vista.notasTxt.setText(null);
    }

    //listener botones
    private void addActionListener(ActionListener listener) {
        vista.importarButton.addActionListener(listener);
        vista.exportarButton.addActionListener(listener);
        vista.nuevoButton.addActionListener(listener);
        vista.kitRadioButton.addActionListener(listener);
        vista.productoQuimicoRadioButton.addActionListener(listener);
        vista.componenteRadioButton.addActionListener(listener);
        vista.moduloRadioButton.addActionListener(listener);
        vista.otroProductoRadioButton.addActionListener(listener);
    }

    //listener ventana (boton cerrar)
    private void addWindowListener(WindowListener listener) {
        vista.frame.addWindowListener(listener);
    }

    //listener de la lista
    private void addListSelectionListener(ListSelectionListener listener) {
        vista.list1.addListSelectionListener(listener);
    }

    public void refrescar() {
        vista.dlmProducto.clear();
        //modelo.obtenerProductos contiene la lista de productos
        for (Producto unProducto : modelo.obtenerProductos()) {
            vista.dlmProducto.addElement(unProducto);
        }
    }


    private void cargarDatosConfiguracion() throws IOException {
        Properties configuracion = new Properties();
        configuracion.load(new FileReader("productos.conf"));
        ultimaRutaExportada = new File(configuracion.getProperty("ultimaRutaExportada"));

        // Cargar la ruta de importación si existe
        String rutaImportada = configuracion.getProperty("ultimaRutaImportada");
        if (rutaImportada != null && !rutaImportada.isEmpty()) {
            ultimaRutaImportada = new File(rutaImportada);
        } else {
            ultimaRutaImportada = new File(System.getProperty("user.home"));
        }
    }

    private void actualizarDatosConfiguracion(File ultimaRutaExportada, File ultimaRutaImportada) {
        if (ultimaRutaExportada != null) {
            this.ultimaRutaExportada = ultimaRutaExportada;
        }
        if (ultimaRutaImportada != null) {
            this.ultimaRutaImportada = ultimaRutaImportada;
        }
    }

    private void guardarConfiguracion() throws IOException {
        Properties configuracion = new Properties();
        // Si ultimaRutaExportada es null,usar home
        String rutaExportadaAGuardar = (ultimaRutaExportada != null) ?
                ultimaRutaExportada.getAbsolutePath() :
                System.getProperty("user.home");

        String rutaImportadaAGuardar = (ultimaRutaImportada != null) ?
                ultimaRutaImportada.getAbsolutePath() :
                System.getProperty("user.home");

        configuracion.setProperty("ultimaRutaExportada", rutaExportadaAGuardar);
        configuracion.setProperty("ultimaRutaImportada", rutaImportadaAGuardar);
        configuracion.store(new PrintWriter("productos.conf"), "Datos configuracion productos");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Nuevo":
                if (hayCamposVacios()) {
                    Util.mensajeError("Hay campos vacíos obligatorios");
                    break;
                }

                if (modelo.existeCodigoBarras(vista.codigoBarrasTxt.getText())) {
                    Util.mensajeError("Ya existe un producto con el codigo de barras" +
                            "\n" + vista.codigoBarrasTxt.getText());
                    break;
                }

                double precio = Double.parseDouble(vista.precioTxt.getText());
                boolean enStock = vista.enStockRadioButton.isSelected();
                boolean descatalogado = vista.descatalogadoRadioButton.isSelected();
                if (vista.kitRadioButton.isSelected()) {
                    modelo.altaKit(vista.codigoBarrasTxt.getText(),
                            vista.fabricanteTxt.getText(),
                            vista.modeloTxt.getText(),
                            precio,
                            enStock,
                            descatalogado,
                            Integer.parseInt(vista.edadMinimaTxt.getText()),
                            (String) vista.nivelCombo.getSelectedItem());
                } else if (vista.productoQuimicoRadioButton.isSelected()) {
                    modelo.altaProductoQuimico(vista.codigoBarrasTxt.getText(),
                            vista.fabricanteTxt.getText(),
                            vista.modeloTxt.getText(),
                            precio,
                            enStock,
                            descatalogado,
                            vista.precaucionesTxt.getText(),
                            vista.fechaCaducidadDPicker.getDate());
                } else if (vista.componenteRadioButton.isSelected()) {
                    modelo.altaComponente(vista.codigoBarrasTxt.getText(),
                            vista.fabricanteTxt.getText(),
                            vista.modeloTxt.getText(),
                            precio,
                            enStock,
                            descatalogado,
                            vista.valorTxt.getText(),
                            vista.valor2Txt.getText());
                } else if (vista.moduloRadioButton.isSelected()) {
                    modelo.altaModulo(vista.codigoBarrasTxt.getText(),
                            vista.fabricanteTxt.getText(),
                            vista.modeloTxt.getText(),
                            precio,
                            enStock,
                            descatalogado,
                            vista.aplicacionTxt.getText());
                } else if (vista.otroProductoRadioButton.isSelected()) {
                    modelo.altaOtroProducto(vista.codigoBarrasTxt.getText(),
                            vista.fabricanteTxt.getText(),
                            vista.modeloTxt.getText(),
                            precio,
                            enStock,
                            descatalogado,
                            vista.notasTxt.getText());
                }

                limpiarCampos();
                refrescar();
                break;
            case "Importar":
                JFileChooser selectorFichero = Util.crearSelectorFichero(ultimaRutaImportada, "Archivos XML", "xml");
                int opt = selectorFichero.showOpenDialog(null);
                if (opt == JFileChooser.APPROVE_OPTION) {
                    try {
                        modelo.importarXML(selectorFichero.getSelectedFile());
                        actualizarDatosConfiguracion(null, selectorFichero.getSelectedFile().getParentFile());
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    }
                }
                refrescar();

                break;
            case "Exportar":
                JFileChooser selectorFichero2 = Util.crearSelectorFichero(ultimaRutaExportada, "Archivos XML", "xml");
                int opt2 = selectorFichero2.showSaveDialog(null);
                if (opt2 == JFileChooser.APPROVE_OPTION) {
                    try {
                        modelo.exportarXML(selectorFichero2.getSelectedFile());
                        actualizarDatosConfiguracion(selectorFichero2.getSelectedFile().getParentFile(), null);
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (TransformerException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "ProductoQuimico":
                vista.mostrarCamposProductoQuimico();
                break;
            case "Kit":
                vista.mostrarCamposKit();
                break;
            case "Componente":
                vista.mostrarCamposComponente();
                break;
            case "Módulo":
                vista.mostrarCamposModulo();
                break;
            case "Otro":
                vista.mostrarCamposOtroProducto();
                break;
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int resp = Util.mensajeConfirmacion("¿Desea cerrar la ventana?", "Salir");
        if (resp == JOptionPane.OK_OPTION) {
            try {
                guardarConfiguracion();
                System.exit(0);
            } catch (IOException ex) {
                ex.printStackTrace();
                // cerrar la aplicación, aunque falle
                System.exit(0);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            Producto productoSeleccionado = (Producto) vista.list1.getSelectedValue();
            vista.codigoBarrasTxt.setText(productoSeleccionado.getCodigoBarras());
            vista.modeloTxt.setText(productoSeleccionado.getModelo());
            vista.fabricanteTxt.setText(productoSeleccionado.getFabricante());
            vista.precioTxt.setText(String.valueOf(productoSeleccionado.getPrecio()));
            vista.enStockRadioButton.setSelected(productoSeleccionado.isEnStock());
            vista.descatalogadoRadioButton.setSelected(productoSeleccionado.isDescatalogado());
            if (productoSeleccionado instanceof Kit) {
                vista.kitRadioButton.doClick();
                vista.edadMinimaTxt.setText(String.valueOf(((Kit) productoSeleccionado).getEdadMinima()));
                vista.nivelCombo.setSelectedItem(((Kit) productoSeleccionado).getNivel());
            } else if (productoSeleccionado instanceof ProductoQuimico) {
                vista.productoQuimicoRadioButton.doClick();
                vista.precaucionesTxt.setText(((ProductoQuimico) productoSeleccionado).getPrecauciones());
                vista.fechaCaducidadDPicker.setDate(((ProductoQuimico) productoSeleccionado).getFechaCaducidad());
            } else if (productoSeleccionado instanceof Componente) {
                vista.componenteRadioButton.doClick();
                vista.valorTxt.setText(((Componente) productoSeleccionado).getValor());
                vista.valor2Txt.setText(((Componente) productoSeleccionado).getValor2());
            } else if (productoSeleccionado instanceof Modulo) {
                vista.moduloRadioButton.doClick();
                vista.aplicacionTxt.setText(((Modulo) productoSeleccionado).getAplicacion());
            } else if (productoSeleccionado instanceof OtroProducto) {
                vista.otroProductoRadioButton.doClick();
                vista.notasTxt.setText(((OtroProducto) productoSeleccionado).getNotas());
            }
        }
    }

    //no los uso
    @Override
    public void windowOpened(WindowEvent e) {
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

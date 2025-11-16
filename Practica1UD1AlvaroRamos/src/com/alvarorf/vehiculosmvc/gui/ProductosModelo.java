package com.alvarorf.vehiculosmvc.gui;

import com.alvarorf.vehiculosmvc.base.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProductosModelo {
    private ArrayList<Producto> listaProductos;

    public ProductosModelo() {
        listaProductos = new ArrayList<Producto>();
    }

    public ArrayList<Producto> obtenerProductos() {
        return listaProductos;
    }

    //altaKit
    public void altaKit(String codigoBarras, String fabricante, String modelo, double precio, boolean enStock, boolean descatalogado, int edadMinima, String nivel) {
        Kit nuevoKit = new Kit(codigoBarras, modelo, fabricante, precio, enStock, descatalogado, edadMinima, nivel);
        listaProductos.add(nuevoKit);
    }

    //altaProductoQuimico
    public void altaProductoQuimico(String codigoBarras, String fabricante, String modelo, double precio, boolean enStock, boolean descatalogado, String precauciones, LocalDate fechaCaducidad) {
        ProductoQuimico nuevoProductoQuimico = new ProductoQuimico(codigoBarras, modelo, fabricante, precio, enStock, descatalogado, precauciones, fechaCaducidad);
        listaProductos.add(nuevoProductoQuimico);
    }

    //altaComponente
    public void altaComponente(String codigoBarras, String fabricante, String modelo, double precio, boolean enStock, boolean descatalogado, String valor, String valor2) {
        Componente nuevoComponente = new Componente(codigoBarras, modelo, fabricante, precio, enStock, descatalogado, valor, valor2);
        listaProductos.add(nuevoComponente);
    }

    //altaModulo
    public void altaModulo(String codigoBarras, String fabricante, String modelo, double precio, boolean enStock, boolean descatalogado, String aplicacion) {
        Modulo nuevoModulo = new Modulo(codigoBarras, modelo, fabricante, precio, enStock, descatalogado, aplicacion);
        listaProductos.add(nuevoModulo);
    }

    //altaOtroProducto
    public void altaOtroProducto(String codigoBarras, String fabricante, String modelo, double precio, boolean enStock, boolean descatalogado, String notas) {
        OtroProducto nuevoOtroProducto = new OtroProducto(codigoBarras, modelo, fabricante, precio, enStock, descatalogado, notas);
        listaProductos.add(nuevoOtroProducto);
    }

    //existeCodigoBarras
    public boolean existeCodigoBarras(String codigoBarras) {
        for (Producto unProducto : listaProductos) {
            if (unProducto.getCodigoBarras().equals(codigoBarras)) {
                return true;
            }
        }
        return false;
    }

    //exportarXML
    public void exportarXML(File fichero) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        //implementacion DOM -> web
        DOMImplementation dom = builder.getDOMImplementation();
        Document documento = dom.createDocument(null, "xml", null);
        //añado el nodo raiz (primera etiqueta)
        Element raiz = documento.createElement("Productos");
        documento.getDocumentElement().appendChild(raiz);

        Element nodoProducto = null;
        Element nodoDatos = null;
        Text texto = null;

        //voy a añadir una etiqueta dentro de producto
        //en funcion del tipo de producto
        for (Producto unProducto : listaProductos) {
            if (unProducto instanceof Kit) {
                nodoProducto = documento.createElement("Kit");
            } else if (unProducto instanceof ProductoQuimico) {
                nodoProducto = documento.createElement("ProductoQuimico");
            } else if (unProducto instanceof Componente) {
                nodoProducto = documento.createElement("Componente");
            } else if (unProducto instanceof Modulo) {
                nodoProducto = documento.createElement("Modulo");
            } else if (unProducto instanceof OtroProducto) {
                nodoProducto = documento.createElement("OtroProducto");
            }
            raiz.appendChild(nodoProducto);

            //atributos comunes (codigoBarras,fabricante,modelo, precio)
            nodoDatos = documento.createElement("codigo-barras");
            nodoProducto.appendChild(nodoDatos);
            texto = documento.createTextNode(unProducto.getCodigoBarras());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("fabricante");
            nodoProducto.appendChild(nodoDatos);
            texto = documento.createTextNode(unProducto.getFabricante());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("modelo");
            nodoProducto.appendChild(nodoDatos);
            texto = documento.createTextNode(unProducto.getModelo());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("precio");
            nodoProducto.appendChild(nodoDatos);
            texto = documento.createTextNode(String.valueOf(unProducto.getPrecio()));
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("enStock");
            nodoProducto.appendChild(nodoDatos);
            texto = documento.createTextNode(String.valueOf(unProducto.isEnStock()));
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("descatalogado");
            nodoProducto.appendChild(nodoDatos);
            texto = documento.createTextNode(String.valueOf(unProducto.isDescatalogado()));
            nodoDatos.appendChild(texto);

            //campos especificos segun tipo
            if (unProducto instanceof Kit) {
                nodoDatos = documento.createElement("edad-minima");
                nodoProducto.appendChild(nodoDatos);
                texto = documento.createTextNode(String.valueOf(((Kit) unProducto).getEdadMinima()));
                nodoDatos.appendChild(texto);

                nodoDatos = documento.createElement("nivel");
                nodoProducto.appendChild(nodoDatos);
                texto = documento.createTextNode(((Kit) unProducto).getNivel());
                nodoDatos.appendChild(texto);
            } else if (unProducto instanceof ProductoQuimico) {
                nodoDatos = documento.createElement("precauciones");
                nodoProducto.appendChild(nodoDatos);
                texto = documento.createTextNode(((ProductoQuimico) unProducto).getPrecauciones());
                nodoDatos.appendChild(texto);

                nodoDatos = documento.createElement("fecha-caducidad");
                nodoProducto.appendChild(nodoDatos);
                texto = documento.createTextNode(String.valueOf(((ProductoQuimico) unProducto).getFechaCaducidad()));
                nodoDatos.appendChild(texto);
            } else if (unProducto instanceof Componente) {
                nodoDatos = documento.createElement("valor");
                nodoProducto.appendChild(nodoDatos);
                texto = documento.createTextNode(((Componente) unProducto).getValor());
                nodoDatos.appendChild(texto);

                nodoDatos = documento.createElement("valor2");
                nodoProducto.appendChild(nodoDatos);
                texto = documento.createTextNode(((Componente) unProducto).getValor2());
                nodoDatos.appendChild(texto);
            } else if (unProducto instanceof Modulo) {
                nodoDatos = documento.createElement("aplicacion");
                nodoProducto.appendChild(nodoDatos);
                texto = documento.createTextNode(((Modulo) unProducto).getAplicacion());
                nodoDatos.appendChild(texto);
            } else if (unProducto instanceof OtroProducto) {
                nodoDatos = documento.createElement("notas");
                nodoProducto.appendChild(nodoDatos);
                texto = documento.createTextNode(((OtroProducto) unProducto).getNotas());
                nodoDatos.appendChild(texto);
            }
        }
        //guardo los datos en fichero
        Source source = new DOMSource(documento);
        Result result = new StreamResult(fichero);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);
    }


    //importarXML
    public void importarXML(File fichero) throws ParserConfigurationException, IOException, SAXException {
        listaProductos = new ArrayList<Producto>();
        Kit nuevoKit = null;
        ProductoQuimico nuevoProductoQuimico = null;
        Componente nuevoComponente = null;
        Modulo nuevoModulo = null;
        OtroProducto nuevoOtroProducto = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(fichero);

        NodeList listaElementos = documento.getElementsByTagName("*");
        for (int i = 0; i < listaElementos.getLength(); i++) {
            Node nodo = listaElementos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                switch (nodo.getNodeName()) {
                    case "Kit":
                        nuevoKit = new Kit();
                        listaProductos.add(nuevoKit);
                        break;
                    case "ProductoQuimico":
                        nuevoProductoQuimico = new ProductoQuimico();
                        listaProductos.add(nuevoProductoQuimico);
                        break;
                    case "Componente":
                        nuevoComponente = new Componente();
                        listaProductos.add(nuevoComponente);
                        break;
                    case "Modulo":
                        nuevoModulo = new Modulo();
                        listaProductos.add(nuevoModulo);
                        break;
                    case "OtroProducto":
                        nuevoOtroProducto = new OtroProducto();
                        listaProductos.add(nuevoOtroProducto);
                        break;
                    case "codigo-barras":
                        if (nuevoKit !=null) {
                            nuevoKit.setCodigoBarras(nodo.getTextContent());
                        } else if (nuevoProductoQuimico != null) {
                            nuevoProductoQuimico.setCodigoBarras(nodo.getTextContent());
                        } else if (nuevoComponente != null) {
                            nuevoComponente.setCodigoBarras(nodo.getTextContent());
                        } else if (nuevoModulo != null) {
                            nuevoModulo.setCodigoBarras(nodo.getTextContent());
                        } else if (nuevoOtroProducto != null) {
                            nuevoOtroProducto.setCodigoBarras(nodo.getTextContent());
                        }
                        break;
                    case "fabricante":
                        if (nuevoKit != null) {
                            nuevoKit.setFabricante(nodo.getTextContent());
                        } else if (nuevoProductoQuimico != null) {
                            nuevoProductoQuimico.setFabricante(nodo.getTextContent());
                        } else if (nuevoComponente != null) {
                            nuevoComponente.setFabricante(nodo.getTextContent());
                        } else if (nuevoModulo != null) {
                            nuevoModulo.setFabricante(nodo.getTextContent());
                        } else if (nuevoOtroProducto != null) {
                            nuevoOtroProducto.setFabricante(nodo.getTextContent());
                        }
                        break;
                    case "modelo":
                        if (nuevoKit != null) {
                            nuevoKit.setModelo(nodo.getTextContent());
                        } else if (nuevoProductoQuimico != null) {
                            nuevoProductoQuimico.setModelo(nodo.getTextContent());
                        } else if (nuevoComponente != null) {
                            nuevoComponente.setModelo(nodo.getTextContent());
                        } else if (nuevoModulo != null) {
                            nuevoModulo.setModelo(nodo.getTextContent());
                        } else if (nuevoOtroProducto != null) {
                            nuevoOtroProducto.setModelo(nodo.getTextContent());
                        }
                        break;

                    case "precio":
                        if (nuevoKit != null) {
                            nuevoKit.setPrecio(Double.parseDouble(nodo.getTextContent()));
                        } else if (nuevoProductoQuimico != null) {
                            nuevoProductoQuimico.setPrecio(Double.parseDouble(nodo.getTextContent()));
                        } else if (nuevoComponente != null) {
                            nuevoComponente.setPrecio(Double.parseDouble(nodo.getTextContent()));
                        } else if (nuevoModulo != null) {
                            nuevoModulo.setPrecio(Double.parseDouble(nodo.getTextContent()));
                        } else if (nuevoOtroProducto != null) {
                            nuevoOtroProducto.setPrecio(Double.parseDouble(nodo.getTextContent()));
                        }
                        break;

                    case "enStock":
                        if (nuevoKit != null) {
                            nuevoKit.setEnStock(Boolean.parseBoolean(nodo.getTextContent()));
                        } else if (nuevoProductoQuimico != null) {
                            nuevoProductoQuimico.setEnStock(Boolean.parseBoolean(nodo.getTextContent()));
                        } else if (nuevoComponente != null) {
                            nuevoComponente.setEnStock(Boolean.parseBoolean(nodo.getTextContent()));
                        } else if (nuevoModulo != null) {
                            nuevoModulo.setEnStock(Boolean.parseBoolean(nodo.getTextContent()));
                        } else if (nuevoOtroProducto != null) {
                            nuevoOtroProducto.setEnStock(Boolean.parseBoolean(nodo.getTextContent()));
                        }
                        break;

                    case "descatalogado":
                        if (nuevoKit != null) {
                            nuevoKit.setDescatalogado(Boolean.parseBoolean(nodo.getTextContent()));
                        } else if (nuevoProductoQuimico != null) {
                            nuevoProductoQuimico.setDescatalogado(Boolean.parseBoolean(nodo.getTextContent()));
                        } else if (nuevoComponente != null) {
                            nuevoComponente.setDescatalogado(Boolean.parseBoolean(nodo.getTextContent()));
                        } else if (nuevoModulo != null) {
                            nuevoModulo.setDescatalogado(Boolean.parseBoolean(nodo.getTextContent()));
                        } else if (nuevoOtroProducto != null) {
                            nuevoOtroProducto.setDescatalogado(Boolean.parseBoolean(nodo.getTextContent()));
                        }
                        break;

                    case "edad-minima":
                        nuevoKit.setEdadMinima(Integer.parseInt(nodo.getTextContent()));
                        break;
                    case "nivel":
                        nuevoKit.setNivel(nodo.getTextContent());
                        nuevoKit = null;
                        break;
                    case "precauciones":
                        nuevoProductoQuimico.setPrecauciones(nodo.getTextContent());
                        break;
                    case "fecha-caducidad":
                        nuevoProductoQuimico.setFechaCaducidad(LocalDate.parse(nodo.getTextContent()));
                        nuevoProductoQuimico = null;
                        break;
                    case "valor":
                        nuevoComponente.setValor(nodo.getTextContent());
                        break;
                    case "valor2":
                        nuevoComponente.setValor2(nodo.getTextContent());
                        nuevoComponente = null;
                        break;
                    case "aplicacion":
                        nuevoModulo.setAplicacion(nodo.getTextContent());
                        nuevoModulo = null;
                        break;
                    case "notas":
                        nuevoOtroProducto.setNotas(nodo.getTextContent());
                        nuevoOtroProducto = null;
                        break;
                }
            }
        }
    }
}

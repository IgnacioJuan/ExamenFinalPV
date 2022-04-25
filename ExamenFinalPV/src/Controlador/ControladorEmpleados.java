/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.BD.ConectionPg;
import Modelo.Empleado.Empleado;
import Modelo.Empleado.ModelEmpleado;
import Vista.VistaEmpleado;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Math.abs;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.ws.Holder;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Juan
 */
public class ControladorEmpleados {

    ModelEmpleado model;
    VistaEmpleado vista;

    public ControladorEmpleados(ModelEmpleado model, VistaEmpleado vista) {
        this.model = model;
        this.vista = vista;
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        CargarTabla();
    }

    public void iniciacontrol() {
        //Botones
        vista.getBtn_crear().addActionListener(l -> abrirDialogo(1));
        vista.getBtn_modificar().addActionListener(l -> abrirDialogo(2));
        vista.getBtn_Guardar().addActionListener(l -> CreatandEdit());
        vista.getBtn_eliminar().addActionListener(l -> delete());
        vista.getBtn_imprimir().addActionListener(l -> ImprimirEmpleados());
        //Evento de la busqueda
        vista.getTxt_busqueda().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                CargarTabla();
            }
        });
    }

    private void CargarTabla() {
        DefaultTableModel tblmodel;
        tblmodel = (DefaultTableModel) vista.getTbl_empleados().getModel();
        tblmodel.setNumRows(0);

        String valor = vista.getTxt_busqueda().getText();
        ArrayList<Empleado> list = model.listEmpleados(valor);
        Holder<Integer> i = new Holder<>(0);
        list.stream().forEach(emp -> {
            tblmodel.addRow(new Object[7]);
            vista.getTbl_empleados().setValueAt(emp.getCedula(), i.value, 0);
            vista.getTbl_empleados().setValueAt(emp.getNombres(), i.value, 1);
            vista.getTbl_empleados().setValueAt(emp.getApellidos(), i.value, 2);
            vista.getTbl_empleados().setValueAt(emp.getFecha_contrato(), i.value, 3);
            vista.getTbl_empleados().setValueAt(emp.getSalario(), i.value, 4);
            vista.getTbl_empleados().setValueAt(emp.getDiscapacidad(), i.value, 5);
            vista.getTbl_empleados().setValueAt(emp.getHorario(), i.value, 6);

            i.value++;

        });

    }

    private void abrirDialogo(int ce) {
        limpiar();
        String tittle = "";
        vista.getDlg_CrearEdit().setSize(550, 450);
        vista.getDlg_CrearEdit().setLocationRelativeTo(vista);
        if (ce == 1) {
            vista.getTxt_cedula().setEditable(true);
            tittle = "Crear nuevo Empleado";
            vista.getDlg_CrearEdit().setName("crear");
            vista.getDlg_CrearEdit().setVisible(true);

        } else {
            if (vista.getTbl_empleados().getSelectedRow() > -1) {
                vista.getTxt_cedula().setEditable(false);
                tittle = "Modificar Empleado";
                cargaMod();
                vista.getDlg_CrearEdit().setName("edit");
                vista.getDlg_CrearEdit().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(vista, "Seleccion una fila de la tabla");
            }
        }
        vista.getDlg_CrearEdit().setTitle(tittle);
    }

    public void cargaMod() {
        ArrayList<Empleado> list = model.listEmpleados(vista.getTbl_empleados().getValueAt(vista.getTbl_empleados().getSelectedRow(), 0).toString());
        list.stream().forEach(emp -> {
            vista.getTxt_apellidos().setText(emp.getApellidos());
            vista.getTxt_cedula().setText(emp.getCedula());
            vista.getTxt_discapacidad().setText(emp.getDiscapacidad());
            vista.getTxt_nombres().setText(emp.getNombres());
            vista.getCb_horario().setSelectedItem(emp.getHorario());
            vista.getSp_salario().setValue(emp.getSalario());
            //
            vista.getDc_FechaContrato().setDate(emp.getFecha_contrato());
        });
    }

    public void limpiar() {
        vista.getTxt_apellidos().setText("");
        vista.getTxt_cedula().setText("");
        vista.getTxt_discapacidad().setText("");
        vista.getTxt_nombres().setText("");
        vista.getCb_horario().setSelectedIndex(0);
        vista.getSp_salario().setValue(0);
        vista.getDc_FechaContrato().setDate(new java.util.Date(fechaActual()));
    }

    protected static String fechaActual() {
        String fechaact = null;
        try {
            Calendar fecha = new GregorianCalendar();
            //Obtenemos el valor del año, mes, día,
            //usando el método get y el parámetro correspondiente                                                     
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH);
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            fechaact = año + "/" + (mes + 1) + "/" + (dia);
        } catch (Exception e) {
            System.out.println(e);
        }
        return fechaact;
    }

    private void CreatandEdit() {
        if (vista.getDlg_CrearEdit().getName().equals("crear")) {
            Crear();
        } else {
            Modificar();
        }
    }

    public void Crear() {
        String Cedula = vista.getTxt_cedula().getText();
        String nombres = vista.getTxt_nombres().getText();
        String apellidos = vista.getTxt_apellidos().getText();
        int año = abs(1900 - vista.getDc_FechaContrato().getCalendar().get(Calendar.YEAR));
        int mes = vista.getDc_FechaContrato().getCalendar().get(Calendar.MONTH);
        int dia = vista.getDc_FechaContrato().getCalendar().get(Calendar.DAY_OF_MONTH);
        Date fecha_contrato = new Date(año, mes, dia);
        double salario = (double) vista.getSp_salario().getValue();
        String discapacidad = vista.getTxt_discapacidad().getText();
        String horario = vista.getCb_horario().getSelectedItem().toString();

        ModelEmpleado empleado = new ModelEmpleado();
        empleado.setApellidos(apellidos);
        empleado.setCedula(Cedula);
        empleado.setDiscapacidad(discapacidad);
        empleado.setFecha_contrato(fecha_contrato);
        empleado.setHorario(horario);
        empleado.setNombres(nombres);
        empleado.setSalario(salario);

        if (empleado.CrearEmpleado()) {
            vista.getDlg_CrearEdit().setVisible(false);
            JOptionPane.showMessageDialog(vista, "Exito en la operacion");
            limpiar();
            CargarTabla();
        } else {
            JOptionPane.showMessageDialog(vista, "Error en la operacion");
        }
    }

    public void Modificar() {
        String Cedula = vista.getTxt_cedula().getText();
        String nombres = vista.getTxt_nombres().getText();
        String apellidos = vista.getTxt_apellidos().getText();
        int año = abs(1900 - vista.getDc_FechaContrato().getCalendar().get(Calendar.YEAR));
        int mes = vista.getDc_FechaContrato().getCalendar().get(Calendar.MONTH);
        int dia = vista.getDc_FechaContrato().getCalendar().get(Calendar.DAY_OF_MONTH);
        Date fecha_contrato = new Date(año, mes, dia);
        double salario = (double) vista.getSp_salario().getValue();
        String discapacidad = vista.getTxt_discapacidad().getText();
        String horario = vista.getCb_horario().getSelectedItem().toString();

        ModelEmpleado empleado = new ModelEmpleado();
        empleado.setApellidos(apellidos);
        empleado.setCedula(Cedula);
        empleado.setDiscapacidad(discapacidad);
        empleado.setFecha_contrato(fecha_contrato);
        empleado.setHorario(horario);
        empleado.setNombres(nombres);
        empleado.setSalario(salario);

        if (empleado.ActualizarEmpleado()) {
            vista.getDlg_CrearEdit().setVisible(false);
            JOptionPane.showMessageDialog(vista, "Exito en la operacion");
            limpiar();
            CargarTabla();
        } else {
            JOptionPane.showMessageDialog(vista, "Error en la operacion");
        }
    }

    private void delete() {
        if (vista.getTbl_empleados().getSelectedRow() > -1) {
            ModelEmpleado empleado = new ModelEmpleado();
            String cedula = vista.getTbl_empleados().getValueAt(vista.getTbl_empleados().getSelectedRow(), 0).toString();
            empleado.setCedula(cedula);
            if (empleado.EliminaEmpleado()) {
                JOptionPane.showMessageDialog(vista, "Exito en la operacion");
                CargarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error en la operacion");
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccion una fila de la tabla");
        }
    }

    private void ImprimirEmpleados() {

        ConectionPg connection = new ConectionPg();

        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/Vista/Reporte/Empleados.jasper"));

            JasperPrint jp = JasperFillManager.fillReport(jr, null, connection.getCon());
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(ControladorEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

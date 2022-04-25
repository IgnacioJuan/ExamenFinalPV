/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Empleado.ModelEmpleado;
import Vista.VistaEmpleado;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.Math.abs;
import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author Juan
 */
public class ValidacionesEmpleados extends ControladorEmpleados{
    
    public ValidacionesEmpleados(ModelEmpleado model, VistaEmpleado vista) {
        super(model, vista);
    }

    @Override
    public void iniciacontrol() {
        super.iniciacontrol(); //To change body of generated methods, choose Tools | Templates.
        
        vista.getTxt_cedula().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                Validar();
            }
        });
        vista.getTxt_nombres().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                Validar();
            }
        });
        vista.getTxt_apellidos().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                Validar();
            }
        });
        vista.getCb_horario().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Validar();
            }
        });
        vista.getDlg_CrearEdit().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Validar(); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
    }
    
    public void Validar(){
        String Cedula = vista.getTxt_cedula().getText();
        String nombres = vista.getTxt_nombres().getText();
        String apellidos = vista.getTxt_apellidos().getText();
        int horario = vista.getCb_horario().getSelectedIndex();
        
        boolean cedulab=false;
        boolean nombresb=false;
        boolean apellidosb=false;
        boolean horariob=false;
        
        if(Cedula.isEmpty()){
            vista.getVal_cedula().setText("*Campo requerido");
            cedulab=false;
        }else if(Cedula.length()!=10){
            vista.getVal_cedula().setText("*Debe contener 10 caracteres");
            cedulab=false;
        }else if(!Cedula.matches("\\d*")){
            vista.getVal_cedula().setText("*Solo se permiten numeros");
        }else{
            vista.getVal_cedula().setText("");
            cedulab=true;
        }
         if(nombres.isEmpty()){
            vista.getVal_nombres().setText("*Campo requerido");
            nombresb=false;
        }else{
            vista.getVal_nombres().setText("");
            nombresb=true;
        }
          if(apellidos.isEmpty()){
            vista.getVal_apellido().setText("*Campo requerido");
            apellidosb=false;
        }else{
            vista.getVal_apellido().setText("");
            apellidosb=true;
        }
           if(horario==0){
            vista.getVal_horario().setText("Debe seleccionar una opcion");
            horariob=false;
        }else{
            vista.getVal_horario().setText("");
            horariob=true;
        }
           if(!cedulab || !nombresb || !apellidosb || !horariob){
            vista.getBtn_Guardar().setEnabled(false);
        }else{
            vista.getBtn_Guardar().setEnabled(true);
        }
    }
    
}

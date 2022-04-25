
package examenfinalpv;

import Controlador.ControladorEmpleados;
import Modelo.Empleado.ModelEmpleado;
import Vista.VistaEmpleado;

public class ExamenFinalPV {

    public static void main(String[] args) {
         VistaEmpleado vista=new VistaEmpleado();
         ModelEmpleado model=new ModelEmpleado();
         ControladorEmpleados controlador=new ControladorEmpleados(model, vista);
         controlador.iniciacontrol();
    }
    
}

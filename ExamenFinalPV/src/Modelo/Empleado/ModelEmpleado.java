/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Empleado;

import Modelo.BD.ConectionPg;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan
 */
public class ModelEmpleado extends Empleado{
    ConectionPg cpg= new ConectionPg();

    public ModelEmpleado() {
    }
    
    
    public ModelEmpleado(String cedula, String nombres, String apellidos, Date fecha_contrato, double salario, String discapacidad, String horario) {
        super(cedula, nombres, apellidos, fecha_contrato, salario, discapacidad, horario);
    }
    
     public ArrayList<Empleado> listEmpleados(String busqueda) {
        ArrayList<Empleado> lista = new ArrayList<>();

        try {
            //Sentencia
            String sql = "Select * from \"Empleados\" where cedula ilike '%"+busqueda+"%' and habilitado=true or nombres ilike '%"+busqueda+"%' and habilitado=true";
            ResultSet rs = cpg.consulta(sql);
            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setApellidos(rs.getString("apellidos"));
                emp.setCedula(rs.getString("cedula"));
                emp.setDiscapacidad(rs.getString("discapacidad"));
                emp.setFecha_contrato(rs.getDate("fecha_contrato"));
                emp.setHorario(rs.getString("horario"));
                emp.setNombres(rs.getString("nombres"));
                emp.setSalario(rs.getDouble("salario"));
                
                lista.add(emp);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public boolean CrearEmpleado() {
        String sql;
        sql = "Insert into \"Empleados\"(cedula, nombres, apellidos, fecha_contrato, salario, discapacidad, horario, habilitado)";
        sql += "values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cpg.getCon().prepareStatement(sql);
            ps.setString(1, getCedula());
            ps.setString(2, getNombres());
            ps.setString(3, getApellidos());
            ps.setDate(4, getFecha_contrato());
            ps.setDouble(5, getSalario());
            ps.setString(6, getDiscapacidad());
            ps.setString(7, getHorario());
            ps.setBoolean(8, true);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean ActualizarEmpleado() {
        String sql;
        sql = "update \"Empleados\" set nombres=?, apellidos=?, fecha_contrato=?, salario=?, discapacidad=?, horario=?"
                + "where cedula='" + getCedula()+ "'";
        try {
            PreparedStatement ps = cpg.getCon().prepareStatement(sql);
            ps.setString(1, getNombres());
            ps.setString(2, getApellidos());
            ps.setDate(3, getFecha_contrato());
            ps.setDouble(4, getSalario());
            ps.setString(5, getDiscapacidad());
            ps.setString(6, getHorario());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean EliminaEmpleado() {
        String sql;
        sql = "update \"Empleados\" set habilitado=?"
                +  "where cedula='" + getCedula() + "'";
        try {
            PreparedStatement ps = cpg.getCon().prepareStatement(sql);
            ps.setBoolean(1, false);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

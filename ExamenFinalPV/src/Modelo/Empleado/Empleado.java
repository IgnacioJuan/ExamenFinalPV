
package Modelo.Empleado;

import java.sql.Date;

public class Empleado {
    private String cedula;
    private String nombres;
    private String apellidos;
    private Date fecha_contrato;
    private double salario;
    private String discapacidad;
    private String horario;

    public Empleado() {
    }

    
    public Empleado(String cedula, String nombres, String apellidos, Date fecha_contrato, double salario, String discapacidad, String horario) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_contrato = fecha_contrato;
        this.salario = salario;
        this.discapacidad = discapacidad;
        this.horario = horario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFecha_contrato() {
        return fecha_contrato;
    }

    public void setFecha_contrato(Date fecha_contrato) {
        this.fecha_contrato = fecha_contrato;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
    
    
    
}

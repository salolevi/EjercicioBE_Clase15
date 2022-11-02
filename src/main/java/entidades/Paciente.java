package entidades;

import java.time.LocalDate;

public record Paciente(int id, String nombre, String apellido, String dni, LocalDate fechaIngreso, Domicilio domicilio) {
}
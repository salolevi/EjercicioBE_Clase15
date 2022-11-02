package servicios;

import daos.DomicilioDAOH2;
import daos.PacienteDAOH2;
import entidades.Paciente;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PacienteService {
    private final PacienteDAOH2 pacienteDAO;
    private final DomicilioDAOH2 domicilioDAO;

    public void darDeAlta(Paciente paciente){
        domicilioDAO.darDeAlta(paciente.domicilio());
        pacienteDAO.darDeAlta(paciente);
    }

    public Paciente buscar(int id){
        var paciente = pacienteDAO.buscar(id);
        var domicilio = domicilioDAO.buscar(paciente.domicilio().id());


        return new Paciente(paciente.id(), paciente.nombre(), paciente.apellido(), paciente.dni(), paciente.fechaIngreso(), domicilio);
    }


    public void eliminar(int id){
        pacienteDAO.eliminar(id);
    }


    public void modificar(Paciente paciente){
        pacienteDAO.modificar(paciente);
        domicilioDAO.modificar(paciente.domicilio());
    }


}
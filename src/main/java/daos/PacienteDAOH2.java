package daos;

import com.sun.source.tree.TryTree;
import entidades.Domicilio;
import entidades.Paciente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.time.ZoneId;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class PacienteDAOH2 implements IDao<Paciente> {

    private static final Logger logger = LogManager.getLogger(PacienteDAOH2.class);
    private final static String DB_URL = "jdbc:h2:~/db_clinica;INIT=RUNSCRIPT FROM 'create2.sql'";
    private final static String SELECT_ID = "SELECT * FROM pacientes WHERE id = ?";

    private final static String INSERT = "INSERT INTO PACIENTES VALUES(?, ?, ?, ?);";

    private final static String DELETE = "DELTE FROM Pacientes WHERE ID = ?;";

    private final static String UPDATE = "UPDATE Pacientes SET nombre = ?, SET dni = ?, set ingreso = ?, set domicilio_id = ?;";

    @Override
    public Paciente darDeAlta(Paciente paciente) {
        try {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Connection c = getConnection();
            var st = c.prepareStatement(INSERT);
            st.setString(1, paciente.nombre());
            st.setString(2, paciente.dni());
            var fechaIngreso = Date.from(paciente.fechaIngreso().atStartOfDay(defaultZoneId).toInstant());
            st.setDate(3, (java.sql.Date) fechaIngreso);
            st.setInt(4, paciente.domicilio().id());

            st.execute();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }finally {

        }
        logger.info("Se agrego el paciente con id " + paciente.id());
        return buscar(paciente.id());
    }

    @Override
    public Paciente buscar(int id) {
        try {
            var connection = getConnection();
            var buscar = connection.prepareStatement(SELECT_ID);
            buscar.setInt(1, id);
            var result = buscar.executeQuery();

            if (result.next()) {
                connection.close();
                var nombre = result.getString(2);
                var apellido = result.getString(3);
                var dni = result.getString(4);
                var fechaIngreso = result.getDate(5);
                var domicilioId = result.getInt(6);

                return new Paciente(id, nombre, apellido, dni, fechaIngreso.toLocalDate(), new Domicilio(domicilioId, null, 0, null, null));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void eliminar(int id) {
        try {
            Connection c = getConnection();
            var st = c.prepareStatement(INSERT);
            st.setInt(1, id);
            st.execute();
        } catch (SQLException e) {
            logger.error("Hubo un error al borrar el paciente " + id);
        } finally {

        }
        logger.info("Se elimino al paciente " + id);

    }

    @Override
    public void modificar(Paciente paciente) {
        try {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            var fechaIngreso = Date.from(paciente.fechaIngreso().atStartOfDay(defaultZoneId).toInstant());
            Connection c = getConnection();
            var st = c.prepareStatement(UPDATE);
            st.setString(1,  paciente.nombre());
            st.setString(2, paciente.dni());
            st.setDate(3, (java.sql.Date) fechaIngreso);
            st.setInt(4, paciente.id());
            st.execute();

        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, "", "");
    }
}
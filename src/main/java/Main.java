import daos.DomicilioDAOH2;
import daos.PacienteDAOH2;
import entidades.Domicilio;
import entidades.Paciente;
import lombok.extern.java.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import servicios.PacienteService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

public class Main {
    private final static String log4jConfigFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "log4j2.xml";

    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws IOException {
        startLogger();

        var service = new PacienteService(new PacienteDAOH2(), new DomicilioDAOH2());
        var paciente = new Paciente(1, "nombre", "appellido", "123123", LocalDate.now(),
                new Domicilio(1, "calle", 1, "localida", "proviuncia"));
        service.buscar(paciente.id());



    }


    private static void startLogger() throws IOException {
        var source = new ConfigurationSource(new FileInputStream(log4jConfigFile));
        Configurator.initialize(null, source);
    }
}
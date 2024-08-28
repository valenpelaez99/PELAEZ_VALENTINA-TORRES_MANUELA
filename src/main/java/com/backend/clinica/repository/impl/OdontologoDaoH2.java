package com.backend.clinica.repository.impl;

import com.backend.clinica.dbconnection.H2Connection;
import com.backend.clinica.entity.Domicilio;
import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.entity.Paciente;
import com.backend.clinica.repository.IDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class OdontologoDaoH2 implements IDao<Odontologo> {

    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoDaoH2.class);

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        LOGGER.info("Odontólogo a registrar: " + odontologo);
        Odontologo odontologoRegistrado = null;
        Connection connection=null;

        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            String insert = "INSERT INTO ODONTOLOGOS(MATRICULA, NOMBRE, APELLIDO) VALUES(?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());

            preparedStatement.execute();



            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                odontologoRegistrado = new Odontologo(resultSet.getLong("id"),
                        odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());
            }

            LOGGER.info("Odontólogo guardado: " + odontologoRegistrado);
            connection.commit();

        } catch (Exception exception){
            LOGGER.error(exception.getMessage());
            exception.printStackTrace();
            if(connection != null){
                try{
                    connection.rollback();
                    LOGGER.error("Tuvimos un problema. " + exception.getMessage());
                    exception.printStackTrace();
                } catch (SQLException sqlException){
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        }

        finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("No se pudo cerrar la conexion: " + ex.getMessage());
            }
        }



        return odontologoRegistrado;
    }

    @Override
    public Odontologo buscarPorId(Long id) {
        Odontologo odontologoBuscado = null;
        Connection connection = null;
        try {
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ODONTOLOGOS WHERE ID = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
              odontologoBuscado = crearObjetoOdontologo(resultSet);
            }


        }  catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }


        if(odontologoBuscado == null) LOGGER.error("No se ha encontrado el odontologo con id: " + id);
        else LOGGER.info("Se ha encontrado el odontologo: " + odontologoBuscado);

        return odontologoBuscado;
    }

    @Override
    public List<Odontologo> listarTodos() {

        return new ArrayList<>();
    }

    private Odontologo crearObjetoOdontologo(ResultSet resultSet) throws SQLException {

        return new Odontologo(resultSet.getLong(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));

    }
}

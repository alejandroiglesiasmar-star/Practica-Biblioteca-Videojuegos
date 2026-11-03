package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.SocioDTO;
import java.util.List;

public interface SocioDAO {
    List<SocioDTO> buscarTodos();
    List<SocioDTO> buscarPorNombreODni(String textoBusqueda);
    SocioDTO buscarPorId(Integer idSocio);
    int insertar(SocioDTO socio);
    boolean existeDni(String dni);
}
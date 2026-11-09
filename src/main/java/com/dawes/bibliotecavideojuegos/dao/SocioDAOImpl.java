package com.dawes.bibliotecavideojuegos.dao;

import com.dawes.bibliotecavideojuegos.dto.SocioDTO;
import com.dawes.bibliotecavideojuegos.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SocioDAOImpl implements SocioDAO {

    @Autowired
    private SocioRepository socioRepository;

    @Override
    public List<SocioDTO> buscarConFiltros(String nombreApellidos, String email, Integer activo) {
        return socioRepository.buscarConFiltros(nombreApellidos, email, activo);
    }

    @Override
    public SocioDTO obtenerPorId(Integer idSocio) {
        return socioRepository.obtenerPorId(idSocio);
    }

    @Override
    public List<SocioDTO> obtenerActivos() {
        return socioRepository.obtenerActivos();
    }

    @Override
    public boolean existeEmail(String email) {
        return socioRepository.existeEmail(email);
    }

    @Override
    public void insertar(SocioDTO socio) {
        socioRepository.insertar(socio);
    }

    @Override
    public int contarActivos() {
        return socioRepository.contarActivos();
    }
}

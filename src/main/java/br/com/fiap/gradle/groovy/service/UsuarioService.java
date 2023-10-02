package br.com.fiap.gradle.groovy.service;

import br.com.fiap.gradle.groovy.controller.exception.ControllerNotFoundException;
import br.com.fiap.gradle.groovy.dto.UsuarioDTO;
import br.com.fiap.gradle.groovy.entities.Usuario;
import br.com.fiap.gradle.groovy.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Page<UsuarioDTO> findAll(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(this::toDTO);
    }

    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Usuário não encontrado com o ID: " + id));
        return toDTO(usuario);
    }

    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        Usuario usuario = toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioRepository.getReferenceById(id);

            usuario.setNome(usuarioDTO.nome());
            usuario.setEmail(usuarioDTO.email());
            usuario.setCpf(usuarioDTO.cpf());
            usuario.setDataNascimento(usuarioDTO.dataNascimento());

            usuario = usuarioRepository.save(usuario);
            return toDTO(usuario);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Usuário não encontrado com o ID: " + id);
        }

    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    private Usuario toEntity(UsuarioDTO usuarioDTO) {
        return new Usuario(usuarioDTO.nome(), usuarioDTO.email(), usuarioDTO.cpf(), usuarioDTO.dataNascimento());
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getNome(), usuario.getEmail(), usuario.getCpf(), usuario.getDataNascimento());
    }
}
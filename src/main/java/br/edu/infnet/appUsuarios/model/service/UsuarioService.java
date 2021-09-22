package br.edu.infnet.appUsuarios.model.service;

import br.edu.infnet.appUsuarios.model.domain.Usuario;
import br.edu.infnet.appUsuarios.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AmazonClient amazonClient;

    public void salvar(Usuario usuario, MultipartFile file) {
        String base64 = amazonClient.uploadFile(file);
        if (base64 != null) {
            usuario.setArquivoUrl(base64);
        }
        usuarioRepository.save(usuario);
    }


    public void incluir(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void alterar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public boolean verficaUsuarioExistente(Usuario usuario) {
        return usuarioRepository.existsByEmail(usuario.getEmail());
    }

    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }
}

package br.edu.infnet.appUsuarios.model.controller;

import br.edu.infnet.appUsuarios.model.domain.Usuario;
import br.edu.infnet.appUsuarios.model.domain.Endereco;
import br.edu.infnet.appUsuarios.model.service.AmazonClient;
import br.edu.infnet.appUsuarios.model.service.UsuarioService;
import br.edu.infnet.appUsuarios.model.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SessionAttributes({"oper", "usuario", "endereco"})
@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private AmazonClient amazonClient;

    @GetMapping("/usuario/lista")
    public String telaLista(Model model) {
        model.addAttribute("lista", usuarioService.findAll());
        return "/usuario/lista";
    }

    @GetMapping(value = "/usuario")
    public String telaCadastro(Model model) {
        Usuario usuario = new Usuario();
        Endereco endereco = new Endereco();
        model.addAttribute("usuario", usuario);
        model.addAttribute("endereco", endereco);
        model.addAttribute("oper", "incluir");
        return "/usuario/cadastro";
    }

    @PostMapping(value = "/usuario/cep")
    public String obterCep(Model model,
                           @SessionAttribute("usuario") Usuario usuario,
                           @SessionAttribute("endereco") Endereco endereco,
                           @SessionAttribute("oper") String oper,
                           @RequestParam String cep) {
        usuario.setEndereco(enderecoService.obterCep(cep));
        model.addAttribute("usuario", usuario);
        model.addAttribute("endereco", endereco);
        model.addAttribute("oper", oper);
        return "/usuario/cadastro";
    }

    @GetMapping("/usuario/{id}/montaAlteracao")
    public String montaAlteracao(Model model, @PathVariable Long id) throws IOException {
        Usuario usuario = usuarioService.findById(id);
        Endereco endereco = usuario.getEndereco();
        model.addAttribute("arquivo", this.amazonClient.getFile(usuario.getArquivoUrl()));
        model.addAttribute("usuario", usuario);
        model.addAttribute("endereco", endereco);
        model.addAttribute("oper", "alterar");
        return "/usuario/cadastro";
    }

    @PostMapping("/usuario/salvar")
    public String salvar(Model model,
                         Usuario usuario,
                         Endereco endereco,
                         @SessionAttribute("oper") String oper,
                         @RequestPart(value = "arquivo") MultipartFile arquivo) {
        model.addAttribute("oper", oper);
        if (oper == "incluir") {
            if (!usuarioService.verficaUsuarioExistente(usuario)) {
                new Usuario(usuario.getNome(), usuario.getEmail(), usuario.getTelefone(), usuario.getEndereco());
                usuario.setEndereco(endereco);
                usuarioService.salvar(usuario, arquivo);
                String mensagem = "O usuario " + usuario.getNome() + " foi cadastrado com sucesso!!!";
                model.addAttribute("msg", mensagem);
                return telaLista(model);
            } else {
                String mensagem = "Já existe o e-mail " + " registrado para outro usuário.";
                model.addAttribute("msg", mensagem);
                String idMsg = "erro";
                model.addAttribute("idMsg", idMsg);
            }
        } else {
            usuario.setEndereco(endereco);
            usuarioService.salvar(usuario, arquivo);
            String mensagem = "O usuario " + usuario.getNome() + " foi alterado com sucesso!!!";
            model.addAttribute("msg", mensagem);
            return telaLista(model);
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("endereco", endereco);
        model.addAttribute("oper", "alterar");
        return "/usuario/cadastro";
    }

    @GetMapping("/usuario/{id}/excluir")
    public String excluir(Model model, @PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        String mensagem = null;
        String idMsg = null;
        try {
            usuarioService.excluir(id);
            mensagem = "O usuario " + usuario.getNome() + " foi excluído com sucesso!!!";
            idMsg = "sucesso";
        } catch (Exception e) {
            mensagem = "Não foi possível realizar a exclusão do usuario " + usuario.getNome() + ". Erro retornado: " + e.getMessage();
            idMsg = "erro";
        }
        model.addAttribute("msg", mensagem);
        model.addAttribute("idMsg", idMsg);
        return telaLista(model);
    }
}

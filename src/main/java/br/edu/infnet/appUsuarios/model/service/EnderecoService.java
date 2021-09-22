package br.edu.infnet.appUsuarios.model.service;

import br.edu.infnet.appUsuarios.model.clients.IEnderecoClient;
import br.edu.infnet.appUsuarios.model.domain.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private IEnderecoClient enderecoClient;

    public Endereco obterCep(String cep){
        return enderecoClient.obterCep(cep);
    }
}

package br.edu.infnet.appUsuarios.model.exceptions;

public class UsuarioJaExisteException extends Exception{
    public UsuarioJaExisteException(String mensagem) {
        super(mensagem);
    }

}

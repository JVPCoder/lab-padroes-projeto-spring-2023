package com.digitalinnovationone.gof.service;

import com.digitalinnovationone.gof.model.Cliente;

/**
 *
 * Interface que define o padrão Strategy no domínio de cliente.
 * Com isso, se necessário, podemos ter múltiplas implementações desta interface
 *
 * @author JVPCoder
 */


public interface ClienteService {

    Iterable<Cliente> buscarTodos();

    Cliente buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);

}

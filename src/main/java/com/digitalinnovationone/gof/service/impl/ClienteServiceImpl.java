package com.digitalinnovationone.gof.service.impl;

import java.util.Optional;

import com.digitalinnovationone.gof.model.Cliente;
import com.digitalinnovationone.gof.model.ClienteRepository;
import com.digitalinnovationone.gof.model.Endereco;
import com.digitalinnovationone.gof.model.EnderecoRepository;
import com.digitalinnovationone.gof.service.ClienteService;
import com.digitalinnovationone.gof.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Implementação da Strategy, a qual pode ser injetada pelo Spring via AutoWired
 *
 * Com isso essa classe é um Service, será tratada no final como Singleton.
 *
 * @author JVPCoder
 */

@Service
public class ClienteServiceImpl implements ClienteService {

    // Singleton: Injetar os componentes com autowired.
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;


    // Strategy: Implementar os métodos definidos na interface.
    // Facade: Abstrair integrações com subsistemas.

    @Override
    public Iterable<Cliente> buscarTodos() {
        //buscar todos os Clientes.
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        //buscar por id.
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        //buscar cliente por id.
        Optional<Cliente> clienteBD = clienteRepository.findById(id);
        if(clienteBD.isPresent()){
            //Consultar se o endereço existe.
            //Caso endereço não exista consumir ViaCep e retornar
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        //deletar cliente por id.
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        //Consultar se o endereço existe.
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            //Caso endereço não exista consumir ViaCep e retornar
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });

        cliente.setEndereco(endereco);
        //inserir cliente.
        clienteRepository.save(cliente);
    }

}

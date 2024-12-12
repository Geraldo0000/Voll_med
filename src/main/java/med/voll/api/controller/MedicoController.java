package med.voll.api.controller;

import jakarta.validation.Valid;

import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
public class MedicoController {// preciso da interface repository, vou declarar como um atributo da classe controller

    @Autowired // ele que vai instanciar e passar essa atributo repository dentro da classe controller
    private MedicoRepository repository; // quem vai instaciar vai ser o Spring (injeção de dependencias)


    //metodo para cadastrar(inclusão)
    @PostMapping
    @Transactional//para ter uma transação ativa com o banco de dados
    public ResponseEntity  cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){//puxando o json do corpo da requisição com um DTO

        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }
    //metodo para consultar cadastro
    // Fazendo uma paginação  Page / Pageable / findAll(paginação) sem toList
    @GetMapping  //DTO                      //tipo -- parametro
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size=10, sort= {"nome"})Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    //public Page<DadosListagemMedico> listar(@PageableDefault(size=10, sort= {"nome"})Pageable paginacao)
    }

    // Metodo para atualizar cadastro
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }

    // metodo para excluir
    @DeleteMapping("/{id}") //-- /{id} é um parametro dinânmico da anotação
    @Transactional // excluindo o objeto do BD
    public ResponseEntity excluir(@PathVariable Long id){ //PathVariable é equivalente ao /{id}
        var medico = repository.getReferenceById(id); // atualizando pelo ID
        medico.excluir();

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }

}

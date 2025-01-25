package forohub.forohub.controller;

import forohub.forohub.domain.topico.DtoActualizarTopico;
import forohub.forohub.domain.topico.DtoListarTopicos;
import forohub.forohub.domain.topico.DtoRegistrarNuevoTopico;
import forohub.forohub.domain.topico.Topico;
import forohub.forohub.domain.topico.TopicoRepository;
import forohub.forohub.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

//Creando un nuevo topico
    @PostMapping
    @Transactional
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid DtoRegistrarNuevoTopico dtoRegistrarNuevoTopico){
        try {
            if (topicoRepository.existsByTituloAndMensaje(dtoRegistrarNuevoTopico.titulo(), dtoRegistrarNuevoTopico.mensaje())) {
                return ResponseEntity.status(400).body("El tópico ya existe.");
            }
            Topico nuevoTopico = new Topico(dtoRegistrarNuevoTopico);
            topicoRepository.save(nuevoTopico);
            return ResponseEntity.ok(new DtoListarTopicos(nuevoTopico));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al registrar el tópico: " + e.getMessage());
        }
    }

//Listado de topicos sin paginado
//    @GetMapping
//    public ResponseEntity<List<DtoListarTopicos>> listarTopicos() {
//        List<Topico> topicos = topicoRepository.findAll();
//        List<DtoListarTopicos> dtoListarTopicos = topicos.stream()
//                .map(DtoListarTopicos::new)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(dtoListarTopicos);
//    }

    //Opcional: listado de topicos con paginado
    @GetMapping
    public ResponseEntity<Page<DtoListarTopicos>> listarTopicos(@PageableDefault(size = 3) Pageable pageable) {
        Page<Topico> topicos = topicoRepository.findAll(pageable);
        Page<DtoListarTopicos> dtoListarTopicos = topicos.map(DtoListarTopicos::new);
        return ResponseEntity.ok(dtoListarTopicos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTopicoPorId(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            DtoListarTopicos dto = new DtoListarTopicos(topico);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(404).body("Tópico no encontrado");
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody DtoActualizarTopico dtoActualizarTopico) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            topico.actualizarTopicos(dtoActualizarTopico);
            return ResponseEntity.ok(new DtoListarTopicos(topico));
        } else {
            return ResponseEntity.status(404).body("Tópico no encontrado");
        }
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok("Tópico eliminado exitosamente");
        } else {
            return ResponseEntity.status(404).body("Tópico no encontrado");
        }
    }
}





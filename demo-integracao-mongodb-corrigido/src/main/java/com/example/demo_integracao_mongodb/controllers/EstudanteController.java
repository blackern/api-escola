package com.example.demo_integracao_mongodb.controllers;

import com.example.demo_integracao_mongodb.models.Estudante;
import com.example.demo_integracao_mongodb.repositories.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/estudantes")
public class EstudanteController {

    @Autowired
    private EstudanteRepository estudanteRepository;

    @PostMapping
    public void gravar(@RequestBody Estudante estudante) {
        this.estudanteRepository.save(estudante);
    }

    @GetMapping
    public List<Estudante> buscarTodos() {
        return this.estudanteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudante> buscarPorId(@PathVariable String id) {
        Optional<Estudante> estudante = this.estudanteRepository.findById(id);
        return estudante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudante> atualizar(@PathVariable String id, @RequestBody Estudante novosDados) {
        if (!this.estudanteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        novosDados.setId(id);
        Estudante estudanteAtualizado = this.estudanteRepository.save(novosDados);
        return ResponseEntity.ok(estudanteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        if (!this.estudanteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        this.estudanteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
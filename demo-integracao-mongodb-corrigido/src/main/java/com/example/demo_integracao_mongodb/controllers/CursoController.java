package com.example.demo_integracao_mongodb.controllers;

import com.example.demo_integracao_mongodb.models.Curso;
import com.example.demo_integracao_mongodb.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public void gravar(@RequestBody Curso curso) {
        this.cursoRepository.save(curso);
    }

    @GetMapping
    public List<Curso> buscarTodos() {
        return this.cursoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable String id) {
        Optional<Curso> curso = this.cursoRepository.findById(id);
        return curso.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizar(@PathVariable String id, @RequestBody Curso novosDados) {
        if (!this.cursoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        novosDados.setId(id);
        Curso cursoAtualizado = this.cursoRepository.save(novosDados);
        return ResponseEntity.ok(cursoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        if (!this.cursoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        this.cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
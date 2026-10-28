package com.example.demo_integracao_mongodb.controllers;

import com.example.demo_integracao_mongodb.models.Professor;
import com.example.demo_integracao_mongodb.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @PostMapping
    public void gravar(@RequestBody Professor professor) {
        this.professorRepository.save(professor);
    }

    @GetMapping
    public List<Professor> buscarTodos() {
        return this.professorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(@PathVariable String id) {
        Optional<Professor> professor = this.professorRepository.findById(id);
        return professor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizar(@PathVariable String id, @RequestBody Professor novosDados) {
        if (!this.professorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        novosDados.setId(id);
        Professor professorAtualizado = this.professorRepository.save(novosDados);
        return ResponseEntity.ok(professorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        if (!this.professorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        this.professorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
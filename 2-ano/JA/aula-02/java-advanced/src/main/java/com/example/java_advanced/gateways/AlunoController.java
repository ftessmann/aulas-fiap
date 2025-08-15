package com.example.java_advanced.gateways;

import com.example.java_advanced.gateways.dtos.AlunoPostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fiap/aluno")
public class AlunoController {


    @GetMapping("/{id}")
    public String getAluno(@PathVariable String id) {
        return "Aluno".concat(id);
    }

    @GetMapping()
    public ResponseEntity<?> getAlunos(
        @RequestParam(name = "sala") String classRoom, // substitui query param sala por classRoom
        @RequestParam(required = false, name = "nomeBuscado") String searchName // substitui o query param nomeBuscado por searchName
        ) {
        ArrayList<String> alunos = new ArrayList<>();

        if (classRoom.equals("2tdspr")) {
            alunos.add("Aluno 1");
        }

        if (alunos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(alunos);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createAluno(@RequestBody AlunoPostRequest aluno) {
        return "Aluno cadastrado com sucesso: ".concat(aluno.getNomeCompleto());
    }
}

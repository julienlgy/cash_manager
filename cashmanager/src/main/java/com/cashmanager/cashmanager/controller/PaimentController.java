package com.cashmanager.cashmanager.controller;

import com.cashmanager.cashmanager.exception.ResourceNotFoundException;
import com.cashmanager.cashmanager.model.Paiment;
import com.cashmanager.cashmanager.repository.PaimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PaimentController {
    @Autowired
    PaimentRepository paimentRepository;

    @GetMapping("/paiments")
    public List<Paiment> getAllPaiments(){
        return paimentRepository.findAll();
    }

    @PostMapping("/paiments")
    public Paiment createPaiment(@Valid @RequestBody Paiment paiment){
        return paimentRepository.save(paiment);
    }

    @GetMapping("/paiments/{id]")
    public Paiment updatePaiment(@PathVariable(value="id") Long paimentId){
        return paimentRepository.findById(paimentId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiment", "id", paimentId));
    }

    @PutMapping("/paiments/{id]")
    public Paiment updatePaiment(@PathVariable(value="id") Long paimentId, @Valid @RequestBody Paiment paimentDetails){
        Paiment paiment = paimentRepository.findById(paimentId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiment","Id", paimentId));

        paiment.setType(paimentDetails.getType());
        paiment.setTotal(paimentDetails.getTotal());

        Paiment updatedPaiment = paimentRepository.save(paiment);

        return updatedPaiment;

    }

    @DeleteMapping("/paiments/{id}")
    public ResponseEntity<?> deletePaiment(@PathVariable(value = "id") Long paimentId) {
        Paiment paiment = paimentRepository.findById(paimentId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", paimentId));

        paimentRepository.delete(paiment);

        return ResponseEntity.ok().build();
    }
}

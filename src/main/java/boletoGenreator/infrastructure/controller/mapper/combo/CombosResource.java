package boletoGenreator.infrastructure.controller.mapper.combo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.combos.ComboStateDTO;

import org.springframework.web.bind.annotation.GetMapping;

@RestController 
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping(CombosEndpoints.COMBO)
public interface CombosResource {

    @GetMapping(CombosEndpoints.STATES)
    public CompletableFuture<List<ComboStateDTO>> getStates();
}

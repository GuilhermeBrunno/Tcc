package com.bat.uberlandia.dashboard.controller;

import com.bat.uberlandia.dashboard.model.*;
import com.bat.uberlandia.dashboard.repository.*;
import com.bat.uberlandia.dashboard.service.ChamadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final ChamadoRepository chamadoRepository;
    private final ChamadoService chamadoService;
    private final MaquinaRepository maquinaRepository;
    private final SetorRepository setorRepository;
}
@GetMapping("/metricas")
public Map<String, Object> metricas() {
    return Map.of(
        "tempoMedioReparoGeralMinutos", chamadoService.getTempoMedioReparoGeral(),
        "chamadosAbertos", chamadoRepository.findByStatus(Chamado.Status.ABERTO).size(),
        "chamadosEmAndamento", chamadoRepository.findByStatus(Chamado.Status.EM_ANDAMENTO).size(),
        "chamadosConcluidos", chamadoRepository.findByStatus(Chamado.Status.CONCLUIDO).size()
    );
}
@GetMapping("/chamados/abertos")
public List<Chamado> chamadosAbertos() {
    return chamadoRepository.findByStatus(Chamado.Status.ABERTO);
}

@GetMapping("/chamados/{id}/tempo-reparo")
public Map<String, Object> tempoReparo(@PathVariable Long id) {
    Chamado c = chamadoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Chamado nao encontrado"));

    long segundos = c.getTempoReparoSegundos();
    return Map.of(
        "chamadoId", c.getId(),
        "segundos", segundos,
        "minutos", segundos / 60.0,
        "status", c.getStatus().name()
    );
}

@GetMapping("/mtbf/maquina/{id}")
public Map<String, Object> mtbfMaquina(@PathVariable Long id) {
    Maquina m = maquinaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Maquina nao encontrada"));

    return Map.of(
        "maquina", m.getNome(),
        "mtbfHoras", chamadoService.calcularMtbfPorMaquina(id)
    );
}

@GetMapping("/mtbf/setor/{id}")
public Map<String, Object> mtbfSetor(@PathVariable Long id) {
    Setor s = setorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Setor nao encontrado"));

    return Map.of(
        "setor", s.getNome(),
        "mtbfHoras", chamadoService.calcularMtbfPorSetor(id)
    );
}
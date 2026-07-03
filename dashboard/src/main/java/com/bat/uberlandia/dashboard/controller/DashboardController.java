
package com.bat.uberlandia.dashboard.controller;

import com.bat.uberlandia.dashboard.model.*;
import com.bat.uberlandia.dashboard.repository.*;
import com.bat.uberlandia.dashboard.service.AlertaService;
import com.bat.uberlandia.dashboard.service.ChamadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ChamadoRepository chamadoRepository;
    private final MaquinaRepository maquinaRepository;
    private final SetorRepository setorRepository;
    private final ChamadoService chamadoService;
    private final AlertaService alertaService;
    private final NotificacaoRepository notificacaoRepository;

    @GetMapping
    public String dashboard(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,

            Model model) {
            
            
                List<Chamado> abertos = chamadoRepository.findByStatus(Chamado.Status.ABERTO);
                List<Chamado> emAndamento = chamadoRepository.findByStatus(Chamado.Status.EM_ANDAMENTO);
                List<Chamado> pausados = chamadoRepository.findByStatus(Chamado.Status.PAUSADO);
                List<Chamado> escalados = chamadoRepository.findByStatus(Chamado.Status.ESCALADO);
                List<Chamado> concluidos;
                
                if (dataInicio != null && dataFim != null) {
                    concluidos = chamadoRepository.findByDataAberturaBetween(dataInicio, dataFim);
                } else {
                    concluidos = chamadoRepository.findByStatus(Chamado.Status.CONCLUIDO);
                }
                
                model.addAttribute("abertos", abertos);
                model.addAttribute("emAndamento", emAndamento);
                model.addAttribute("pausados", pausados);
                model.addAttribute("escalados", escalados);
                model.addAttribute("concluidos", concluidos);
                
                model.addAttribute("tempoMedioReparo", chamadoService.getTempoMedioReparoGeral());
                model.addAttribute("totalAlertas", alertaService.contarAlertasNaoLidos());
               
       
List<Setor> setores = setorRepository.findAll();
Map<String, Double> mtbfPorSetor = new LinkedHashMap<>();

for (Setor s : setores) {
    mtbfPorSetor.put(s.getNome(), chamadoService.calcularMtbfPorSetor(s.getId()));
}

model.addAttribute("mtbfPorSetor", mtbfPorSetor);

List<Maquina> maquinas = maquinaRepository.findAll();
Map<String, Double> mtbfPorMaquina = new LinkedHashMap<>();

for (Maquina m : maquinas) {
    mtbfPorMaquina.put(m.getNome(), chamadoService.calcularMtbfPorMaquina(m.getId()));
}

model.addAttribute("mtbfPorMaquina", mtbfPorMaquina);

model.addAttribute("dataInicio", dataInicio);
model.addAttribute("dataFim", dataFim);

return "dashboard";
}
}



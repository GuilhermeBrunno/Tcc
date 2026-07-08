package com.bat.uberlandia.dashboard.repository;

import com.bat.uberlandia.dashboard.model.Chamado;
import com.bat.uberlandia.dashboard.model.Chamado.MotivoFalha;

public interface MotivoFalhaCount {
    Chamado.MotivoFalha getMotivo();
    Long getTotal();
}
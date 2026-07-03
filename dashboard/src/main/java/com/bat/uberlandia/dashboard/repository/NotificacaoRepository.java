package com.bat.uberlandia.dashboard.repository;

import com.bat.uberlandia.dashboard.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findByChamadoIdOrderByDataEnvioDesc(Long chamadoId);

    List<Notificacao> findByUsuarioIdAndLidaFalse(Long usuarioId);

    List<Notificacao> findByLidaFalseOrderByDataEnvioDesc();
}
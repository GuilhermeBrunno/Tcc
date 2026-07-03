package com.bat.uberlandia.dashboard.repository;

import com.bat.uberlandia.dashboard.model.Chamado;
import com.bat.uberlandia.dashboard.model.Chamado.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findByStatus(Status status);

    List<Chamado> findByStatusIn(List<Status> statusList);

    List<Chamado> findByStatusAndAlerta30MinEnviadoFalse(Status status);

    List<Chamado> findByMaquinaSetorIdAndDataAberturaBetween(
            Long setorId, LocalDateTime inicio, LocalDateTime fim);

    List<Chamado> findByDataAberturaBetween(
            LocalDateTime inicio, LocalDateTime fim);

    List<Chamado> findByMaquinaId(Long maquinaId);

    List<Chamado> findByMaquinaSetorId(Long setorId);

    List<Chamado> findTop10ByMaquinaIdAndStatusOrderByDataConclusaoDesc(
            Long maquinaId, Status status);
            
            @Query("SELECT c FROM Chamado c WHERE c.maquina.setor.id = :setorId " +
       "AND c.status = :status AND c.dataConclusao BETWEEN :inicio AND :fim " +
       "ORDER BY c.dataConclusao ASC")
List<Chamado> findChamadosConcluidosPorSetorEPeriodo(
        @Param("setorId") Long setorId,
        @Param("status") Status status,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim);

@Query("SELECT AVG(c.tempoReparoAcumulado) FROM Chamado c " +
       "WHERE c.status = 'CONCLUIDO'")
Double findTempoMedioReparoGeral();

@Query("SELECT AVG(c.tempoReparoAcumulado) FROM Chamado c " +
       "WHERE c.status = 'CONCLUIDO' AND c.maquina.setor.id = :setorId")
Double findTempoMedioReparoPorSetor(@Param("setorId") Long setorId);
}
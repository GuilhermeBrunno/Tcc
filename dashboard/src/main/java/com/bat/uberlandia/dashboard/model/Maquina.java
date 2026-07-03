package com.bat.uberlandia.dashboard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "maquinas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Maquina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Column(length = 50)
    private String modelo;

    @Column(length = 50)
    private String numeroSerie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id", nullable = false)
    @ToString.Exclude
    private Setor setor;

    @OneToMany(mappedBy = "maquina", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Chamado> chamados = new ArrayList<>();
}
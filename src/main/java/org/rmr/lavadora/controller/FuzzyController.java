package org.rmr.lavadora.controller;

import org.rmr.lavadora.model.TrapezoidalMembershipFunction;
import org.rmr.lavadora.model.TriangularMembershipFunction;

import java.util.HashMap;
import java.util.Map;

public class FuzzyController {
    // --- Inicialización de todas las membresías y reglas --- \\
    // - Entradas - \\
    // GradoSuciedad [0 100] \\
    private TriangularMembershipFunction suciedadPoca = new TriangularMembershipFunction(0,0,40);
    private TriangularMembershipFunction suciedadNormal = new TriangularMembershipFunction(20,50,80);
    private TriangularMembershipFunction suciedadMucha = new TriangularMembershipFunction(60,100,100);
    // TipoTejido [0 10] \\
    private TrapezoidalMembershipFunction tejidoDelicado = new TrapezoidalMembershipFunction(0,0,1,3);
    private TriangularMembershipFunction tejidoNormal = new TriangularMembershipFunction(2,5,8);
    private TrapezoidalMembershipFunction tejidoResistente = new TrapezoidalMembershipFunction(7,9,10,10);
    //PesoCarga [0 10] \\
    private TriangularMembershipFunction pesoPequeno = new TriangularMembershipFunction(0,0,4);
    private TriangularMembershipFunction pesoMediano = new TriangularMembershipFunction(2,5,8);
    private TriangularMembershipFunction pesoGrande = new TriangularMembershipFunction(6,10,10);
    // - Salidas - \\
    // CantidadAgua [10 50] \\
    private TrapezoidalMembershipFunction aguaBaja = new TrapezoidalMembershipFunction(10,10,15,25);
    private TriangularMembershipFunction aguaMedia = new TriangularMembershipFunction(20,30,40);
    private TrapezoidalMembershipFunction aguaAlta = new TrapezoidalMembershipFunction(35,45,50,50);
    // TiempoLavado [15 75] \\
    private TrapezoidalMembershipFunction tiempoCorto = new TrapezoidalMembershipFunction(15,15,20,35);
    private TriangularMembershipFunction tiempoMedio = new TriangularMembershipFunction(30,45,60);
    private TrapezoidalMembershipFunction tiempoLargo = new TrapezoidalMembershipFunction(55,65,75,75);
    // VelocidadCentrifugado [400 1400] \\
    private TrapezoidalMembershipFunction velocidadLenta = new TrapezoidalMembershipFunction(400,400,500,700);
    private TriangularMembershipFunction velocidadMedia = new TriangularMembershipFunction(600,900,1200);
    private TrapezoidalMembershipFunction velocidadRapida = new TrapezoidalMembershipFunction(1100,1300,1400,1400);

    // --- Mapa con las salidas y valores calculados --- \\
    public Map<String, Double> calculateOutputs(double suciedad, double tejido, double peso) {
        // -- Evaluar las reglas -- \\
        // Fuzzificar las entradas \\
        // Suciedad \\
        double gradoSuciedadPoco = suciedadPoca.getMembership(suciedad);
        double gradoSuciedadNomral = suciedadNormal.getMembership(suciedad);
        double gradoSuciedadMucha = suciedadMucha.getMembership(suciedad);
        // Tejido \\
        double gradoTejidoDelicado = tejidoDelicado.getMembership(tejido);
        double gradoTejidoNormal = tejidoNormal.getMembership(tejido);
        double gradoTejidoResistenete = tejidoResistente.getMembership(tejido);
        // Peos \\
        double gradoPesoPequeno = pesoPequeno.getMembership(peso);
        double gradoPesoMedio = pesoMediano.getMembership(peso);
        double gradoPesoGrande = pesoGrande.getMembership(peso);

        // -- Calculo de las reglas -- \\ - // AND con Math.min() \\
        double[] ruleSregths = new double[26];

        // --- Aplicar Reglas --- \\
        ruleSregths[1] = gradoTejidoDelicado;
        ruleSregths[2] = Math.min(gradoTejidoDelicado, gradoSuciedadMucha);
        ruleSregths[3] = gradoTejidoDelicado;
        ruleSregths[4] = Math.min(gradoSuciedadMucha, gradoTejidoResistenete);
        ruleSregths[5] = Math.min(gradoSuciedadMucha, gradoTejidoNormal);
        ruleSregths[6] = Math.min(gradoSuciedadMucha, gradoPesoGrande);
        ruleSregths[7] = Math.min(gradoSuciedadPoco, gradoPesoPequeno);
        ruleSregths[8] = Math.min(gradoSuciedadPoco, gradoPesoGrande);
        ruleSregths[9] = Math.min(gradoSuciedadNomral, Math.min(gradoTejidoNormal, gradoPesoMedio));
        ruleSregths[10] = gradoPesoGrande;
        ruleSregths[11] = gradoPesoPequeno;
        ruleSregths[12] = gradoPesoPequeno;
        ruleSregths[13] = gradoTejidoNormal;
        ruleSregths[14] = Math.min(gradoSuciedadNomral, gradoPesoGrande);
        ruleSregths[15] = Math.min(gradoSuciedadNomral, gradoTejidoResistenete);
        ruleSregths[16] = Math.min(gradoSuciedadMucha, gradoPesoPequeno);
        ruleSregths[17] = Math.min(gradoTejidoDelicado, gradoPesoGrande);
        ruleSregths[18] = Math.min(gradoSuciedadPoco, gradoTejidoResistenete);
        ruleSregths[19] = Math.min(gradoSuciedadNomral, Math.min(gradoPesoPequeno, gradoTejidoNormal));
        ruleSregths[20] = Math.min(gradoSuciedadMucha, Math.min(gradoTejidoNormal, gradoPesoGrande));
        ruleSregths[21] = Math.min(gradoPesoGrande, gradoTejidoNormal);
        ruleSregths[22] = Math.min(gradoSuciedadPoco, gradoPesoMedio);
        ruleSregths[23] = Math.min(gradoSuciedadPoco, Math.min(gradoTejidoDelicado, gradoPesoPequeno));
        ruleSregths[24] = Math.min(gradoSuciedadMucha, Math.min(gradoTejidoResistenete, gradoPesoGrande));
        ruleSregths[25] = Math.min(gradoSuciedadNomral, Math.min(gradoTejidoResistenete, gradoPesoGrande));

        // --- Defuzzificación (Centro de gravedad) --- \\
        double agua = defuzzifyAgua(ruleSregths);
        double tiempo = defuzzifyTiempo(ruleSregths);
        double velocidad = defuzzifyVelocidad(ruleSregths);

        Map<String, Double> outputs = new HashMap<>();
        outputs.put("agua", agua);
        outputs.put("tiempo", tiempo);
        outputs.put("velocidad", velocidad);

        return outputs;
    }

    // --- Calculos --- \\\
    private double defuzzifyAgua(double[] strengths) {
        double numerator = 0;
        double denominator = 0;
        double step = 0.5; // rango de salida

        for (double agua = 10; agua <= 50; agua += step) {
            // -- Grado de membrecía por cada conjunto de salida -- \\
            double membBaja = aguaBaja.getMembership(agua);
            double membMedia = aguaMedia.getMembership(agua);
            double membAlta = aguaAlta.getMembership(agua);

            // - Determinar la influencia de cada regla sobre esta salida - \\
            // - El resultado de cada regla se corta a la altura de su fuerza - \\
            // - La suna es para OR - \\
            double aguaBajaClipped = Math.min(membBaja, strengths[7] + strengths[19] + strengths[23]);
            double aguaMediaClipped = Math.min(membMedia, strengths[9]);
            double aguaAltaClipped = Math.min(membAlta, strengths[4] + strengths[6] + strengths[8] + strengths[10] + strengths[14] + strengths[17] + strengths[20] + strengths[24]);

            // -- Se cambian las formas cortadas usando el maximo (Union de conjuntos difusos) -- \\
            double aggtegatedMembership = Math.max(aguaBajaClipped, Math.max(aguaMediaClipped, aguaAltaClipped));

            numerator += agua * aggtegatedMembership;
            denominator += aggtegatedMembership;
        }
        return (denominator == 0) ? 0 : numerator / denominator;
    }

    private double defuzzifyTiempo(double[] strengths) {
        double numerator = 0;
        double denominator = 0;
        double step = 1.0;

        for (double tiempo = 15; tiempo <= 75; tiempo += step) {
            double membCorta = tiempoCorto.getMembership(tiempo);
            double membMedia = tiempoMedio.getMembership(tiempo);
            double membLarga = tiempoLargo.getMembership(tiempo);

            double tiempoCortoClipped = Math.min(membCorta, strengths[3] + strengths[7] + strengths[8] + strengths[18] + strengths[22] + strengths[23]);
            double tiempoMedioClipped = Math.min(membMedia, strengths[2] + strengths[9] + strengths[14] + strengths[15] + strengths[16] + strengths[19]);
            double tiempoLargoClipped = Math.min(membLarga, strengths[4] + strengths[5] + strengths[20] + strengths[24] + strengths[25]);

            double aggregatedMembership = Math.max(tiempoCortoClipped, Math.max(tiempoMedioClipped, tiempoLargoClipped));

            numerator += tiempo * aggregatedMembership;
            denominator += aggregatedMembership;
        }

        return (denominator == 0) ? 0 : numerator / denominator;
    }

    private double defuzzifyVelocidad(double[] strengths) {
        double numerator = 0;
        double denominator = 0;
        double step = 10.0;

        for (double velocidad = 400; velocidad <= 1400; velocidad += step) {
            double membLenta = velocidadLenta.getMembership(velocidad);
            double membMedia = velocidadMedia.getMembership(velocidad);
            double membRapida = velocidadRapida.getMembership(velocidad);

            double velocidadLentaClipped = Math.min(membLenta, strengths[1] + strengths[23]);
            double velocidadMediaClipped = Math.min(membMedia, strengths[9] + strengths[13] + strengths[21]);
            double velocidadRapidaClipped = Math.min(membRapida, strengths[12] + strengths[15] + strengths[18] + strengths[24] + strengths[25]);

            double aggregatedMembership = Math.max(velocidadLentaClipped, Math.max(velocidadMediaClipped, velocidadRapidaClipped));

            numerator += velocidad * aggregatedMembership;
            denominator += aggregatedMembership;
        }
        return (denominator == 0) ? 0 : numerator / denominator;
    }
}







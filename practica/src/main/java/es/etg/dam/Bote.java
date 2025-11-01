package es.etg.dam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static es.etg.dam.Constantes.Botes.MAX_PERSONAS;
import static es.etg.dam.Constantes.Botes.MAX_RETARDO;
import static es.etg.dam.Constantes.Botes.MIN_PERSONAS;
import static es.etg.dam.Constantes.Botes.MIN_RETARDO;
import static es.etg.dam.Constantes.Botes.MSG_ERROR_ID;
import static es.etg.dam.Constantes.Pasajeros.HOMBRE;
import static es.etg.dam.Constantes.Pasajeros.MUJER;
import static es.etg.dam.Constantes.Pasajeros.NINO;

public class Bote {

    private static final Random RANDOM = new Random();
    private static final String FORMATO_RESULTADO = "%s;%d;%d;%d;%d%n";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println(MSG_ERROR_ID);
            return;
        }

        String id = args[0];

        int total = RANDOM.nextInt(MAX_PERSONAS) + MIN_PERSONAS;
        List<Pasajeros> pasajeros = new ArrayList<>(total);

        for (int i = 0; i < total; i++) {
            pasajeros.add(new Pasajeros());
        }

        int mujeres = 0, hombres = 0, ninos = 0;
        for (Pasajeros p : pasajeros) {
            switch (p.getTipo()) {
                case MUJER ->
                    mujeres++;
                case HOMBRE ->
                    hombres++;
                case NINO ->
                    ninos++;
            }
        }

        try {
            Thread.sleep((RANDOM.nextInt(MAX_RETARDO - MIN_RETARDO + 1) + MIN_RETARDO) * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        System.out.printf(FORMATO_RESULTADO, id, total, mujeres, hombres, ninos);
    }
}

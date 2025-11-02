package es.etg.dam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static es.etg.dam.Constantes.Botes.CLASE_BOTE;
import static es.etg.dam.Constantes.Botes.CLASSPATH;
import static es.etg.dam.Constantes.Botes.CP;
import static es.etg.dam.Constantes.Botes.DOS_PUNTOS;
import static es.etg.dam.Constantes.Botes.ID_BOTE;
import static es.etg.dam.Constantes.Botes.JAVA;
import static es.etg.dam.Constantes.Botes.MAX_RETARDO;
import static es.etg.dam.Constantes.Botes.MIN_RETARDO;
import static es.etg.dam.Constantes.Botes.MSG_ERROR_GENERAR_INFORME;
import static es.etg.dam.Constantes.Botes.MSG_FIN;
import static es.etg.dam.Constantes.Botes.MSG_INICIO;
import static es.etg.dam.Constantes.Botes.MSG_LANZANDO_BOTE;
import static es.etg.dam.Constantes.Botes.NUM_BOTES;
import static es.etg.dam.Constantes.Botes.PUNTO_COMA;

public class ServicioEmergencias {

    private static final Random RANDOM = new Random();
    private final Map<String, int[]> registros = new LinkedHashMap<>();

    public void gestionarEmergencia() {
        System.out.println(MSG_INICIO);
        String classpath = System.getProperty(CLASSPATH);

        for (int i = 0; i < NUM_BOTES; i++) {
            String id = String.format(ID_BOTE, i);

            try {
                Process process = new ProcessBuilder(JAVA, CP, classpath, CLASE_BOTE, id).start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String linea = reader.readLine();
                    process.waitFor();

                    if (linea != null && linea.contains(PUNTO_COMA)) {
                        String[] partes = linea.split(PUNTO_COMA);
                        int total = Integer.parseInt(partes[1]);
                        int mujeres = Integer.parseInt(partes[2]);
                        int hombres = Integer.parseInt(partes[3]);
                        int ninos = Integer.parseInt(partes[4]);

                        registros.put(id, new int[]{total, mujeres, hombres, ninos});
                        System.out.printf(MSG_LANZANDO_BOTE, id, total);
                    }
                }

                Thread.sleep((RANDOM.nextInt(MAX_RETARDO - MIN_RETARDO + 1) + MIN_RETARDO) * 1000L);

            } catch (IOException e) {
                System.err.println(MSG_ERROR_GENERAR_INFORME + id + DOS_PUNTOS + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println(MSG_FIN);
        new GeneradorInforme().generar(registros);
    }
}

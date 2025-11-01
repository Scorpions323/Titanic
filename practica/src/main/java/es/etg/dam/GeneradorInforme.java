package es.etg.dam;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static es.etg.dam.Constantes.Botes.ID_BOTE;
import static es.etg.dam.Constantes.Botes.NUM_BOTES;
import static es.etg.dam.Constantes.Informe.ENCABEZADO;
import static es.etg.dam.Constantes.Informe.FORMATO_BLOQUE_BOTE;
import static es.etg.dam.Constantes.Informe.FORMATO_FECHA;
import static es.etg.dam.Constantes.Informe.FORMATO_RESUMEN;
import static es.etg.dam.Constantes.Informe.HOMBRES;
import static es.etg.dam.Constantes.Informe.INFORME;
import static es.etg.dam.Constantes.Informe.MSG_EXITO_INFORME;
import static es.etg.dam.Constantes.Informe.MUJERES;
import static es.etg.dam.Constantes.Informe.NINOS;
import static es.etg.dam.Constantes.Informe.TEXTO_EJECUCION;
import static es.etg.dam.Constantes.Informe.TOTAL;

public class GeneradorInforme {

    public void generar(Map<String, int[]> registros) {
        try (FileWriter writer = new FileWriter(INFORME)) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(FORMATO_FECHA);

            writer.write(ENCABEZADO + "\n\n");
            writer.write(TEXTO_EJECUCION + dtf.format(LocalDateTime.now()) + "\n\n");

            int total = 0, mujeres = 0, hombres = 0, ninos = 0;

            for (int i = 0; i < NUM_BOTES; i++) {
                String id = String.format(ID_BOTE, i);
                int[] datos = registros.get(id);
                if (datos == null) {
                    continue;
                }

                writer.write(String.format(FORMATO_BLOQUE_BOTE,
                        id,
                        TOTAL, datos[0],
                        MUJERES, datos[1],
                        HOMBRES, datos[2],
                        NINOS, datos[3]
                ));

                total += datos[0];
                mujeres += datos[1];
                hombres += datos[2];
                ninos += datos[3];
            }

            writer.write(String.format(FORMATO_RESUMEN,
                    TOTAL, total,
                    MUJERES, mujeres,
                    HOMBRES, hombres,
                    NINOS, ninos
            ));

            System.out.println(MSG_EXITO_INFORME);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

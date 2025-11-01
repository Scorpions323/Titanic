package es.etg.dam;

public final class Constantes {

    private Constantes() {
    }

    public static final class Pasajeros {

        public static final String MUJER = "Mujer";
        public static final String HOMBRE = "Hombre";
        public static final String NINO = "Niño";
    }

    public static final class Botes {

        public static final int NUM_BOTES = 20;
        public static final String ID_BOTE = "B%02d";

        public static final int MIN_PERSONAS = 1;
        public static final int MAX_PERSONAS = 100;
        public static final int MIN_RETARDO = 2;
        public static final int MAX_RETARDO = 6;

        public static final String JAVA = "java";
        public static final String CP = "-cp";
        public static final String CLASSPATH = "java.class.path";
        public static final String CLASE_BOTE = "es.etg.dam.Bote";
        public static final String PUNTO_COMA = ";";
        public static final String MSG_ERROR_ID = "Debe indicar el identificador del bote";

        public static final String MSG_LANZANDO_BOTE = "%s → %d personas%n";
        public static final String MSG_INICIO = "Iniciando gestión de emergencia...";
        public static final String MSG_FIN = "Todos los botes han sido procesados.";
    }

    public static final class Informe {

        public static final String INFORME = "Informe.md";
        public static final String ENCABEZADO = "# SERVICIO DE EMERGENCIAS";
        public static final String FORMATO_FECHA = "dd/MM/yyyy 'a las' HH:mm:ss";
        public static final String TEXTO_EJECUCION = "Ejecución realizada el día ";
        public static final String TOTAL = "Total Salvados";
        public static final String MUJERES = "Mujeres";
        public static final String HOMBRES = "Hombres";
        public static final String NINOS = "Niños";

        public static final String FORMATO_BLOQUE_BOTE = "## Bote %s\n- %s %d\n  - %s %d\n  - %s %d\n  - %s %d\n\n";

        public static final String FORMATO_RESUMEN = "## Total\n- %s %d\n  - %s %d\n  - %s %d\n  - %s %d";

        public static final String MSG_EXITO_INFORME = "Informe.md generado correctamente.";
        public static final String MSG_NO_IMPLEMENTADO = "Este método no está implementado en esta versión";
    }
}

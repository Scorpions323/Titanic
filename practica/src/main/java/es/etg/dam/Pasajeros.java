package es.etg.dam;

import java.util.Random;

import static es.etg.dam.Constantes.Pasajeros.HOMBRE;
import static es.etg.dam.Constantes.Pasajeros.MUJER;
import static es.etg.dam.Constantes.Pasajeros.NINO;

public class Pasajeros {

    private static final Random RANDOM = new Random();
    private final String tipo;

    public Pasajeros() {
        String[] tipos = {MUJER, HOMBRE, NINO};
        this.tipo = tipos[RANDOM.nextInt(tipos.length)];
    }

    public String getTipo() {
        return tipo;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.diegoleon.cubilete;

import java.util.Random;

/**
 *
 * @author diegoleon
 */
public class CubileteClass {

    // Constantes
    private static final String[] CARTAS = {"9", "10", "J", "Q", "K", "As", "-"};
    private static final int CARTA_NULL = 6;

    private static final String[] JUGADAS = {"Ninguna", "Un par", "Dos pares", "Tercia", "Full", "Poker", "Quintilla"};
    private static final int TIRADAS = 3;
    private static final int PUNTOS_X_PUNTOS = 10;
    private static final int PUNTOS_X_JUGADA = 20;

    // Datos dinámicos
    private int jTiradas = 0; // nuero de tiradas
    private int jPuntos = 0; // puntos de la jugada
    private int[] jMano = {CARTA_NULL, CARTA_NULL, CARTA_NULL, CARTA_NULL, CARTA_NULL}; // mano actual
    private int jInicio = 0; // valor de la tirada inicial

    private int[] mMano = {CARTA_NULL, CARTA_NULL, CARTA_NULL, CARTA_NULL, CARTA_NULL};
    private int mInicio = 0;

    // validar jugadas
    /**
     * 0: Ninguna 1: Un Par 2: Dos Pares 3: Tercia 4: Full 5: Poker 6: Quintilla
     */
    private static int getJugada(int[] array) {
        int[] contadorCartas = {0, 0, 0, 0, 0, 0};

        for (int id : array) {
            if (id == CARTA_NULL) {
                return 0;
            }

            contadorCartas[id]++;
        }

        for (int i = 0; i < contadorCartas.length; i++) {
            int valor = contadorCartas[i];

            if (valor == 5) {
                return 6;
            }
            if (valor == 4) {
                return 5;
            }
            if (valor == 3) {
                for (int valor2 : contadorCartas) {
                    if (valor2 == 2) {
                        return 4;
                    }
                }
                return 3;
            }
            if (valor == 2) {
                for (int j = i + 1; j < contadorCartas.length; j++) {
                    if (contadorCartas[j] == 2) {
                        return 2;
                    }
                }
                return 1;
            }
        }

        return 0;
    }

    private static int getManoPuntos(int[] array) {
        int puntos = 0;
        for (int i = 0; i < array.length; i++) {
            puntos += array[i] + 1;
        }
        return puntos;
    }

    private static int[] crearMano() {
        Random random = new Random();
        int[] mano = new int[5];
        for (int i = 0; i < mano.length; i++) {
            mano[i] = random.nextInt(6);
        }
        return mano;
    }

    private int tirarDado() {
        Random random = new Random();
        int dado = random.nextInt(6);
        return dado;
    }

    private String getNombreCara(int id) {
        return CARTAS[id];
    }

    private String[] getNombreMano(int[] mano) {
        String[] array = new String[5];

        for (int i = 0; i < 5; i++) {
            array[i] = getNombreCara(mano[i]);
        }

        return array;
    }

    // Acciones
    // 0: Empate 1: Ganador Jugador 2: Ganador Maquina
    public int iniciar() {
        jInicio = tirarDado();
        mInicio = tirarDado();

        if (jInicio == mInicio) {
            return 0;
        }

        boolean jugadorGanador = jInicio > mInicio;

        jTiradas++;
        jMano = crearMano();

        if (!jugadorGanador) {
            mMano = crearMano();
        }

        return jugadorGanador ? 1 : 2;
    }

    // true: se cambio false: no se pudo cammmbiar
    public boolean cambiarMano(int[] mano) {
        if (jTiradas >= TIRADAS) {
            return false;
        }

        jTiradas++;

        for (int p : mano) {
            if (p >= 5) {
                continue;
            }

            jMano[p] = tirarDado();
        }

        return true;
    }

    // 0: Empate 1: Ganador Jugador 2: Ganador Maquina
    public int confirmarMano() {
        if (mMano[0] == CARTA_NULL) {
            mMano = crearMano();
        }

        int jJugada = getJugada(jMano);
        int mJugada = getJugada(mMano);

        if (jJugada == mJugada) {
            return 0;
        }

        if (jJugada == 0 && mJugada == 0) {

            int jPts = getManoPuntos(jMano);
            int mPts = getManoPuntos(mMano);

            if (jPts == mPts) {
                return 0;
            }

            if (jPts > mPts) {
                jPuntos += (PUNTOS_X_PUNTOS * jPts);
                return 1;
            }

            return 2;
        }

        boolean jugadorGanador = jJugada > mJugada;

        if (jugadorGanador) {
            jPuntos += (PUNTOS_X_JUGADA * jJugada);
            return 1;
        }

        return 2;
    }

    public String[] getManoJugador() {
        return getNombreMano(jMano);
    }

    public String[] getManoMaquina() {
        return getNombreMano(mMano);
    }

    public String getTiradaInicialJugador() {
        return getNombreCara(jInicio);
    }

    public String getTiradaInicialMaquina() {
        return getNombreCara(mInicio);
    }

    public int getJugadorTiradas() {
        return jTiradas;
    }

    public int getJuegoTiradas() {
        return TIRADAS;
    }

    public String getJugadorManoNombre() {
        return JUGADAS[getJugada(jMano)];
    }

    public String getMaquinaManoNombre() {
        return JUGADAS[getJugada(mMano)];
    }

    public int getJugadorPuntos() {
        return jPuntos;
    }
}

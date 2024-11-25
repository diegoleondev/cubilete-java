/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package dev.diegoleon.cubilete;

/**
 *
 * @author diegoleon
 */
public class Cubilete {

    public static void main(String[] args) {
        CubileteClass cubilete = new CubileteClass();

        int inicia = cubilete.iniciar();

        if (inicia == 0) {
            System.out.println("Empate tirar de nuevo.");
            return;
        }

        System.out.println("Derecho de iniciar");
        System.out.println("Jugador: " + cubilete.getTiradaInicialJugador());
        System.out.println("Maquina: " + cubilete.getTiradaInicialMaquina());
        System.out.println("Ganador: " + ((inicia == 1) ? "Jugador" : "maquina"));

        System.out.println("");

        printMano(cubilete);

        boolean salir = false;
        while (salir == false && (cubilete.getJugadorTiradas() < cubilete.getJuegoTiradas())) {
            System.out.println("0: Cambiar mano  1: Confirmar mano");
            int opcion = Integer.parseInt(System.console().readLine());

            if (opcion == 1) {
                salir = true;
                continue;
            }

            System.out.print("Cartas a cambiar separadas por coma: ");
            String[] cartas = System.console().readLine().split(",");
            System.out.println("");

            boolean result = cubilete.cambiarMano(stringANummeros(cartas));
            System.out.print("Tiarada " + cubilete.getJugadorTiradas() + " de " + cubilete.getJuegoTiradas() + ": ");
            imprimirMano(cubilete.getManoJugador());
            System.out.println("");

            if (result == false) {
                salir = true;
            }
        }

        int result = cubilete.confirmarMano();
        System.out.println("");
        printMano(cubilete);
        System.out.println("");

        switch (result) {
            case 0 ->
                System.out.println("Empate");
            case 1 ->
                System.out.println("Eres el ganador!!!");
            case 2 ->
                System.out.println("La maquina gana :(");
            default ->
                System.out.println("Algo salio mal");
        }

        System.out.println("El jugador gana " + cubilete.getJugadorPuntos() + " puntos");
    }

    // funcion para imprimir la mano
    private static void imprimirMano(String[] mano) {
        for (String carta : mano) {
            System.out.print(carta + " ");
        }
    }

    private static int[] stringANummeros(String[] array) {
        int[] x = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            x[i] = Integer.parseInt(array[i]);
        }

        return x;
    }

    private static void printMano(CubileteClass cubilete) {
        System.out.println("Manos");
        System.out.print("Maquina: ");
        imprimirMano(cubilete.getManoMaquina());
        System.out.print(" (" + cubilete.getMaquinaManoNombre() + ")");
        System.out.print("\nJugador: ");
        imprimirMano(cubilete.getManoJugador());
        System.out.print("(" + cubilete.getJugadorManoNombre() + ")");

        System.out.println("");
    }
}

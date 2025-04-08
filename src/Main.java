import java.util.Scanner;

public class Main {
    public static class BigViginere
    {
        private int[] key;
        private char [][] alfabeto;

        public BigViginere()
        {
            Scanner teclado = new Scanner(System.in);
            String llaveString; // Se crea la variable que va a guardar la llave en string que digite el humano
            key = new int[0];  // se inicializa vacio la key , como lo pide el problema

            System.out.println("Ingrese la llave del cifrado, ¡solo pueden ser numeros! ");
            llaveString = teclado.nextLine();

            key = new int[llaveString.length()]; // se cambia el valor del arreglo key , para poder llenarlo con la llaveString

            for (int i = 0; i < llaveString.length(); i++)
            {
                key[i] = Character.getNumericValue(llaveString.charAt(i));  //se descompone la llavestring en cada posicion, para añadirlo a la posicion correcta del arreglo key[]
            }

            alfabeto = new char[64][64];
            String caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
            for(int i = 0; i < 64; i++)
            {
                for(int j = 0; j < 64; j++)
                {
                    alfabeto[i][j] = caracteres.charAt((j + i) % 64);
                }
            }
        }

        public BigViginere(String numericKey)
        {
            key = new int[numericKey.length()];
            for(int i = 0; i < numericKey.length(); i++)
            {
                key[i] = Character.getNumericValue(numericKey.charAt(i));
            }
            alfabeto = new char[64][64];
            String caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
            for(int i = 0; i < 64; i++)
            {
                for(int j = 0; j < 64; j++)
                {
                    alfabeto[i][j] = caracteres.charAt((j + i) % 64);
                }
            }
        }
        public String encrypt(String message)
        {
            long inicio = System.currentTimeMillis(); // Inicio del temporizador (añadido)
            String MensajeEncriptado = "";
            int LargoMensaje = message.length();
            int largoKey = key.length;

            for(int i = 0 ; i < LargoMensaje; i++)
            {
                char caracter = message.charAt(i);
                int fila = 0;

                while(fila < 64 && alfabeto[fila][0] != caracter)
                {
                    fila++;
                }

                int columna = key[i % largoKey];
                MensajeEncriptado += alfabeto[fila][columna];
            }
            long fin = System.currentTimeMillis(); // Fin del temporizador (añadido)
            System.out.println("Tiempo de cifrado: " + (fin - inicio) + " ms"); // (añadido)
            return MensajeEncriptado;
        }

        public String Decrypt(String EncryptedMessage)
        {
            long inicio = System.currentTimeMillis(); // Inicio del temporizador (añadido)
            String MensajeDecriptado = "";
            int LargoMensaje = EncryptedMessage.length();
            int largoKey = key.length;

            for (int i = 0 ; i < LargoMensaje; i++)
            {
                char caracter = EncryptedMessage.charAt(i);
                int columna = key[i % largoKey];

                int fila = 0;
                while (alfabeto[fila][columna] != caracter)
                {
                    fila++;
                }
                MensajeDecriptado += alfabeto[fila][0];
            }
            long fin = System.currentTimeMillis(); // Fin del temporizador (añadido)
            System.out.println("Tiempo de descifrado: " + (fin - inicio) + " ms"); // (añadido)
            return MensajeDecriptado;
        }

        public void reEncrypt()
        {
            Scanner teclado = new Scanner(System.in);

            System.out.println("Ingrese el mensaje cifrado: ");
            String mensajeCifrado = teclado.nextLine();          //Se le pide al humano que digite su mensaje que esta cifrado

            long inicioDescifrado = System.currentTimeMillis(); // (añadido)
            String mensajeDescifrado = Decrypt(mensajeCifrado); // Con la funcion de descifrar que hicimos arriba, podemos obtener mas rapido el mennsaje original
            long finDescifrado = System.currentTimeMillis();   // (añadido)
            System.out.println("Tiempo de descifrado (reEncrypt): " + (finDescifrado - inicioDescifrado) + " ms"); // (añadido)

            System.out.println("El mensaje descifrado es: " + mensajeDescifrado);

            System.out.println("Ingrese una nueva llave, !Solo pueden ser numeros! : ");
            String llaveString = teclado.nextLine(); // Se le pide la nueva llave al humano

            for (int i = 0; i < llaveString.length(); i++)
            {
                key[i] = Character.getNumericValue(llaveString.charAt(i)); // descomponemos la llave que esta en string, uno en uno, y la añadimos a nuestro arreglo
            }

            long inicioCifrado = System.currentTimeMillis(); // (añadido)
            String NuevoMensajeCifrado = encrypt(mensajeDescifrado); // Ciframos el mensaje con la nueva llave, ocupamos la funcion que hicimos arriba de cifrar
            long finCifrado = System.currentTimeMillis();   // (añadido)
            System.out.println("Tiempo de cifrado (reEncrypt): " + (finCifrado - inicioCifrado) + " ms"); // (añadido)

            System.out.println("El mensaje cifrado con la nueva llave es: " + NuevoMensajeCifrado);
        }

        public char search(int position)
        {
            long inicio = System.currentTimeMillis(); // (añadido)
            if (position > 4096)
            {
                return 0;
            }
            int contador = 0;
            for (int fila = 0; fila < 64; fila++)
            {
                for (int columna = 0; columna < 64; columna++)
                {
                    if (contador == position)
                    {
                        long fin = System.currentTimeMillis(); // (añadido)
                        System.out.println("Tiempo de búsqueda (search): " + (fin - inicio) + " ms"); // (añadido)
                        return alfabeto[fila][columna];
                    }
                    contador++;
                }
            }
            long fin = System.currentTimeMillis(); // (añadido)
            System.out.println("Tiempo de búsqueda (search): " + (fin - inicio) + " ms"); // (añadido)
            return 0;
        }

        public char optimalSearch(int position)
        {
            long inicio = System.currentTimeMillis(); // (añadido)
            int fila = position / 64;
            int columna = position % 64;
            long fin = System.currentTimeMillis(); // (añadido)
            System.out.println("Tiempo de búsqueda (optimalSearch): " + (fin - inicio) + " ms"); // (añadido)
            return alfabeto[fila][columna];
        }
    }

    public static void main(String[] args)
    {
        Scanner teclado = new Scanner(System.in);
        Scanner teclado2 = new Scanner(System.in);

        BigViginere c1 = new BigViginere();

        while(true)
        {
            System.out.println("Ingrese una opcion: ");
            System.out.println("1.- Cifrar mensaje");
            System.out.println("2.- Descifrar mensaje");
            System.out.println("3.- Re-Cifrar mensaje");
            System.out.println("4.- Buscar caracter en un numero de posicion (Deficiente)");
            System.out.println("5.-Buscar caracter en un numero de posicion (Eficiente)");
            System.out.println("6.- SALIR ");
            System.out.println("Ingrese una opcion: ");
            int opcion = teclado.nextInt();
            if(opcion == 1)
            {
                System.out.println("Ingrese el mensaje a cifrar: ");
                String mensaje = teclado2.nextLine();
                String MensajeCifrado = c1.encrypt(mensaje);
                System.out.println("El mensaje a cifrado es: " + MensajeCifrado);
            }
            else if(opcion == 2)
            {
                System.out.printf("Ingrese el mensaje cifrado que quiere descifrar: ");
                String mensaje = teclado2.nextLine();
                String MensajeDescifrado = c1.Decrypt(mensaje);
                System.out.println("El mensaje a descifrado es: " + MensajeDescifrado);
            }
            else if(opcion == 3)
            {
                c1.reEncrypt();
            }
            else if(opcion == 4)
            {
                System.out.println("Ingrese el numero de la posicion que quiera saber su caracter: ");
                int posicion = teclado.nextInt();
                char c = c1.search(posicion);
                System.out.println("El numero de la posicion " + posicion + " es: " + c);
            }
            else if(opcion == 5)
            {
                System.out.println("Ingrese el numero de la posicion que quiera saber su caracter: ");
                int posicion = teclado.nextInt();
                char c = c1.optimalSearch(posicion);
                System.out.println("El numero de la posicion " + posicion + " es: " + c);
            }
            else if(opcion == 6)
            {
                break;
            }
            else
            {
                System.out.println("Opcion no valida");
            }
        }
    }
}
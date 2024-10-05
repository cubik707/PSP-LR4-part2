package org.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("localhost");

        Scanner scanner = new Scanner(System.in);

        double x = getValidInput(scanner, "Введите значение x: ");
        double y = getValidInput(scanner, "Введите значение y: ");
        double z = getValidInput(scanner, "Введите значение z: ");

        String message = x + "," + y + "," + z;
        byte[] buffer = message.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9876);
        socket.send(packet);

        byte[] receiveBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        socket.receive(receivePacket);

        String result = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Результат вычислений: " + result);

        socket.close();
    }

    private static double getValidInput(Scanner scanner, String prompt) {
        double value = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            try {
                value = scanner.nextDouble();
                valid = true;  // Если ввод успешен, установить флаг valid в true
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите корректное числовое значение.");
                scanner.next();  // Очистить неверный ввод
            }
        }
        return value;
    }
}

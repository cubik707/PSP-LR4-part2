package org.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9876);

        System.out.println("Сервер запущен на порту " + socket.getLocalPort());

        while (true) {
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            // Ожидание пакета от клиента
            socket.receive(receivePacket);

            // Получение данных от клиента
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            System.out.println("Подключился клиент с адреса " + clientAddress.getHostAddress() + ":" + clientPort);

            String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Получено от клиента: " + data);

            // Парсинг входных данных
            String[] parts = data.split(",");
            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);
            double z = Double.parseDouble(parts[2]);

            // Вычисление значения функции
            double result = calculateFunction(x, y, z);

            // Отправка результата обратно клиенту
            String resultMessage = "Результат: " + result;
            byte[] sendBuffer = resultMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
            socket.send(sendPacket);

            System.out.println("Результат вычислений отправлен клиенту: " + resultMessage);
        }
    }

    private static double calculateFunction(double x, double y, double z) {
        return Math.abs(Math.pow(x, (y / x))) - Math.sqrt(y / x) + (y - x)
                * (Math.cos(y) - Math.exp(z / (y - x)) / (1 + Math.pow((y - x), 2)));
    }
}

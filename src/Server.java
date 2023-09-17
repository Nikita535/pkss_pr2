import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) {
        int port = 777;
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket client = serverSocket.accept();
             ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(client.getInputStream())
        ) {
            System.out.println("server is up");
            System.out.println("Client connected: " + client.getInetAddress().getHostAddress());

            while (true) {
                out.writeObject(menu());

                String word = (String) in.readObject();
                if (word.equals("exit")) {
                    client.close();
                    System.out.println("Client " + client.getInetAddress() + " disconnected.");
                    break;
                } else if (word.length() != 1 || !Character.isDigit(word.charAt(0))) {
                    out.writeObject("Неправильный формат входных данных! Попробуйте еще раз.\n" + "Для продолжения нажмите Enter");
                    in.readObject();

                } else {

                    int task = Integer.parseInt(word);

                    switch (task) {

                        case 1 -> {
                            try {
                                double[] x = new double[10];
                                double[] y = new double[10];
                                for (int i = 0; i < 10; i++) {
                                    String xStr = "Введите x координату " + (i + 1) + " вершины";
                                    String yStr = "Введите y координату " + (i + 1) + " вершины";
                                    if (i == 0) {
                                        String hint = "Введите десять пар значений x y, которые обозначают координаты вершин десятиугольника.\n";
                                        xStr = hint + xStr;
                                    }
                                    out.writeObject(xStr);
                                    x[i] = Double.parseDouble((String) in.readObject());
                                    out.writeObject(yStr);
                                    y[i] = Double.parseDouble((String) in.readObject());
                                }
                                double perimeter = calculatePerimeter(x, y);
                                out.writeObject("Ответ: " + String.valueOf(perimeter) + " Для продолжения нажмите Enter");
                                 in.readObject();
                            } catch (Exception exception) {
                                out.writeObject("Вы ввели недопустимые значения! Попробуйте еще раз.\n" + "Для продолжения нажмите Enter");
                                 in.readObject();
                            }
                        }

                        case 2 -> {
                            try {
                                out.writeObject("Введите натуральное число n");
                                int n = Integer.parseInt((String) in.readObject());
                                StringBuilder str = new StringBuilder();
                                for (int i = 1; i <= n; i++) {
                                    if (isDivisibleByDigits(i)) {
                                        str.append(i + " ");
                                    }
                                }
                                out.writeObject("Ответ: " + str + "\nДля продолжения нажмите Enter");
                                 in.readObject();

                            } catch (Exception exception) {
                                out.writeObject("Вы ввели недопустимые значения! Попробуйте еще раз.\n" + "Для продолжения нажмите Enter");
                                in.readObject();
                            }
                        }

                        case 3 -> {
                            try {
                                out.writeObject("Введите основание(введите 0 для выхода):");
                                while (true) {
                                    String str = (String) in.readObject();
                                    if (str.equals("0")) {
                                        break;
                                    } else {
                                        int base = Integer.parseInt(str);
                                        out.writeObject("Введите показатель степени: ");
                                        int exponent = Integer.parseInt((String) in.readObject());
                                        int result = power(base, exponent);
                                        out.writeObject("Ответ: " + result + "\nВведите основание(введите 0 для выхода):");
                                    }
                                }
                            } catch (Exception exception) {
                                out.writeObject("Вы ввели недопустимые значения! Попробуйте еще раз.\n" + "Для продолжения нажмите Enter");
                                 in.readObject();
                            }
                        }

                        case 4 -> {
                            try {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < 3; i++) {
                                    out.writeObject("Введите " + (i+1) + " пару натуральных чисел, разделив числа пробелом.");
                                    String str = (String) in.readObject();
                                    String[] strArr = str.split(" ");
                                    int firstNum = Integer.parseInt(strArr[0]);
                                    int secondNum = Integer.parseInt(strArr[1]);
                                    stringBuilder.append("Максимальное из пары " + firstNum + " " + secondNum + ": " + max(firstNum, secondNum) + "\n");
                                }
                                out.writeObject("Ответ: " + stringBuilder + "\nДля продолжения нажмите Enter");
                                var stub = in.readObject();
                            } catch (Exception exception) {
                                out.writeObject("Вы ввели недопустимые значения! Попробуйте еще раз.\n" + "Для продолжения нажмите Enter");
                                 in.readObject();
                            }
                        }

                        case 5 ->{
                            try{
                                out.writeObject("Введите строку чисел a b c");
                                Double res=  min((String) in.readObject());
                                out.writeObject("Ответ: " + res + "\nДля продолжения нажмите Enter");
                                in.readObject();
                            }catch (Exception e){
                                out.writeObject("Вы ввели недопустимые значения! Попробуйте еще раз.\n" + "Для продолжения нажмите Enter");
                                in.readObject();
                            }
                        }
                        default -> {
                            out.writeObject("Такого задания не существует, Попробуйте еще раз.\n" + "Для продолжения нажмите Enter");
                            in.readObject();
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static double min(String str) {
        double a = Double.parseDouble(str.split(" ")[0]);
        double b = Double.parseDouble(str.split(" ")[1]);
        double c = Double.parseDouble(str.split(" ")[2]);
      return Math.min(Math.min(a,b),c);
    }


    public static double calculatePerimeter(double[] x, double[] y) {
        double perimeter = 0;
        for (int i = 0; i < 9; i++) {
            double dx = x[i + 1] - x[i];
            double dy = y[i + 1] - y[i];
            perimeter += Math.sqrt(dx * dx + dy * dy);
        }
        double dx = x[0] - x[9];
        double dy = y[0] - y[9];
        perimeter += Math.sqrt(dx * dx + dy * dy);
        return perimeter;
    }

    public static boolean isDivisibleByDigits(int n) {
        String numberAsString = String.valueOf(n);
        for (int i = 0; i < numberAsString.length(); i++) {
            int digit = Integer.parseInt(Character.toString(numberAsString.charAt(i)));
            if (digit == 0 || n % digit != 0) {
                return false;
            }
        }
        return true;
    }

    public static int power(int base, int exponent) {
        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static String menu() {
        return """
                Для выбора задачи введите ее номер, для завершения программы введите "exit"
                1. Найти периметр десятиугольника
                2. Вывести числа, которые не превосхят N и делятся на свои цифры
                3. Возведение числа в степень
                4. Максимальные значения в 3 парах
                5. Минимальное число из 3х
                """;
    }
}
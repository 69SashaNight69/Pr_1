import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PostOffice {
    static final int WORK_DAY_DURATION = 5; // Тривалість робочого дня у секундах
    static final BlockingQueue<Package> packageQueue = new LinkedBlockingQueue<>();
    static boolean isWorkingHours = true;
    static final List<Package> arrivedAfterClose = new ArrayList<>(); // Список для посилок, які дійшли після закриття

    public static synchronized boolean isOpen() {
        return isWorkingHours;
    }

    public static synchronized void closePostOffice() {
        isWorkingHours = false;
        System.out.println(ConsoleColors.RED + "============= Пошта закрилася ================" + ConsoleColors.RESET);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread workerThread = new Thread(new Worker(), "Працівник");

        Runnable postOffice = () -> {
            int i = 1;
            workerThread.start();
            while (isWorkingHours) {
                new Thread(new Sender(i), "Відправник-" + i).start();
                i++;  // Номер нового відправника
                try {
                    Thread.sleep(1000);  // Новий відправник з'являється кожні 1 сек
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread postOfficeThread = new Thread(postOffice, "Пошта");

        postOfficeThread.start();  // Запускаємо потік пошти

        Thread.sleep(WORK_DAY_DURATION * 1000);
        closePostOffice();
        workerThread.join();  // Чекаємо, доки працівник завершить всі операції
    }
}


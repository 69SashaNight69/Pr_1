import java.util.concurrent.TimeUnit;

class Worker implements Runnable {

    @Override
    public void run() {
        while (PostOffice.isOpen() || !PostOffice.packageQueue.isEmpty()) {
            try {
                // Працівник бере посилку, якщо є, або чекає певний час (1 сек).
                Package packageToProcess = PostOffice.packageQueue.poll(1, TimeUnit.SECONDS);
                if (packageToProcess == null) {
                    if (!PostOffice.isOpen() && PostOffice.packageQueue.isEmpty()) {
                        break;
                    }
                } else {
                    if (PostOffice.isOpen()) {
                        System.out.printf(ConsoleColors.GREEN + "Працівник прийняв %s\n"+ ConsoleColors.RESET, packageToProcess);
                        Thread.sleep(2000);  // Упаковка триває 2 сек
                        System.out.printf(ConsoleColors.GREEN + "Працівник упакував %s\n"+ ConsoleColors.RESET, packageToProcess);
                        new Thread(new Receiver(packageToProcess)).start();
                    } else {
                        System.out.printf(ConsoleColors.RED + "Працівник не може упакувати %s, оскільки пошта закрита.\n" + ConsoleColors.RESET, packageToProcess);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Виводимо інформацію про посилки, які дійшли після закриття
        if (!PostOffice.arrivedAfterClose.isEmpty()) {
            for (Package pkg : PostOffice.arrivedAfterClose) {
                System.out.printf(ConsoleColors.YELLOW +"Посилка %s дійшла після закриття пошти. Її не можна забрати зараз.\n" + ConsoleColors.RESET, pkg);
            }
        }

        // Після обробки всіх посилок працівник йде додому
        System.out.println(ConsoleColors.RED + "============= Працівник пішов додому ================" + ConsoleColors.RESET);
    }
}

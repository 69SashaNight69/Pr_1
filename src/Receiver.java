class Receiver implements Runnable {
    private final Package pkg;

    public Receiver(Package pkg) {
        this.pkg = pkg;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);  // Симуляція доставки
            if (PostOffice.isOpen()) {
                System.out.printf(ConsoleColors.GREEN + "Посилка %s дійшла до отримувача.\n" + ConsoleColors.RESET, pkg);
                Thread.sleep(1000);  // Час на те, щоб отримувач забрав посилку
                System.out.printf(ConsoleColors.GREEN + "Отримувач забрав %s\n" + ConsoleColors.RESET, pkg);
            } else {
                PostOffice.arrivedAfterClose.add(pkg); // Додаємо посилку до списку тих, що прийшли після закриття
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

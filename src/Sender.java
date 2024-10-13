class Sender implements Runnable {
    private final int senderId;

    public Sender(int id) {
        this.senderId = id;
    }

    @Override
    public void run() {
        if (PostOffice.isOpen()) {
            String senderName = "Відправник-" + senderId;
            Package newPackage = new Package(senderName);
            try {
                PostOffice.packageQueue.put(newPackage);
                System.out.printf("%s прийшов на пошту відправити %s\n", senderName, newPackage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.printf(ConsoleColors.RED + "Відправник-%d прийшов, але пошта вже закрита і нові посилки не приймаються!\n" + ConsoleColors.RESET, senderId);
        }
    }
}

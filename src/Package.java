import java.util.concurrent.atomic.AtomicInteger;

class Package {
    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private final int packageId;
    private final String senderName;

    public Package(String senderName) {
        this.packageId = idCounter.incrementAndGet();
        this.senderName = senderName;
    }

    public int getPackageId() {
        return packageId;
    }

    public String getSenderName() {
        return senderName;
    }

    @Override
    public String toString() {
        return String.format("Посилка від %s з ID: %d", senderName, packageId);
    }
}

abstract class Server {
    public abstract boolean notify(String message);
}

class Notification {
    private String name;
    private Server server;
    private int maxRetries;
    private static Notification instance;

    private Notification(String name, Server server, int maxRetries) {
        this.name = name;
        this.server = server;
        this.maxRetries = maxRetries;
    }

    public static Notification getInstance(String name, Server server, int maxRetries) {
        if (instance == null) instance = new Notification(name, server, maxRetries);
        return instance;
    }

    public void notify(String message) {
        int retries = 0;
        boolean success;

        do {
            success = this.server.notify(message);
            retries++;
        } while (!success && retries < this.maxRetries);
    }

    public String getName() { return this.name; }
    public int getMaxRetries() { return this.maxRetries; }

    public void setName(String name) { this.name = name; }
    public void setMaxRetries(int maxRetries) { this.maxRetries = maxRetries; }
    public void setServer(Server server) { this.server = server; }
}

class EmailNotificationFactory {
    public static Notification create(int maxRetries) {
        return Notification.getInstance("Email Server", new Email(), maxRetries);
    }
}

class SMSNotificationFactory {
    public static Notification create(int maxRetries) {
        return Notification.getInstance("SMS Server", new SMS(), maxRetries);
    }
}

class PushNotificationFactory {
    public static Notification create(int maxRetries) {
        return Notification.getInstance("Push Notification Server", new PushNotification(), maxRetries);
    }
}

class Email implements Server {
    public boolean notify(String message) {
        // envia email
        return true;
    }
}

class SMS implements Server {
    public boolean notify(String message) {
        // envia sms
        return true;
    }
}

class PushNotification implements Server {
    public boolean notify(String message) {
        // envia notificação push
        return true;
    }
}

class Test {
    public static void main(String[] args) {
        Test.testEmail();
        Test.testSMS();
        Test.testPushNotification();
    }

    public static void testEmail main() {
        Notification email = EmailNotificationFactory.create(3);
        email.notify("enviando um email");
    }

    public static void testSMS main() {
        Notification sms = SMSNotificationFactory.create(3);
        sms.notify("enviando um sms");
    }

    public static void testPushNotification main() {
        Notification pushNotification = PushNotificationFactory.create(3);
        pushNotification.notify("enviando um push notification");
    }
}
interface IServer {
    public abstract boolean notify(String message);
}

public abstract class INotification {
    protected String name;
    protected IServer server;
    protected int maxRetries;
    protected int totalMaxRetries;
    protected int totalRetries;

    public INotification(String name, IServer server, int maxRetries, int totalMaxRetries) {
        this.name = name;
        this.server = server;
        this.maxRetries = maxRetries;
        this.totalMaxRetries = totalMaxRetries;
    }

    public abstract void send(String message);

    public String getName() { return this.name; }
    public int getMaxRetries() { return this.maxRetries; }
    public int getTotalRetries() { return this.totalRetries; }
}

class Notification extends INotification {
    private static Notification instance;

    private Notification(String name, IServer server, int maxRetries, int totalMaxRetries) {
        super(name, server, maxRetries, totalMaxRetries);
    }

    public static Notification getInstance(String name, IServer server, int maxRetries, int totalMaxRetries) {
        if (instance == null) instance = new Notification(name, server, maxRetries, totalMaxRetries);
        return instance;
    }

    public void send(String message) {
        int retries = 0;
        boolean success;

        do {
            success = this.server.notify(message);
            retries++;
            this.totalRetries++;
        } while (!success && retries < this.maxRetries);
    }
}


public class NotificationProxy extends INotification {
    private INotification base;

    public NotificationProxy(INotification base) {
        super(base.getName(), base.server, base.getMaxRetries());
        this.base = base;
    }

    @Override
    public void send(String message) {
        System.out.println("[LOG] Enviando notificação: " + message);
        System.out.println("[LOG] Usando servidor: " + base.getName());

        if (this.base.getTotalRetries() > 100) {
            System.out.println("Número máximo de tentativas alcançada");
            return;
        }

        base.send(message);

        System.out.println("[LOG] Notificação finalizada");
    }
}

class EmailNotificationFactory {
    public static INotification create(int maxRetries, int totalMaxRetries) {
        INotification notification = 
            Notification.getInstance("Email IServer", new Email(), maxRetries, totalMaxRetries);
        
        return new NotificationProxy(notification);
    }
}

class SMSNotificationFactory {
    public static INotification create(int maxRetries, int totalMaxRetries) {
        INotification notification = 
            Notification.getInstance("SMS IServer", new SMS(), maxRetries, totalMaxRetries);
        
        return new NotificationProxy(notification);
    }
}

class PushNotificationFactory {
    public static INotification create(int maxRetries, int totalMaxRetries) {
        INotification notification = 
            Notification.getInstance("Push Notification IServer", new PushNotification(), maxRetries, totalMaxRetries);
        
        return new NotificationProxy(notification);
    }
}

class WhatsappFactory {
    public static INotification create(int maxRetries, int totalMaxRetries) {
        INotification notification = 
            Notification.getInstance("Whatsapp IServer", new WhatsappAdaptor(new Whatsapp()), maxRetries, totalMaxRetries);
        
        return new NotificationProxy(notification);
    }
}

class Email implements IServer {
    public boolean notify(String message) {
        // envia email
        return true;
    }
}

class SMS implements IServer {
    public boolean notify(String message) {
        // envia sms
        return true;
    }
}

class PushNotification implements IServer {
    public boolean notify(String message) {
        // envia notificação push
        return true;
    }
}

class Whatsapp {
    public boolean sendMessage(String message) {
        // envia mensagem whatsapp
        return true;
    }
}

class WhatsappAdaptor implements IServer {
    private Whatsapp whatsapp;

    public WhatsappAdaptor(Whatsapp whatsapp) {
        this.whatsapp = whatsapp;
    }

    public boolean notify(String message) {
        return this.whatsapp.sendMessage(message);
    }
}

class Test {
    public static void main(String[] args) {
        Test.testEmail();
        Test.testSMS();
        Test.testPushNotification();
    }

    public static void testEmail() {
        INotification email = EmailNotificationFactory.create(3, 100);
        email.send("enviando um email");
    }

    public static void testSMS() {
        INotification sms = SMSNotificationFactory.create(3, 100);
        sms.send("enviando um sms");
    }

    public static void testPushNotification() {
        INotification pushNotification = PushNotificationFactory.create(3, 100);
        pushNotification.send("enviando um push notification");
    }

    public static void testWhatsapp() {
        INotification whatsapp = WhatsappFactory.create(3, 100);
        whatsapp.send("enviando uma mensagem do whatsapp");
    }
}
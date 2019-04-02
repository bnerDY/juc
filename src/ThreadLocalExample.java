public class ThreadLocalExample {

    public static java.lang.ThreadLocal<Session> session = new java.lang.ThreadLocal<Session>();

    public static class Session {
        private String id;
        private String user;
        private String status;

        public String getUser() {
            String res = "";
            return res;
        }

        public String getStatus() {
            String res = "";
            return res;
        }

        public void setStatus(String status) {
        }
    }

    public void createSession() {
        session.set(new Session());
    }

    public String getUser() {
        return session.get().getUser();
    }

    public String getStatus() {
        return session.get().getStatus();
    }

    public void setStatus(String status) {
        session.get().setStatus(status);
    }

    public static void main(String[] args) {
        new Thread(() -> {
            ThreadLocalExample handler = new ThreadLocalExample();
            handler.getStatus();
            handler.getUser();
            handler.setStatus("close");
            handler.getStatus();
        }).start();
    }
}
package mitrofanov;

import lombok.Getter;


public class Configuration {
    public static final String DB_URL = System.getenv("DB_URL");
    public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    public static final String DB_USER = System.getenv("DB_USER");
    public static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    public static final String BOT_USERNAME = System.getenv("BOT_USERNAME");
}

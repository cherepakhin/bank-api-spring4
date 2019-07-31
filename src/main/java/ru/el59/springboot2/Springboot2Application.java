package ru.el59.springboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.Logger;

@SpringBootApplication
public class Springboot2Application {
    private static Logger logger = Logger.getLogger(Springboot2Application.class.getName());
    public static void main(String[] args) {
        SpringApplication.run(Springboot2Application.class, args);
        String url = "http://127.0.0.1:8080/api/client/";
        String os = System.getProperty("os.name").toLowerCase(); // получаем имя операционной системы
        Runtime rt = Runtime.getRuntime();
        try {
            if (os.contains("win")) {
                // не поддерживаются ссылки формата "leodev.html#someTag"
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url); // если windows, открываем урлу через командную строку
            } else if (os.contains("mac")) {
                rt.exec("open " + url); // аналогично в MAC
            } else if (os.contains("nix") || os.contains("nux")) {
                // c nix системами все несколько проблемотичнее
                String[] browsers = {"yandex-browser","chrome","firefox", "mozilla", "konqueror", "netscape", "opera", "links", "lynx"};
                // Формируем строку с вызовом всем браузеров через логическое ИЛИ в shell консоли
                // "browser0 "URI" || browser1 "URI" ||..."
                StringBuilder cmd = new StringBuilder();
                for (int i = 0; i < browsers.length; i++)
                    cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url).append("\" ");
                rt.exec(new String[]{"sh", "-c", cmd.toString()});
            } else {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getLocalizedMessage());
        }
    }
}

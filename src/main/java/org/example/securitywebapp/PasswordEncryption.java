package org.example.securitywebapp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryption {
    public static void main(String[] args) {
        // Создаем экземпляр BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Указываем пароль, который нужно зашифровать
        String rawPassword = "$2a$10$Gc3Hey/88ArWfv4X8brLue6Q0kJg4N/hppdrdVgND/CwxRvtrpmM6";

        // Шифруем пароль
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Выводим зашифрованный пароль
        System.out.println("Зашифрованный пароль: " + encodedPassword);
    }
}

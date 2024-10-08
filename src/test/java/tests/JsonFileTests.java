package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Users;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class JsonFileTests {

    private final ClassLoader cl = JsonFileTests.class.getClassLoader();
    private static final ObjectMapper om = new ObjectMapper();

    @DisplayName("Тест берет json файл из ресурсов и проверяет данные внутри него")
    @Test
    public void jsonFileParsingTest() throws Exception {
        try (Reader reader = new InputStreamReader(
                Objects.requireNonNull(cl.getResourceAsStream("users.json"))
        )) {
            Users users = om.readValue(reader, Users.class);

            Assertions.assertThat(users.getUsers().get(0).getId()).isEqualTo(1);
            Assertions.assertThat(users.getUsers().get(0).getName()).isEqualTo("Roman");
            Assertions.assertThat(users.getUsers().get(0).getAge()).isEqualTo(16);

            Assertions.assertThat(users.getUsers().get(1).getId()).isEqualTo(2);
            Assertions.assertThat(users.getUsers().get(1).getName()).isEqualTo("Dima");
            Assertions.assertThat(users.getUsers().get(1).getAge()).isEqualTo(20);

            Assertions.assertThat(users.getUsers().get(2).getId()).isEqualTo(3);
            Assertions.assertThat(users.getUsers().get(2).getName()).isEqualTo("Lena");
            Assertions.assertThat(users.getUsers().get(2).getAge()).isEqualTo(25);
        }
    }
}
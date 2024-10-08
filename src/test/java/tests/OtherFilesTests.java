package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class OtherFilesTests {
    private final ClassLoader cl = JsonFileTests.class.getClassLoader();

    @DisplayName("Тест берет pdf файл и проверяет текст внутри")
    @Test
    void pdfFileParsingTest() throws Exception {
        PDF pdf = null;
        try (ZipInputStream zis = new ZipInputStream(
                Objects.requireNonNull(cl.getResourceAsStream("files.zip"))
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("pdf_example.pdf")) {
                    pdf = new PDF(zis);
                    break;
                }
            }

            Assertions.assertThat(pdf.text).contains("Пример pdf");
        }
    }

    @DisplayName("Тест берет xlsx файл и проверяет данные внутри")
    @Test
    void xlsxFileParsingTest() throws Exception {
        XLS xlsx = null;
        try (ZipInputStream zis = new ZipInputStream(
                Objects.requireNonNull(cl.getResourceAsStream("files.zip"))
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("xlsx_example.xlsx")) {
                    xlsx = new XLS(zis);
                    break;
                }
            }

            String actualValue = xlsx.excel.getSheetAt(0).getRow(5).getCell(5).getStringCellValue();
            Assertions.assertThat(actualValue).contains("Беговая 252");
        }
    }
    @DisplayName("Тест берет csv файл и проверяет данные внутри")
    @Test
    void csvFileParsingTest() throws Exception {
        CSVReader csvReader = null;
        try (ZipInputStream zis = new ZipInputStream(
                Objects.requireNonNull(cl.getResourceAsStream("files.zip"))
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("csv_example.csv")) {
                    csvReader = new CSVReader(new InputStreamReader(zis));
                    break;
                }
            }

            List<String[]> data = csvReader.readAll();

            Assertions.assertThat(data.get(0)).isEqualTo(new String[]{"#userName", "Ivan Ivanov", "#name"});
            Assertions.assertThat(data.get(1)).isEqualTo(new String[]{"#userEmail", "ivanov@ivan.ru", "#email"});
        }
    }


}
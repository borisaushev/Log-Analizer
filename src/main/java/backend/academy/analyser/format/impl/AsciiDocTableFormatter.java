package backend.academy.analyser.format.impl;

import backend.academy.analyser.format.ReportTable;
import backend.academy.analyser.format.TableFormatter;
import java.util.ArrayList;
import java.util.List;

public class AsciiDocTableFormatter implements TableFormatter {
    // Метод для вычисления максимальной ширины для каждого столбца
    private List<Integer> calculateColumnWidths(ReportTable reportTable) {
        List<Integer> widths = new ArrayList<>();

        // Начальные значения ширин — это длины заголовков столбцов
        for (String column : reportTable.columns()) {
            widths.add(column.length());
        }

        // Проходим по каждой записи и обновляем ширину для каждого столбца
        for (List<Object> entry : reportTable.entries()) {
            for (int i = 0; i < entry.size(); i++) {
                int valueLength = entry.get(i) != null ? entry.get(i).toString().length() : 0;
                widths.set(i, Math.max(widths.get(i), valueLength));
            }
        }

        return widths;
    }

    @Override
    public String formatTable(ReportTable reportTable) {
        List<Integer> columnWidths = calculateColumnWidths(reportTable);
        StringBuilder asciidoc = new StringBuilder();

        // Добавляем описание таблицы, если оно задано
        if (reportTable.description() != null && !reportTable.description().isEmpty()) {
            asciidoc.append(".").append(reportTable.description()).append("\n");
        }

        // Начинаем таблицу AsciiDoc
        asciidoc.append("|===\n");

        // Генерация заголовков столбцов
        for (int i = 0; i < reportTable.columns().size(); i++) {
            String column = reportTable.columns().get(i);
            asciidoc.append("| ").append(padRight(column, columnWidths.get(i))).append(" ");
        }
        asciidoc.append("|\n");

        // Генерация строк таблицы
        for (List<Object> entry : reportTable.entries()) {
            for (int i = 0; i < entry.size(); i++) {
                String value = entry.get(i) != null ? entry.get(i).toString() : "";
                asciidoc.append("| ").append(padRight(value, columnWidths.get(i))).append(" ");
            }
            asciidoc.append("|\n");
        }

        // Завершаем таблицу AsciiDoc
        asciidoc.append("|===\n");

        return asciidoc.toString();
    }

    // Вспомогательный метод для выравнивания текста до нужной ширины
    private String padRight(String text, int width) {
        return String.format("%-" + width + "s", text);
    }
}

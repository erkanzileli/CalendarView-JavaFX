package controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.CalendarPaneModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AgendaController implements Initializable {


    public GridPane calendarGridPane;
    public StackPane root;
    public Label lblYear;
    public Label lblMonth;

    private ArrayList<CalendarPaneModel> allCalendarDays = new ArrayList<CalendarPaneModel>(35);

    private YearMonth currentYearMonth;
    private HashMap<Integer, String> months;

    @FXML
    void minusMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        updateCalendar(currentYearMonth);
    }

    @FXML
    void minusYear() {
        currentYearMonth = currentYearMonth.minusYears(1);
        updateCalendar(currentYearMonth);
    }

    @FXML
    void plusMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateCalendar(currentYearMonth);
    }

    @FXML
    void plusYear() {
        currentYearMonth = currentYearMonth.plusYears(1);
        updateCalendar(currentYearMonth);
    }


    public void initialize(URL location, ResourceBundle resources) {
        months = new HashMap<Integer, String>();
        months.put(1, "Ocak");
        months.put(2, "Şubat");
        months.put(3, "Mart");
        months.put(4, "Nisan");
        months.put(5, "Mayıs");
        months.put(6, "Haziran");
        months.put(7, "Temmuz");
        months.put(8, "Ağustos");
        months.put(9, "Eylül");
        months.put(10, "Ekim");
        months.put(11, "Kasım");
        months.put(12, "Aralık");
        currentYearMonth = YearMonth.now();
        fillGridPane();
        updateCalendar(currentYearMonth);
    }

    private void fillGridPane() {
        //GridPane içine  35 adet Pane yerleştirme
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                CalendarPaneModel pane = new CalendarPaneModel();
                pane.setPrefSize(130, 120);
                calendarGridPane.add(pane, j, i);
                allCalendarDays.add(pane);
            }
        }
    }

    private void updateCalendar(YearMonth yearMonth) {
        LocalDate localDate = LocalDate.of(currentYearMonth.getYear(), currentYearMonth.getMonthValue(), 1);
        //Ayın 1'i pazartesi değilse bir önceki ayın son pazartesisine kadar git
        while (!localDate.getDayOfWeek().toString().equals("MONDAY")) {
            localDate = localDate.minusDays(1);
        }
        LocalDate today = LocalDate.now();
        //Takvimi günlerin numaraları ile doldurma
        for (CalendarPaneModel pane : allCalendarDays) {
            Text text = new Text(5, 90, String.valueOf(localDate.getDayOfMonth()));
            //bugünün yeşil yazılması
            if (localDate.equals(today)) {
                text.setFill(Color.GREEN);
                text.setFont(new Font(26));
            } else {
                text.setFont(new Font(20));
            }
            pane.getChildren().setAll(text);
            pane.setDay(localDate.getDayOfMonth());
            pane.setOnMouseClicked(e -> {
                Parent createDiaryRecord = null;
                try {
                    createDiaryRecord = FXMLLoader.load(getClass().getResource("/fxml/createAgendaRecord.fxml"));
                } catch (IOException ignored) {
                    System.out.println(ignored.getMessage());
                }
                JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
                jfxDialogLayout.setBody(createDiaryRecord);
                JFXDialog dialog = new JFXDialog(root, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
                dialog.show();
            });
            localDate = localDate.plusDays(1);
        }
        lblYear.setText(String.valueOf(yearMonth.getYear()));
        lblMonth.setText(months.get(yearMonth.getMonthValue()));
    }


}

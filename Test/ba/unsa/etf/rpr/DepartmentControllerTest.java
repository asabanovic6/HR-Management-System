
package ba.unsa.etf.rpr;


import ba.unsa.etf.rpr.controllers.ChangePasswordController;
import ba.unsa.etf.rpr.controllers.DepartmentController;
import ba.unsa.etf.rpr.controllers.EmployeeController;
import ba.unsa.etf.rpr.controllers.LogInController;
import ba.unsa.etf.rpr.model.Department;
import ba.unsa.etf.rpr.utilities.HrDAO;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(ApplicationExtension.class)
public class DepartmentControllerTest {



    Stage theStage;
    DepartmentController ctrl;
    HrDAO dao;

    @Start
    public void start (Stage primaryStage) throws Exception {
        dao = HrDAO.getInstance();
        dao.returnBaseOnDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/Department.fxml" ), bundle);
        Locale.setDefault(new Locale("bs", "BA"));
        DepartmentController ctrl = new DepartmentController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle(bundle.getString("Add_new_department"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
        theStage=primaryStage;
    }
    @BeforeEach
    public void resetDataBase() throws SQLException {
        dao.returnBaseOnDefault();
    }

    @AfterEach
    public void closeTheWindow(FxRobot robot) {
        if (robot.lookup("#btnCancel").tryQuery().isPresent())
            robot.clickOn("#btnCancel");
    }
    @Test
    public void testAddDepartment(FxRobot robot) {

        robot.clickOn("#BtnOk");


        TextField ime = robot.lookup("#fieldDepName").queryAs(TextField.class);
        Background bg = ime.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);

        assertTrue(theStage.isShowing());
    }
    @Test
    public void testAddDepartment2 (FxRobot robot) {

        robot.clickOn("#fieldDepName");
        robot.write("Odjel za menadzment");
        robot.clickOn("#choiceManager");
        robot.clickOn("Amina Sabanovic");
        robot.clickOn("#choiceLocation");
        robot.clickOn("Sarajevo");


        robot.clickOn("#BtnOk");

        assertFalse(theStage.isShowing());
    }
}

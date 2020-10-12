package ba.unsa.etf.rpr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HrDAOTest {
    private HrDAO dao = HrDAO.getInstance();

    @BeforeEach
    public void resetDataBase() throws SQLException {
        dao.returnBaseOnDefault();
    }

    @Test
    void regenerateFile() {
        // Testiramo da li će fajl ponovo biti kreiran nakon brisanja
        // Ovaj test može padati na Windowsu zbog lockinga, u tom slučaju pokrenite ovaj test
        // odvojeno od ostalih
       HrDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        this.dao = HrDAO.getInstance();
        Employee emp = dao.getEmployee(1);
        assertEquals("Amina Sabanovic", emp.getEmployeeName());
    }
   @Test
    void test1Datum () {
       Employee emp = dao.getEmployee(1);
       LocalDateTime date = LocalDateTime.of(2010, 10 , 05, 00, 00);
       assertEquals(date, emp.getHireDate());
   }
}

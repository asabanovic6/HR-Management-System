package ba.unsa.etf.rpr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class HrDAOTest {
    private HrDAO dao = HrDAO.getInstance();

    @BeforeEach
    public void resetDataBase() throws SQLException {
        dao.returnBaseOnDefault();
    }

    @Test
   public void regenerateFile() {
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
    public void test1Date () {
       Employee emp = dao.getEmployee(1);
       LocalDateTime date = LocalDateTime.of(2010, 10 , 05, 00, 00);
       assertEquals(date, emp.getHireDate());
   }

   @Test
   public void test1Manager () {
       Manager man = dao.getManager(1);
       assertEquals("Amina Sabanovic", man.getEmployeeName());
   }
    @Test
    public void test2Manager () {
        AtomicReference<Manager> man = null;
      assertThrows(NotManagerException.class,()-> man.set(dao.getManager(2)),"This employee is not manager!" );
    }



}

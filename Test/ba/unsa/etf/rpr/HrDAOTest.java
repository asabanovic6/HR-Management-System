package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.utilities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
        LocalDate date = LocalDate.of(2010, 10 , 05);
        assertEquals(date, emp.getHireDate());
    }

    @Test
    public void testGetManager () {
        Manager man = dao.getManager(1);
        assertEquals("Amina Sabanovic", man.getEmployeeName());
    }
    @Test
    public void testGetManagerExceptionEdition () {
        AtomicReference<Manager> man = null;
        assertThrows(NullPointerException.class,()-> man.set(dao.getManager(2)),"This employee is not manager!" );
    }

    @Test
    public void testGetEmployeesFromDepartment () throws NonExistentDepartment {

        ArrayList<Employee> employees = dao.getEmployeesFromDepartment(1);
        assertEquals("Adnan Tomic ", employees.get(0).getEmployeeName());
        assertEquals("Amina Sabanovic", employees.get(1).getEmployeeName());
        assertEquals("Hana Veladzic", employees.get(2).getEmployeeName());
    }

    @Test
    public void testGetEmployeesFromDepartmentExceptionEdition () {
        AtomicReference<ArrayList<Employee>> emp = null;
        assertThrows(NonExistentDepartment.class,()-> emp.set(dao.getEmployeesFromDepartment(99)),"This department doesn't exist!");
    }

    @Test
    public void testGetManagerFromDepartment () throws NonExistentDepartment {
        Manager manager = dao.getManagerFromDepartment(1);
        assertEquals("Amina Sabanovic", manager.getEmployeeName());
    }

    @Test
    public void testGetManagerFromDepartmentExceptionEdition () {
        AtomicReference<Manager> man = null;
        assertThrows(NonExistentDepartment.class,()-> man.set(dao.getManagerFromDepartment(99)),"This department doesn't exist!");
    }
    @Test
    public void testGetDepartmentsOnLocation () throws NonExistentLocation {
        ArrayList<Department> departments = dao.getDepartmentsOnLocation(1);
        assertEquals("Restoran", departments.get(0).getDepartmentName());

    }

    @Test
    public void testGetDepartmentsOnLocationExceptionEdition () {
        AtomicReference<ArrayList<Department>> dep = null;
        assertThrows(NonExistentLocation.class,()-> dep.set(dao.getDepartmentsOnLocation(99)),"This location doesn't exist in data base!");
    }

    @Test
    public void testGetWorkersFromManager () {
        ArrayList<Worker> workers = dao.getWorkersFromManager(1);
        assertEquals("Adnan Tomic ", workers.get(0).getEmployeeName());
        assertEquals("Hana Veladzic", workers.get(1).getEmployeeName());
    }

    @Test
    public void testGetDepartments () {
        ArrayList<Department> dep = dao.getDepartments();
        assertEquals("Restoran", dep.get(0).getDepartmentName());
        assertEquals("Kafić", dep.get(1).getDepartmentName());
    }

    @Test
    public void testGetJobs () {
        ArrayList<String> jobs= dao.getJobs();
        assertEquals("Menadžer", jobs.get(0));
        assertEquals("Kuhar", jobs.get(1));
        assertEquals("Šanker", jobs.get(2));
        assertEquals("Čistać", jobs.get(3));
    }
    @Test
    public void testDeleteEmployee () throws NonExistentDepartment {
        dao.deleteWorker("Adnan Tomic ");
        ArrayList<Employee> employees = dao.getEmployeesFromDepartment(1);
        assertEquals("Amina Sabanovic", employees.get(0).getEmployeeName());
        assertEquals("Hana Veladzic", employees.get(1).getEmployeeName());
    }

    @Test
    void testAddJob() {
        Job job = new Job();
        job.setJobTitle("Ekonomista");
        job.setMinSalary(1300);
        job.setMaxSalary(1500);
        dao.addJob(job);

        ArrayList<String> jobs = dao.getJobs();
        assertEquals("Ekonomista", jobs.get(4));
    }
    @Test
    void testAddDepartment() {
        Department dep = new Department();
        dep.setDepartmentName("Lounge");
        dep.setManagerId(1);
        dep.setLocationId(1);
        dao.addDepartment(dep);

        ArrayList<Department> deps = dao.getDepartments();
        assertEquals("Lounge", deps.get(2).getDepartmentName());
    }

    @Test
    void testAddLocation() {
        Location loc = new Location();
        loc.setCity("Tuzla");
        dao.addLocation(loc);

        ArrayList<Location> locs = dao.getLocations();
        assertEquals("Tuzla", locs.get(2).getCity());
    }

    @Test
    void testAddWorker() {
        Worker worker = new Worker();
        worker.setEmployeeName("Senada Šabanović");
        Manager manager = dao.getManager(4);
        worker.setManager(manager);
        worker.setEmail("senadaSabanovic@gmail.com");
        worker.setCmp(0.1);
        worker.setSalary(1000);

        worker.setJob(dao.getJob(2));
        worker.setHireDate("2020-10-05");
        worker.setExpireDate("2030-10-05");
        worker.setDepartment(dao.getDepartment(2));

        dao.addWorker(worker);
        ArrayList<Worker> workers = dao.getWorkersFromManager(4);
        assertEquals("Senada Šabanović", workers.get(3).getEmployeeName());
        LocalDate date = LocalDate.of(2020, 10 , 05);
        assertEquals(date,workers.get(3).getHireDate());
    }
    @Test
    void testAddManager() throws NonExistentDepartment {
        Manager man = new Manager();
        man.setEmployeeName("Nijaz Šabanović");
        man.setEmail("nSabanovic@gmail.com");
        man.setCmp(0.1);
        man.setSalary(1000);
        Job job = dao.getJob(1);
        man.setJob(job);
        man.setHireDate("2020-10-05");
        man.setExpireDate("2030-10-05");
        Department department = dao.getDepartment(3);
        man.setDepartment(department);
        dao.addManager(man);
        Department dep = new Department();
        dep.setDepartmentName("Lounge");
        dep.setManagerId(man.getEmployeeId());
        dep.setLocationId(1);
        dao.addDepartment(dep);
        Manager manager = dao.getManagerFromDepartment(3);
        assertEquals("Nijaz Šabanović", manager.getEmployeeName());
    }



}

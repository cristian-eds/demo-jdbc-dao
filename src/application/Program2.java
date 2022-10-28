package application;

import java.util.List;

import model.dao.DepartmentDao;
import model.dao.FactoryDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = FactoryDao.createDepartmentDao();
		
		//System.out.println("TEST: Insert Department");
		Department dep = new Department(null, "RH");
		//departmentDao.insert(dep);
		//System.out.println("Inserted!");
		
		System.out.println("\n TEST: Update Department");
		dep = departmentDao.findById(3);
		dep.setName("Test");
		departmentDao.update(dep);
		System.out.println("Updated!");
		
		System.out.println("\n TEST: Select all Departments");
		List<Department> list = departmentDao.findAll();
		for (Department depart : list) {
			System.out.println(depart);
		}
		

	}

}

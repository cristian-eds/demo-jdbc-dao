package application;

import model.dao.DepartmentDao;
import model.dao.FactoryDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao departmetnDao = FactoryDao.createDepartmentDao();
		
		System.out.println("TEST: Insert Department");
		Department dep = new Department(null, "RH");
		departmetnDao.insert(dep);
		System.out.println("Inserted!");
		

	}

}

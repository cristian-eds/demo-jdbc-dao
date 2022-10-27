package application;

import java.util.Date;
import java.util.List;

import model.dao.FactoryDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao sellerDao = FactoryDao.createSellerDao();

		System.out.println("TEST: seller by id");
		Seller seller = sellerDao.findById(2);
		System.out.println(seller);

		System.out.println("\n TEST: seller by department");

		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);

		for (Seller sell : list) {
			System.out.println(sell);
		}

		System.out.println("\n TEST: seller find all");

		
		list = sellerDao.findAll();

		for (Seller sell : list) {
			System.out.println(sell);
		}
		
		System.out.println("TEST: Seller insert");
		Seller newSeller = new Seller(null,"Cristian","cristian@email.com",new Date(), 2000.0, department);
		
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New Seller id: "+newSeller.getId());

	}

}

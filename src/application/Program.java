package application;

import model.dao.FactoryDao;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = FactoryDao.createSellerDao();
		
		
		System.out.println("TEST: seller by id");
		Seller seller = sellerDao.findById(2);
		System.out.println(seller);
		

	}

}

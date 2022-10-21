package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department depart = new Department(1,"Eletronics");
		
		Seller seller = new Seller(1, "Fulano","fulano@email.com",new Date(),2000.0,depart);
		System.out.println(depart);
		System.out.println(seller);

	}

}

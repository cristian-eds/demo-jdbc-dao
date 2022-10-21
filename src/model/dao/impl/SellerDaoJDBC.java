package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement(
					"SELECT seller.* , department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ? ");

			statement.setInt(1, id);
			result = statement.executeQuery();

			if (result.next()) {
				Department dep = new Department();
				dep.setId(result.getInt("DepartmentId"));
				dep.setName(result.getString("DepName"));

				Seller seller = new Seller();
				seller.setId(result.getInt("id"));
				seller.setName(result.getString("Name"));
				seller.setEmail(result.getString("Email"));
				seller.setSalary(result.getDouble("BaseSalary"));
				seller.setBirthDate(result.getDate("BirthDate"));
				seller.setDepartment(dep);

				return seller;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(result);
			DB.closeStatement(statement);
		}
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}

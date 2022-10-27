package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("" + "INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) " + "VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getSalary());
			st.setInt(5, seller.getDepartment().getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);

				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					" UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "Where Id = ?"
					);
			
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getSalary());
			st.setInt(5, seller.getDepartment().getId());
			st.setInt(6, seller.getId());
			
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}

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
				Department dep = instantiateDepartment(result);

				Seller seller = instantiateSeller(result, dep);

				return seller;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(result);
			DB.closeStatement(statement);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("Select seller.* , department.Name as DepName FROM seller INNER JOIN "
					+ "department ON seller.DepartmentId = department.Id " + "ORDER BY Name");

			result = statement.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (result.next()) {
				Department dep = map.get(result.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(result);
					map.put(result.getInt("DepartmentId"), dep);
				}

				Seller seller = instantiateSeller(result, dep);

				list.add(seller);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(result);
			DB.closeStatement(statement);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department depart) {
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("Select seller.* , department.Name as DepName FROM seller INNER JOIN "
					+ "department ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? ORDER BY Name");

			statement.setInt(1, depart.getId());
			result = statement.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (result.next()) {
				Department dep = map.get(result.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(result);
					map.put(result.getInt("DepartmentId"), dep);
				}

				Seller seller = instantiateSeller(result, dep);

				list.add(seller);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(result);
			DB.closeStatement(statement);
		}
	}

	private Department instantiateDepartment(ResultSet result) throws SQLException {
		Department dep = new Department();
		dep.setId(result.getInt("DepartmentId"));
		dep.setName(result.getString("DepName"));
		return dep;
	}

	private Seller instantiateSeller(ResultSet result, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(result.getInt("id"));
		seller.setName(result.getString("Name"));
		seller.setEmail(result.getString("Email"));
		seller.setSalary(result.getDouble("BaseSalary"));
		seller.setBirthDate(result.getDate("BirthDate"));
		seller.setDepartment(dep);
		return seller;
	}

}

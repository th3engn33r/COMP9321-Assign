package dao;

import common.DBConnectionFactory;
import common.ServiceLocatorException;

public class GenericDAO {
	/**
	 * The service locator to retrieve database connections from
	 */
	protected DBConnectionFactory services;
	
	public GenericDAO(){
		try {
			services = new DBConnectionFactory();
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		}
	}
	
	public GenericDAO(DBConnectionFactory services) {
		this.services = services;
	}
}

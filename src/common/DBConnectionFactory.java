package common;

import java.sql.*;

import javax.naming.NamingException;
import javax.sql.*;

public class DBConnectionFactory extends AbstractJndiLocator{

	private DataSource ds;
	
	public DBConnectionFactory() throws ServiceLocatorException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DBConnectionFactory(DataSource ds) throws ServiceLocatorException {
		this.ds = ds;
	}
	
	public Connection createConnection() throws ServiceLocatorException {
		try {
			return getDataSource().getConnection();
		} catch (SQLException e) {
			throw new ServiceLocatorException("Unable to create connection: " + e.getMessage(), e);
		}
	}

	/**
	 * Finds a data source by looking up the initial context
	 * @return
	 * @throws ServiceLocatorException
	 */
	public DataSource getDataSource() throws ServiceLocatorException {
		if (ds == null) {
			try {
				ds = (DataSource) lookup("jdbc:mysql");
			} catch (NamingException e) {
				throw new ServiceLocatorException("Unable to locate data source; " + e.getMessage(), e);
			}
		}
		return ds;
	}

}

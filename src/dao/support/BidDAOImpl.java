package dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import beans.AddressBean;
import beans.AuctionItemBean;
import beans.BidBean;
import common.DBConnectionFactory;
import common.ServiceLocatorException;
import dao.BidDAO;
import dao.DataAccessException;
import dao.GenericDAO;

public class BidDAOImpl extends GenericDAO implements BidDAO{

	public BidDAOImpl() {
		super();
	}

	public BidDAOImpl(DBConnectionFactory services) {
		super(services);
	}

	
	public BidBean getBidItemById(String item_id) {
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM TBL_BID WHERE item_id = ?");
			stmt.setString(1, item_id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				BidBean biditem = createBidItemBean(rs);
				stmt.close();
				return biditem;
			}
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; "
					+ e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public BidBean getWinBidItemById(String item_id) {
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM TBL_BID WHERE item_id = ? AND status = ?");
			stmt.setString(1, item_id);
			stmt.setInt(2, 2);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				BidBean biditem = createBidItemBean(rs);
				stmt.close();
				return biditem;
			}
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; "
					+ e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public BidBean getLoseBidItemById(String item_id) {
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM TBL_BID WHERE item_id = ? AND status = ?");
			stmt.setString(1, item_id);
			stmt.setInt(2, 0);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				BidBean biditem = createBidItemBean(rs);
				stmt.close();
				return biditem;
			}
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; "
					+ e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}

	
	public BidBean addBidItem(BidBean item) {
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("insert into tbl_bid (item_id, bidder_id, price, bid_date, status, item_name) values (?, ?, ?, ?, ?, ?)");
			stmt.setString(1, item.getItemId());
			stmt.setInt(2, item.getBidderId());
			stmt.setFloat(3, item.getPrice());
			//stmt.setDate(4, item.getDate());
			java.sql.Date sqlDate = new java.sql.Date(item.getDate().getTime());
			stmt.setDate(4, sqlDate);
			stmt.setInt(5, item.getStatus());
			stmt.setString(6, item.getName());
			
			int n = stmt.executeUpdate();
			if (n != 1)
				throw new DataAccessException(
						"Did not insert one row into database");
			} catch (ServiceLocatorException e) {
				throw new DataAccessException("Unable to retrieve connection; "
						+ e.getMessage(), e);
			} catch (SQLException e) {
				throw new DataAccessException("Unable to execute query; "
						+ e.getMessage(), e);
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
		}
		return item;
	}
	
	
	public BidBean updateBid(String itemid, int bidderid, Float price, Date date){
		BidBean updateditem = new BidBean();
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement update = con.prepareStatement("UPDATE tbl_bid SET bidder_id = ?, price = ?, bid_date = ? WHERE item_id = ?");

			update.setInt(1, bidderid);
			update.setFloat(2, price);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			update.setDate(3, sqlDate);
			update.setString(4, itemid);
			
			int n = update.executeUpdate();
			} catch (ServiceLocatorException e) {
				throw new DataAccessException("Unable to retrieve connection; "
						+ e.getMessage(), e);
			} catch (SQLException e) {
				throw new DataAccessException("Unable to execute query; "
						+ e.getMessage(), e);
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
		}
		return updateditem;
	}
	
	
	public BidBean updateBidStatus(String itemid, int newstatus, int oldstatus){
		BidBean updateditem = new BidBean();
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement update = con.prepareStatement("UPDATE tbl_bid SET status = ? WHERE item_id = ? AND status = ?");

			update.setInt(1, newstatus);
			update.setString(2, itemid);
			update.setInt(3, oldstatus);
			
			int n = update.executeUpdate();
			} catch (ServiceLocatorException e) {
				throw new DataAccessException("Unable to retrieve connection; "
						+ e.getMessage(), e);
			} catch (SQLException e) {
				throw new DataAccessException("Unable to execute query; "
						+ e.getMessage(), e);
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
		}
		return updateditem;
	}
	
	
	public BidBean updateBidStatusFrom2(String itemid, int newstatus){
		BidBean updateditem = new BidBean();
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement update = con.prepareStatement("UPDATE tbl_bid SET status = ? WHERE item_id = ? AND status = ?");

			update.setInt(1, newstatus);
			update.setString(2, itemid);
			update.setInt(3, 2);
			
			int n = update.executeUpdate();
			} catch (ServiceLocatorException e) {
				throw new DataAccessException("Unable to retrieve connection; "
						+ e.getMessage(), e);
			} catch (SQLException e) {
				throw new DataAccessException("Unable to execute query; "
						+ e.getMessage(), e);
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
		}
		return updateditem;
	}

	
	public BidBean deleteBidItemById(String item_id) {
		Connection con = null;
		BidBean deletedItem = getBidItemById(item_id);
		try {
			con = services.createConnection();
			PreparedStatement stmt = con
					.prepareStatement("delete from tbl_bid where item_id = ?");
			stmt.setString(1, item_id);
			int n = stmt.executeUpdate();
			if (n != 1)
				throw new DataAccessException(
						"Did not delete one row from database");
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; "
					+ e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();

				}
			}
		}
		return deletedItem;
	}

	
	public ArrayList<BidBean> getAllBidItems() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		ArrayList<BidBean> list = new ArrayList<BidBean>() ;
		try {
			
			con = services.createConnection();
			stmt = con.prepareStatement("SELECT * FROM TBL_BID WHERE");

			rs = stmt.executeQuery();
			while (rs.next())
				list.add(createBidItemBean(rs));
			
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; "
					+ e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
		} finally {
			
			if (con != null) {
				try {
					stmt.close();
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}
	
	
	public ArrayList<BidBean> getAllWinBidItems() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		ArrayList<BidBean> list = new ArrayList<BidBean>() ;
		try {
			
			con = services.createConnection();
			stmt = con.prepareStatement("SELECT * FROM TBL_BID WHERE status = ?");
			stmt.setInt(1, 2);
			rs = stmt.executeQuery();
			while (rs.next())
				list.add(createBidItemBean(rs));
			
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; "
					+ e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
		} finally {
			
			if (con != null) {
				try {
					stmt.close();
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}
	
	private BidBean createBidItemBean(ResultSet rs) throws SQLException {
		BidBean item = new BidBean();
		item.setId(rs.getInt("id"));
		item.setItemId(rs.getString("item_id"));
		item.setBidderId(rs.getInt("bidder_id"));
		item.setPrice(rs.getFloat("price"));
		item.setDate(rs.getDate("bid_date"));
		item.setName(rs.getString("item_name"));
		return item;
	}
	
	public BidBean deleteAllBidItems(){
		Connection con = null;
		try {
			con = services.createConnection();
			Statement stmt = con.createStatement();
			String sql = "DELETE FROM TBL_BID;";
			//stmt.setString(1, item_id);
			stmt.executeUpdate(sql);
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; "
					+ e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
		} finally {
			
			if (con != null) {
				try {
					//stmt.close();
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public ArrayList<BidBean> getBidItemsByUser(int userid){
		ArrayList<BidBean> list = new ArrayList();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			
			con = services.createConnection();
			stmt = con.prepareStatement("SELECT * FROM TBL_BID WHERE bidder_id = ?");
			stmt.setInt(1, userid);
			rs = stmt.executeQuery();
			
			while (rs.next())
				//System.out.println("yes");
				list.add(createBidItemBean(rs));
			
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; "
					+ e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
		} finally {
			
			if (con != null) {
				try {
					stmt.close();
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}
	
	
	public ArrayList<BidBean> getBidItemsByUserAndStatus(int userid, int status){
		ArrayList<BidBean> list = new ArrayList();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			
			con = services.createConnection();
			stmt = con.prepareStatement("SELECT * FROM TBL_BID WHERE bidder_id = ? AND status = ?");
			stmt.setInt(1, userid);
			stmt.setInt(2, status);
			rs = stmt.executeQuery();
			
			while (rs.next())
				//System.out.println("yes");
				list.add(createBidItemBean(rs));
			
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; "
					+ e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
		} finally {
			
			if (con != null) {
				try {
					stmt.close();
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}
	
}

package dao.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import common.DBConnectionFactory;
import common.ServiceLocatorException;
import beans.AddressBean;
import beans.AuctionItemBean;
import beans.BidBean;
import dao.AuctionItemDAO;
import dao.DataAccessException;
import dao.GenericDAO;

public class AuctionItemDAOImpl extends GenericDAO implements AuctionItemDAO {

	public AuctionItemDAOImpl() {
		super();
	}

	public AuctionItemDAOImpl(DBConnectionFactory services) {
		super(services);
	}

	
	public AuctionItemBean getAuctionItemById(String id) {
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM TBL_ITEMS WHERE id = ? AND reservePrice <> ?");
			stmt.setString(1, id);
			stmt.setFloat(2, 0);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				AuctionItemBean contact = createAuctionItemBean(rs);
				stmt.close();
				return contact;
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
	
	
	public AuctionItemBean getAuctionItemByIdALL(String id) {
		Connection con = null;
		System.out.println("seacrh item");
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM TBL_ITEMS WHERE id = ?");
			stmt.setString(1, id);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				System.out.println("Found Item");
				AuctionItemBean contact = createAuctionItemBean(rs);
				stmt.close();
				return contact;
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
	
	
	
	public ArrayList<AuctionItemBean> getAuctionItemBySearchKey(String searchKey) throws DataAccessException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		ArrayList<AuctionItemBean> list = new ArrayList<AuctionItemBean>() ;
		try {
			
			con = services.createConnection();
			stmt = con.prepareStatement("SELECT * FROM TBL_ITEMS WHERE reservePrice <> ? AND (UPPER(CATEGORY) LIKE UPPER(?) OR UPPER(ITEM_NAME) LIKE UPPER(?) OR UPPER(DESCRIPTION) LIKE UPPER(?))");
			stmt.setString(2, "%"+searchKey+"%");
			stmt.setString(3, "%"+searchKey+"%");
			stmt.setString(4, "%"+searchKey+"%");
			stmt.setFloat(1, 0);
			rs = stmt.executeQuery();
			while (rs.next()){
				AuctionItemBean i = createAuctionItemBean(rs); 
				list.add(i);
				//System.out.println("add item : " + i.getItemName() +" price : "+i.getReservePrice());
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
					stmt.close();
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}
	
	
	public ArrayList<AuctionItemBean> getAuctionItemBySearchKeyAdv(String searchKey, String category, float minPrice, float maxPrice) throws DataAccessException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		ArrayList<AuctionItemBean> list = new ArrayList<AuctionItemBean>() ;
		try {
			
			con = services.createConnection();
			stmt = con.prepareStatement("SELECT * FROM TBL_ITEMS WHERE reservePrice <> ? AND (UPPER(CATEGORY) LIKE UPPER(?) OR UPPER(ITEM_NAME) LIKE UPPER(?) OR UPPER(DESCRIPTION) LIKE UPPER(?)) AND category = ? AND bidPrice >= ? AND bidPrice <= ?");
			stmt.setString(2, "%"+searchKey+"%");
			stmt.setString(3, "%"+searchKey+"%");
			stmt.setString(4, "%"+searchKey+"%");
			stmt.setFloat(1, 0);
			stmt.setString(5, category);
			stmt.setFloat(6, minPrice);
			stmt.setFloat(7, maxPrice);
			rs = stmt.executeQuery();
			while (rs.next()){
				AuctionItemBean i = createAuctionItemBean(rs); 
				list.add(i);
				//System.out.println("add item : " + i.getItemName() +" price : "+i.getReservePrice());
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
					stmt.close();
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}
	
	
	public AuctionItemBean updatePriceToZero(AuctionItemBean item){
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement update = con.prepareStatement("UPDATE tbl_items SET reservePrice = ? WHERE id = ?");

			update.setFloat(1, 0);
			update.setString(2, item.getId());
			
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
		return item;
	}
	
	
	public AuctionItemBean updateCurrentPrice(AuctionItemBean item, Float price){
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement update = con.prepareStatement("UPDATE tbl_items SET bidPrice = ? WHERE id = ?");

			update.setFloat(1, price);
			update.setString(2, item.getId());
			
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
		return item;
	}
	
	
	public ArrayList<AuctionItemBean> getAllAuctionItemsByOwner(int uid){
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		ArrayList<AuctionItemBean> list = new ArrayList<AuctionItemBean>() ;
		try {
			
			con = services.createConnection();
			stmt = con.prepareStatement("SELECT * FROM TBL_ITEMS WHERE owner_id = ? AND reservePrice <> ?");
			stmt.setInt(1, uid);
			stmt.setFloat(2, 0);
			rs = stmt.executeQuery();
			while (rs.next())
				list.add(createAuctionItemBean(rs));
			
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
	
	public java.sql.Date getCurrentDatetime() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}
	
	
	public ArrayList<AuctionItemBean> getClosedAuctionItemsByOwner(int uid){
		Connection con = null;
		System.out.println("enter get closed");
		ResultSet rs = null;
		PreparedStatement stmt = null;
		ArrayList<AuctionItemBean> list = new ArrayList<AuctionItemBean>() ;
		try {
			
			con = services.createConnection();
			stmt = con.prepareStatement("SELECT * FROM TBL_ITEMS WHERE owner_id = ? AND endTime > ?");
			stmt.setInt(1, uid);
			java.sql.Date d = getCurrentDatetime();
			System.out.println("now : "+d);
			stmt.setDate(2, d);
			rs = stmt.executeQuery();
			while (rs.next())
				list.add(createAuctionItemBean(rs));
			
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
	
	public ArrayList<AuctionItemBean> getAllAuctionItems(){
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		ArrayList<AuctionItemBean> list = new ArrayList<AuctionItemBean>() ;
		try {
			
			con = services.createConnection();
			stmt = con.prepareStatement("SELECT * FROM TBL_ITEMS WHERE reservePrice <> ?");
			stmt.setFloat(1, 0);
			rs = stmt.executeQuery();
			while (rs.next())
				list.add(createAuctionItemBean(rs));
			
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
	
	
	public AuctionItemBean deleteAuctionItemById(String id) {
		Connection con = null;
		AuctionItemBean deletedItem = getAuctionItemById(id);
		try {
			con = services.createConnection();
			PreparedStatement stmt = con
					.prepareStatement("delete from tbl_items where id = ?");
			stmt.setString(1, id);
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

	public AuctionItemBean addAuctionItem(AuctionItemBean item) {
		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con
					.prepareStatement("insert into tbl_items (id, owner_id, item_name, category, description, picture, streetAddress, city, state, country, postalCode, reservePrice, biddingStartPrice, biddingIncrements, bidPrice, endTime, notes, status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, item.getId());
			stmt.setInt(2, item.getOwnerId());
			stmt.setString(3, item.getItemName());
			stmt.setString(4, item.getCategory());
			stmt.setString(5, item.getDescription());
			stmt.setString(6, item.getPicture());
			stmt.setString(7, item.getAddress().getStreetAddress());
			stmt.setString(8, item.getAddress().getCity());
			stmt.setString(9, item.getAddress().getState());
			stmt.setString(10, item.getAddress().getCountry());
			stmt.setString(11, item.getAddress().getPostalCode());
			stmt.setFloat(12, item.getReservePrice());
			stmt.setFloat(13, item.getBiddingStartPrice());
			stmt.setFloat(14, item.getBiddingIncrements());
			stmt.setFloat(15, item.getCurrentBid());
//			java.sql.Date sqlDate = new java.sql.Date(item.getEndTime()
//					.getTime());
//			stmt.setDate(16, sqlDate);
			java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(item.getEndTime().getTime());
			System.out.println("sqlTimestamp:" + sqlTimestamp);
			stmt.setTimestamp(16, sqlTimestamp);
			
			stmt.setString(17, item.getNotes());
			stmt.setInt(18, item.getStatus());

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
	
	public boolean haltAuctionItemById(String id) {
		Connection con = null;
		boolean success = false;
		try {
			con = services.createConnection();
			PreparedStatement update = con.prepareStatement("UPDATE tbl_items SET status = ? WHERE id = ?");

			update.setFloat(1, 0);
			update.setString(2, id);
			
			update.executeUpdate();
			success = true;
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
		return success;
	}

	public boolean unhaltAuctionItemById(String id) {
		Connection con = null;
		boolean success = false;
		try {
			con = services.createConnection();
			PreparedStatement update = con.prepareStatement("UPDATE tbl_items SET status = ? WHERE id = ?");

			update.setFloat(1, 1);
			update.setString(2, id);
			
			update.executeUpdate();
			success = true;
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
		return success;
	}
	
	private AuctionItemBean createAuctionItemBean(ResultSet rs) throws SQLException {
		AuctionItemBean item = new AuctionItemBean();
		item.setId(rs.getString("id"));
		item.setOwnerId(rs.getInt("owner_id"));
		item.setItemName(rs.getString("item_name"));
		item.setCategory(rs.getString("category"));
		item.setDescription(rs.getString("description"));
		item.setPicture(rs.getString("picture"));
		AddressBean addr = new AddressBean();
		addr.setStreetAddress(rs.getString("streetAddress"));
		addr.setCity(rs.getString("city"));
		addr.setState(rs.getString("state"));
		addr.setCountry(rs.getString("country"));
		addr.setPostalCode(rs.getString("postalCode"));
		item.setAddress(addr);
		item.setReservePrice(rs.getFloat("reservePrice"));
		item.setBiddingStartPrice(rs.getFloat("biddingStartPrice"));
		item.setBiddingIncrements(rs.getFloat("biddingIncrements"));
		item.setCurrentBid(rs.getFloat("bidPrice"));
		
		//Date handling (timestamp > long > date > formated date string > date)
		java.sql.Timestamp sqlTimestamp = rs.getTimestamp("endTime");
		long timeMillis = sqlTimestamp.getTime();
		Date date = new Date(timeMillis);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd-kk:mm");
		try {
			item.setEndTime(df.parse(df.format(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		item.setNotes(rs.getString("notes"));
		item.setStatus(rs.getInt("status"));
		return item;
	}

}
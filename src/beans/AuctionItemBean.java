package beans;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import business.support.AuctionServiceImpl;
import business.support.BidServiceImpl;
import business.support.UserServiceImpl;

public class AuctionItemBean {
	
	public static boolean check = false;
	
	private UserBean owner;
	
	public UserBean getOwner() {
		return owner;
	}
	
	public void setOwner(UserBean owner) {
		this.owner = owner;
	}
	public AuctionItemBean() {
		super();
		//System.out.println("create item bean");
	}
	private String id;
	private int ownerId;
	private String itemName;
	private String category;
	private String picture;
	private String description;
	private AddressBean Address;
	private float reservePrice;
	private float biddingStartPrice;
	private float biddingIncrements;
	private float currentBid;
	private Date endTime;
	private String notes;
	private int status;
	private boolean isOpen;
	
	private static BidServiceImpl bidService;
	private static AuctionServiceImpl auctionService;
	private static UserServiceImpl userService;
	
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int i) {
		this.ownerId = i;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String pictureURL) {
		this.picture = pictureURL;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getReservePrice() {
		return reservePrice;
	}
	public void setReservePrice(float reservePrice) {
		this.reservePrice = reservePrice;
	}
	public float getBiddingStartPrice() {
		return biddingStartPrice;
	}
	public void setBiddingStartPrice(float biddingStartPrice) {
		this.biddingStartPrice = biddingStartPrice;
	}
	public float getBiddingIncrements() {
		return biddingIncrements;
	}
	public void setBiddingIncrements(float biddingIncrements) {
		this.biddingIncrements = biddingIncrements;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public AddressBean getAddress() {
		return Address;
	}
	public void setAddress(AddressBean address) {
		Address = address;
	}
	public float getCurrentBid() {
		return currentBid;
	}
	public void setCurrentBid(float currentBid) {
		this.currentBid = currentBid;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public synchronized void setClosing(int closingtime){

		System.out.println("owner : "+this.ownerId);
		this.isOpen = true;
		final String id = this.getId();
		(new Timer(true)).schedule(new TimerTask() {
			@Override
			public void run() {
					AuctionItemBean.this.close(id);
				
			}
		}, closingtime);
		System.out.println("Timer set!");
	}
	
	private synchronized void close(String id) {
		System.out.println("item id : "+id);
		check = true; 
		//BidBean biditem = bidService.getBidItemById(this.getId());
		//AuctionItemBean itemm = auctionService.getItemByIdALL(id);
		//UserBean owner = userService.getUserByID(this.getOwnerId());
		this.isOpen = false;
		System.out.println("Auction for item "+this.itemName+" is closed now");
		//userService = new UserServiceImpl();
		//UserBean bidder = userService.getUserByID(biditem.getBidderId());
		this.owner.notify(this, 1);
		//bidder.notify(this, 2);
	}
}

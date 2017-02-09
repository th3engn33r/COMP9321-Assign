package beans;


import java.awt.Component;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/*import javax.mail.*;
import javax.mail.internet.*;*/
import javax.swing.JOptionPane;

import business.support.AuctionServiceImpl;
import business.support.BidServiceImpl;

//import com.sun.glass.ui.Window;
import com.sun.java.swing.plaf.windows.resources.windows;

public class UserBean {
	private int uid;
	private String firstName;
	private String lastName;
	private String nickname;
	private int accessLevel;
	private String username;
	private String password;
	private String email;
	private String contactNumber;
	private String yearOfBirth;
	private AddressBean address;
	private String creditCard;
	private String confirmCode;
	private boolean isOnline;
	public UserBean(){
		super();
	}
	public String getYearOfBirth() {
		return yearOfBirth;
	}
	public void setYearOfBirth(String yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
	public String getConfirmCode() {
		return confirmCode;
	}
	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}
	public AddressBean getAddress() {
		return address;
	}
	public void setAddress(AddressBean address) {
		this.address = address;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	public void notify(AuctionItemBean item, int role){
		if (!item.isOpen()){//reserve price was not met until closing time. owner can choose accept or reject the highest bid
			System.out.println("reserve price wasn't met until closing time");
			if (role==1){
				if (this.isOnline){
					Component frame = null;
					JOptionPane.showMessageDialog(frame, "reserve price wasn't met until closing time");
				}
				else {
					System.out.println("not online, send email only to owner");
				}
				//return "sendBidOwner";
				sendemail("Auction Closed Time", "Your auction item has reached its closing time. Log in to http://localhost:8080/Auction2/ and check your Auction List page");
			}
			else {
				if (this.isOnline){
					Component frame = null;
					JOptionPane.showMessageDialog(frame, "Your bid has reached its closing time. Wait for the owner to accept/reject your bid");
				}
				else {
					System.out.println("not online, send email only to bidder");
				}
				sendemail("WIN A BID Notification", "Congratulation, You have won an item. Log in To http://localhost:8080/Auction2/ and check your Bid List page");
			}
		}
		//else {//there's a bidder who bid >= reserve price before closing time
			//sendemail();
		//}
		//sendemail();
	}
	
	public boolean checkBid(){
		BidServiceImpl bidService = new BidServiceImpl();
		ArrayList<BidBean> winbidlist = bidService.getBidItemsByUserAndStatus(this.uid, 1);
		if (winbidlist.isEmpty()){
			return false;
		}
		return true;
	}
	
	public boolean checkAuction(){
		AuctionServiceImpl auctionService = new AuctionServiceImpl();
		ArrayList<AuctionItemBean> closedbidlist = auctionService.getClosedAuctionItemsByOwner(uid, new Date());
		if (closedbidlist.isEmpty()){
			return false;
		}
		for (int i=0; i<closedbidlist.size(); i++){
			if (closedbidlist.get(i).getEndTime().getTime() < new Date().getTime()){
				BidServiceImpl bidService = new BidServiceImpl();
				//BidBean closedbid = bidService.getBidItemById(closedbidlist.get(i).getId());
				auctionService.updatePriceToZero(closedbidlist.get(i));
				bidService.updateBidStatus(closedbidlist.get(i).getId(), 1, 2);
				//bidService.updateBidStatus(closedbidlist.get(i).getId(), 1, 0);
				return true;
			}
		}
		return false;
	}
	
	public void sendemail(String subject, String content){
		System.out.println("send email");
		  String userEmail=this.getEmail();	

		  String host = "smtp.gmail.com";
		  int port = 587;
		  final String username = "auctiontime.Roo@gmail.com";
		  final String password = "AdminRoo";//your password
		
		  Properties props = new Properties();
		  props.put("mail.smtp.host", host);
		  props.put("mail.smtp.auth", "true");
		  props.put("mail.smtp.starttls.enable", "true");
		  props.put("mail.smtp.port", port);
		  Session session = Session.getInstance(props, new Authenticator() {
		   protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(username, password);
		   }
		  });
		
		  try {
		
		   Message message = new MimeMessage(session);
		   message.setFrom(new InternetAddress("auctiontime.roo@gmail.com"));
		   message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
		   message.setSubject(subject);
		   message.setText(content);
		
		   Transport transport = session.getTransport("smtp");
		   transport.connect(host, port, username, password);
		
		   Transport.send(message);
		
		   System.out.println("Email is sent.");
		
		  } catch (MessagingException e) {
		   throw new RuntimeException(e);
		  }
    }
	
}
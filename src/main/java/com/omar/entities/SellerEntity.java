package com.omar.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class SellerEntity {

	@Id
	@GeneratedValue
	long id;
	long visaCardId;
	String username;

	String password;
	Date birth_date;
	String title;
	@NotNull
	String email;
	String phoneNumber;
	String address;
	String profileImage;
	String gender;
	String lang;
	String facebookUrl;
	String status;
	boolean hasStockFlag;
	boolean differentPricesFlag;
	Date registrationDate;
	Date modificationDate;
	double rate;

	// 0 is seller - 1 is customer
	@Column(columnDefinition = "integer default 0")
	int userType;

	@OneToMany(mappedBy = "seller")
	private List<UserEntity> users;

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVisaCardId() {
		return visaCardId;
	}

	public void setVisaCardId(long visaCardId) {
		this.visaCardId = visaCardId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isHasStockFlag() {
		return hasStockFlag;
	}

	public void setHasStockFlag(boolean hasStockFlag) {
		this.hasStockFlag = hasStockFlag;
	}

	public boolean isDifferentPricesFlag() {
		return differentPricesFlag;
	}

	public void setDifferentPricesFlag(boolean differentPricesFlag) {
		this.differentPricesFlag = differentPricesFlag;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}

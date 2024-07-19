package models.user;

import java.sql.Date;

import org.jetbrains.annotations.NotNull;

public class UserDTO {

  private int id;
  private String email;
  private String name;
  private Gender gender;
  private String address;
  private String phoneNumber;
  private Date createdAt;
  private Role role;
  
  private double moneySpend;

  public UserDTO() {}

  public UserDTO(
    int id,
    @NotNull String email,
    @NotNull String name,
    @NotNull Gender gender,
    @NotNull String address,
    @NotNull String phoneNumber,
    @NotNull Date createdAt,
    @NotNull Role role
  ) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.gender = gender;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.createdAt = createdAt;
    this.role = role;
  }

  public int getId() {
    return id;
  }

  public @NotNull String getEmail() {
    return email;
  }

  public void setEmail(@NotNull String email) {
    this.email = email;
  }

  public @NotNull String getName() {
    return name;
  }

  public void setName(@NotNull String name) {
    this.name = name;
  }

  public @NotNull Gender getGender() {
    return gender;
  }

  public void setGender(@NotNull Gender gender) {
    this.gender = gender;
  }

  public @NotNull String getAddress() {
    return address;
  }

  public void setAddress(@NotNull String address) {
    this.address = address;
  }

  public @NotNull String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(@NotNull String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public @NotNull Date getCreatedAt() {
    return createdAt;
  }

  public @NotNull Role getRole() {
    return role;
  }

  public void setRole(@NotNull Role role) {
    this.role = role;
  }
  
  public void setMoneySpend(double moneySpend) {
	this.moneySpend = moneySpend;
  }
  
  public double getMoneySpend() {
	return moneySpend;
  }
}

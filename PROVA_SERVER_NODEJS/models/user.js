class user {
  setUsername(username){
    this.username = username;
    return this;
  }
  setId(id){
    this.id =id;
    return this;
  }
  setDateOfBirth(dateOfBirth){
    this.dateOfBirth = dateOfBirth;
    return this;
  }
  setEmail(email){
    this.email = email;
    return this;
  }
  setPassword(password){
    this.password = password;
    return this;
  }
  setCreditCards(creditCards){
    this.creditCards = creditCards;
    return this;

  }
  setOrder(order){
      this.order = order;
      return this;
  }
  setDeviceToken(deviceToken){
      this.deviceToken = deviceToken;
      return this;
  }
}

module.exports = user;

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
}

module.exports = user;

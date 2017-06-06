class user {
  setName(name){
    this.name = name;
    return this;
  }
  setId(id){
    this.id =id;
    return this;
  }
  setAge(age){
    this.age = age;
    return this;
  }
  setEmail(email){
    this.email = email;
    return this;
  }
  set(password){
    this.password = password;
    return this;
  }
}

module.exports = user;

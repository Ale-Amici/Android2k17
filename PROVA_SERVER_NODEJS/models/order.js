class order {
    setId(id){
    this.id = id;
    return this;
    }
    setStatus(status){
      this.status = status;
      return this;
    }
    setCreationDate(creationDate){
        this.creationDate = creationDate;
        return this;
    }
    setIsPaid(isPaid){
      this.isPaid = isPaid;
      return this;
    }
    setTotalPrice(totalPrice){
      this.totalPrice = totalPrice;
      return this;
    }
    setOrderItems(orderItems){
      this.orderItems = orderItems;
      return this;
    }
    setUsingCreditCard(usingCreditCard){
      this.usingCreditCard = usingCreditCard;
      return this;
    }
    setChosenCreditCard(chosenCreditCard){
      this.chosenCreditCard = chosenCreditCard;
      return this;
    }
    setChosenDeliveryPlace(chosenDeliveryPlace){
      this.chosenDeliveryPlace = chosenDeliveryPlace;
      return this;
    }
    setChosenBarId(chosenBarId) {
      this.chosenBarId = chosenBarId;
      return this;
    }
    setCustomerSessionToken(customerSessionToken){
        this.customerSessionToken = customerSessionToken;
        return this;
    }
    setDestroyCode(destroyCode){
        this.destroyCode = destroyCode;
        return this;
    }
}

module.exports = order;

class order {
  setId(id){
    this.id = id;
    return this;
  }
  setStatus(status){
      this.status = status;
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
  setChosenPayment(chosenPayment){
      this.chosenPayment = chosenPayment;
      return this;
  }
  setChosenDeliveryPlace(chosenDeliveryPlace){
      this.chosenDeliveryPlace = chosenDeliveryPlace;
      return this;
  }
}

module.exports = order;

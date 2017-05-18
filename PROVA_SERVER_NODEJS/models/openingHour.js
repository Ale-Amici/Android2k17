class openingHour {
  setDayOfWeek(dayOfWeek){
    this.dayOfWeek = dayOfWeek;
    return this;
  }
  setTimeOpen(timeOpen){
    this.timeOpen = timeOpen;
    return this;
  }
  setWorkingTime(workingTime){
    this.workingTime = workingTime;
    return this;
  }

}

module.exports = openingHour;

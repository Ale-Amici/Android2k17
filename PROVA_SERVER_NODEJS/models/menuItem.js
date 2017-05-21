class menuItem {

    setId(id){
        this.id = id;
        return this;
    }

    setName(name){
        this.name = name;
        return this;
    }

    setDescription(description){
        this.description = description;
        return this;
    }
    setCategory(category){
        this.category = category;
        return this;
    }

}

module.exports = menuItem;

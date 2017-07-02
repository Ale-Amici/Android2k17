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

    setIngredients(ingredients){
        this.ingredients = ingredients;
        return this;
    }

    setAdditions(additions){
        this.additions = additions;
        return this;
    }

    setSizes(sizes){
        this.sizes = sizes;
        return this;
    }
    setDiscount(discount){
        this.discount = discount;
        return this;
    }
}

module.exports = menuItem;

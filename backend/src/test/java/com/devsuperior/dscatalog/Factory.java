package com.devsuperior.dscatalog;

import com.devsuperior.dscatalog.entities.Category;

public class Factory {
	
	public static Category category() {
		return new Category(1L, "Electronics");
	}

}

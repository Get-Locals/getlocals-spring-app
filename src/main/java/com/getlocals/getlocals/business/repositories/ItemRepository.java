package com.getlocals.getlocals.business.repositories;

import com.getlocals.getlocals.business.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}

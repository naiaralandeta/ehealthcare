package com.dell.ehealthcare.services;

import com.dell.ehealthcare.model.Item;
import com.dell.ehealthcare.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> findAll(){ return itemRepository.findAll();}

    public Optional<Item> findOne(Long id){
        return itemRepository.findById(id);
    }

    public void deleteById(Long id){
        itemRepository.deleteById(id);
    }
}

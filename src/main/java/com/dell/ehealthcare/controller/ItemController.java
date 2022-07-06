package com.dell.ehealthcare.controller;


import com.dell.ehealthcare.model.Item;
import com.dell.ehealthcare.services.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/api/user/item")
    public ResponseEntity<Object> createItem(@RequestBody Item item){
        Item savedItem = itemService.save(item);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedItem.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

    @GetMapping("/api/user/item")
    public ResponseEntity<List<Item>> retrieveAllItems(){

        List<Item> orders = itemService.findAll();

        if(!orders.isEmpty()){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/api/user/item/{id}")
    public ResponseEntity<Object> updateQuantity(@PathVariable("id") Long id, @RequestBody Item item){
        Optional<Item> itemData = itemService.findOne(id);

        if(itemData.isPresent()){
            Item updatedItem = itemData.get();
            updatedItem.setQuantity(item.getQuantity());

            return new ResponseEntity<>(itemService.save(updatedItem), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("api/user/item/{id}")
    public void deleteMedicine(@PathVariable Long id){
        itemService.deleteById(id);
    }
}

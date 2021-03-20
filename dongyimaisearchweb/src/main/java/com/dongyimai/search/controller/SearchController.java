package com.dongyimai.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dongyimai.search.service.ItemSearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/itemsearch")
public class SearchController {

    @Reference
    private ItemSearchService itemSearchService;

    @RequestMapping("/search")
    public Map<String,Object> search(@RequestBody Map searchMap){
//        System.out.println("searchMap : " + searchMap.get("keywords"));
        return itemSearchService.search(searchMap);
    }


}

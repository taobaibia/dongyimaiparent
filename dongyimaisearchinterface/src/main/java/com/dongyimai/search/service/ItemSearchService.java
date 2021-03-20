package com.dongyimai.search.service;

import com.dongyimai.pojo.TbItem;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {

    public Map<String,Object> search(Map searchMap);

    public void deleteByGoodsIds(Long[] ids);

    public void importList(List<TbItem> itemList);

}

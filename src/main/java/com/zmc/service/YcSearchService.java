package com.zmc.service;

import java.util.List;

import com.zmc.common.entity.YcPageEntity;
import com.zmc.common.entity.YcSearchEntity;

public interface YcSearchService {
    
    List<YcSearchEntity> searchByKey(String dir,String key)throws Exception;
    YcPageEntity searchPageNum(String dir,String key, int pageIndex, int pageSize)throws Exception;

}

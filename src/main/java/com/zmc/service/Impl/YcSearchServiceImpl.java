package com.zmc.service.Impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.zmc.common.entity.YcPageEntity;
import com.zmc.common.entity.YcSearchEntity;
import com.zmc.service.YcSearchService;
import com.zmc.utils.lucene.ConstantLucene;
import com.zmc.utils.lucene.SearcherDo;

@Service
public class YcSearchServiceImpl implements YcSearchService {

	@Override
	public List<YcSearchEntity> searchByKey(String dir,String key) throws Exception {

		SearcherDo sd = new SearcherDo();
		return sd.ycSearchForFile(dir, key);
		
	}

	@Override
	public YcPageEntity searchPageNum(String dir,String key, int pageIndex, int pageSize) throws Exception {

		SearcherDo sd = new SearcherDo();
		List<YcSearchEntity> list = sd.searchPageByAfter(dir, key, pageIndex, pageSize);
		if(list == null){
			return null;
		}
		YcPageEntity one = new YcPageEntity();
		one.setList(list);
		one.setPageIndex(pageIndex);
		one.setPageSize(pageSize);
		return one;
	}

}

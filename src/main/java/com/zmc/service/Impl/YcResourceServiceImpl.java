package com.zmc.service.Impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmc.common.entity.YcResource;
import com.zmc.constant.Constant;
import com.zmc.mapper.YcResourceMapper;
import com.zmc.service.YcResourceService;

@Service
public class YcResourceServiceImpl implements YcResourceService {

    @Autowired
    private YcResourceMapper ycResourceMapper;

    public void addYcResource(YcResource role) throws Exception {
        ycResourceMapper.insertYcResource(role);
    }

    public Boolean deleteYcResourceById(Long id) {
        try {
            Integer result = ycResourceMapper.deleteYcResourceById(id);
            if (result>0)
                return true;
            else
                return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public YcResource findYcResourceById(Long id) throws Exception {
		
		return ycResourceMapper.findYcResourceById(id);
	}

    public List<YcResource> findAllYcResources() throws Exception {
        return ycResourceMapper.findAllYcResources();
    }

    public Boolean forbidYcResourceById(Long id) throws Exception {
        try {
            Integer result = ycResourceMapper.forbidOrEnableYcResourceById(id,Constant.DISABLE);
            if (result>0)
                return true;
            else
                return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Boolean enableYcResourceById(Long id) throws Exception {
        try {
            Integer result = ycResourceMapper.forbidOrEnableYcResourceById(id,Constant.ENABLE);
            if (result>0)
                return true;
            else
                return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Boolean updateYcResource(YcResource role) throws Exception {
        Integer result = ycResourceMapper.updateYcResource(role);
        return result > 0;
    }

    public void relateYcResourceAndResource(Long YcResourceId, Long resourceId) throws Exception {
        ycResourceMapper.relateYcResourceAndResource(YcResourceId,resourceId);
    }

    public void unelateYcResourceAndResource(Long YcResourceId, Long resourceId) throws Exception {
        ycResourceMapper.unrelateYcResourceAndResource(YcResourceId,resourceId);
    }


}

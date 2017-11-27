package com.zmc.service.Impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmc.common.entity.YcResourceServer;
import com.zmc.constant.Constant;
import com.zmc.mapper.YcResourceServersMapper;
import com.zmc.service.YcResourceServersService;

@Service
public class YcResourceServersServiceImpl implements YcResourceServersService {

    @Autowired
    private YcResourceServersMapper ycResourceMapper;

    public void addYcResource(YcResourceServer role) throws Exception {
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
	public YcResourceServer findYcResourceById(Long id) throws Exception {
		
		return ycResourceMapper.findYcResourceById(id);
	}

    public List<YcResourceServer> findAllYcResources() throws Exception {
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

    public Boolean updateYcResource(YcResourceServer role) throws Exception {
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

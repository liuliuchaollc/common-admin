package com.zmc.service;

import java.util.List;

import com.zmc.common.entity.YcResourceServer;

public interface YcResourceServersService {
    void addYcResource(YcResourceServer role) throws Exception;
    Boolean deleteYcResourceById(Long id)throws Exception;
    YcResourceServer findYcResourceById(Long id)throws Exception;
    List<YcResourceServer> findAllYcResources()throws Exception;
    Boolean forbidYcResourceById(Long id) throws Exception;
    Boolean enableYcResourceById(Long id) throws Exception;
    Boolean updateYcResource(YcResourceServer role)throws Exception;
    void relateYcResourceAndResource(Long YcResourceId,Long resourceId)throws Exception;
    void unelateYcResourceAndResource(Long YcResourceId,Long resourceId)throws Exception;

}

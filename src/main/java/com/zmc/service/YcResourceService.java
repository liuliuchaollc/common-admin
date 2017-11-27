package com.zmc.service;

import java.util.List;

import com.zmc.common.entity.YcResource;

public interface YcResourceService {
    void addYcResource(YcResource role) throws Exception;
    Boolean deleteYcResourceById(Long id)throws Exception;
    YcResource findYcResourceById(Long id)throws Exception;
    List<YcResource> findAllYcResources()throws Exception;
    Boolean forbidYcResourceById(Long id) throws Exception;
    Boolean enableYcResourceById(Long id) throws Exception;
    Boolean updateYcResource(YcResource role)throws Exception;
    void relateYcResourceAndResource(Long YcResourceId,Long resourceId)throws Exception;
    void unelateYcResourceAndResource(Long YcResourceId,Long resourceId)throws Exception;

}

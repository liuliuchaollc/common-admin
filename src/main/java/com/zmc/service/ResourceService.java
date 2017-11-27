package com.zmc.service;

import java.util.List;

import com.zmc.common.entity.Resource;

/**
 * Created by zhongmc on 2017/6/29.
 */
public interface ResourceService {
    void addResource(Resource resource)throws Exception;
    List<Resource> findAllResources()throws Exception;
    Boolean deleteResourceById(Long id);
    List<Resource> findWildResourcesByUsername(String username) throws Exception;
    List<Resource> findResourceByRoleId(Long id)throws Exception;
    void deleteResourceBatchByIds(List<Long> ids)throws Exception;
    Boolean updateResource(Resource resource);
}

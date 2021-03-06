package com.zmc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmc.common.entity.YcResourceServer;

public interface YcResourceServersMapper {
    /**
     * 新增角色
     */
    void insertYcResource(YcResourceServer role) throws Exception;
    /**
     * 删除角色
     */
    Integer deleteYcResourceById(Long id) throws Exception;
    
    /**
     * find by id
     */
    YcResourceServer findYcResourceById(Long id) throws Exception;
    /**
     * 禁用角色
     */
    Integer forbidOrEnableYcResourceById(@Param("id") Long id, @Param("code") Integer code) throws Exception;
    /**
     * 获取所有角色
     */
    List<YcResourceServer> findAllYcResources()throws Exception;
    /**
     * 更新角色
     */
    Integer updateYcResource(YcResourceServer role)throws Exception;

    /**
     * 关联YcResource和resource
     * @param YcResourceId
     * @param resourceId
     * @return
     * @throws Exception
     */
    void relateYcResourceAndResource(@Param("YcResourceId") Long YcResourceId,@Param("resourceId")Long resourceId)throws Exception;

    /**
     * 取消关联YcResource和resource
     * @param YcResourceId
     * @param resourceId
     * @return
     * @throws Exception
     */
    void unrelateYcResourceAndResource(@Param("YcResourceId") Long YcResourceId,@Param("resourceId")Long resourceId)throws Exception;
}

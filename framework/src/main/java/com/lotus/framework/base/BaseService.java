package com.lotus.framework.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lotus.datasources.utils.PageUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

public interface BaseService<T extends BaseEntity> extends IService<T> {

    PageUtils queryPage(Map<String, Object> params);


//    SysUserEntity getUser();

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    boolean save(T entity);

    @Override
    default boolean saveBatch(Collection<T> entityList) {
        return this.saveBatch(entityList, 1000);
    }

    @Override
    boolean saveBatch(Collection<T> entityList, int batchSize);


    @Override
    boolean updateById(T entity);

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    default boolean saveOrUpdateBatch(Collection<T> entityList) {
        return this.saveOrUpdateBatch(entityList, 1000);
    }

    @Override
    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);


    @Override
    boolean update(T entity, Wrapper<T> updateWrapper);

    @Override
    default boolean update(Wrapper<T> updateWrapper) {
        return this.update(null, updateWrapper);
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    default boolean updateBatchById(Collection<T> entityList) {
        return this.updateBatchById(entityList, 1000);
    }

    @Override
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    @Override
    boolean saveOrUpdate(T entity);

}

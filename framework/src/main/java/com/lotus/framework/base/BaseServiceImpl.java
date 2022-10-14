package com.lotus.framework.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lotus.datasources.utils.PageUtils;
import com.lotus.datasources.utils.Query;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @program: framework
 * @description:
 * @author: changjiz
 * @create: 2019-07-02 11:50
 **/
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        IPage<T> page = this.page(
                new Query<T>().getPage(params),
                new QueryWrapper<T>().orderByDesc("gmt_modified")
        );

        return new PageUtils(page);
    }

    /*@Override
    public SysUserEntity getUser() {
        try {
            Object obj = SecurityUtils.getSubject().getPrincipal();
            if (null != obj) {
                return (SysUserEntity) obj;
            }
        } catch (UnavailableSecurityManagerException e) {
            logger.info("获取登录用户失败，可能存在匿名访问--->BaseServiceImpl");
        }
        return null;
    }*/

    @Override
    public boolean save(T entity) {
        init(Arrays.asList(entity));
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        init(entityList);
        return super.saveBatch(entityList, batchSize);
    }

    @Override
    public boolean updateById(T entity) {
        setUpdateVal(Arrays.asList(entity));
        return super.updateById(entity);
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        setUpdateVal(Arrays.asList(entity));
        return super.update(entity, updateWrapper);
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        setUpdateVal(entityList);
        return super.updateBatchById(entityList, batchSize);
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        if (null == entity.getId() || 0L == entity.getId()) {
            init(Arrays.asList(entity));
        } else {
            setUpdateVal(Arrays.asList(entity));
        }
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        entityList.forEach(entity -> {
            if (null == entity.getId() || 0L == entity.getId()) {
                init(Arrays.asList(entity));
            } else {
                setUpdateVal(Arrays.asList(entity));
            }
        });
        return super.saveOrUpdateBatch(entityList, batchSize);
    }


    private void init(Collection<T> entitys) {
        Long userId = BaseUtils.getUserId();
        Date now = new Date();
        entitys.forEach(entity -> {
            entity.setIsDeleted(0);
            entity.setCreator(userId);
            entity.setModifier(userId);
            entity.setGmtCreated(now);
            entity.setGmtModified(now);
        });
    }

    private void setUpdateVal(Collection<T> entitys) {
        Long userId = BaseUtils.getUserId();
        Date now = new Date();
        entitys.forEach(entity -> {
            if (null != entity) {
                entity.setModifier(userId);
                entity.setGmtModified(now);
            }
        });
    }

}

package top.itcat.aop.multipledb;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import top.itcat.aop.multipledb.annotation.DataSource;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@DataSource
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
    public T selectById(Serializable id) {
        return super.baseMapper.selectById(id);
    }

    public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
        return super.baseMapper.selectBatchIds(idList);
    }

    public List<T> selectByMap(Map<String, Object> columnMap) {
        return super.baseMapper.selectByMap(columnMap);
    }

    public T selectOne(Wrapper<T> wrapper) {
        return SqlHelper.getObject(super.baseMapper.selectList(wrapper));
    }

    public Map<String, Object> selectMap(Wrapper<T> wrapper) {
        return super.selectMap(wrapper);
    }

    public Object selectObj(Wrapper<T> wrapper) {
        return super.selectObj(wrapper);
    }

    public int selectCount(Wrapper<T> wrapper) {
        return super.selectCount(wrapper);
    }

    public List<T> selectList(Wrapper<T> wrapper) {
        return super.baseMapper.selectList(wrapper);
    }

    public Page<T> selectPage(Page<T> page) {
        return super.selectPage(page, Condition.EMPTY);
    }

    public List<Map<String, Object>> selectMaps(Wrapper<T> wrapper) {
        return super.baseMapper.selectMaps(wrapper);
    }

    public List<Object> selectObjs(Wrapper<T> wrapper) {
        return super.baseMapper.selectObjs(wrapper);
    }

    public Page<Map<String, Object>> selectMapsPage(Page page, Wrapper<T> wrapper) {
        return super.selectMapsPage(page, wrapper);
    }

    public Page<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
        return super.selectPage(page, wrapper);
    }
}

package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.rest.dao.impl.JedisClientSingle;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * redis服务
 * @author: xianzhixianzhixian on 2018/9/3
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private JedisClientSingle jedisClientSingle;
    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Override
    public TaotaoResult syncContent(Long contentCid) {
        Long result = 0L;
        try {
            result = jedisClientSingle.hdel(INDEX_CONTENT_REDIS_KEY, contentCid+"");
        }catch (Exception e){
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok(result);
    }

    @Override
    public TaotaoResult syncItem(Long itemId) {
        Long result = 0L;
        try{
            result = jedisClientSingle.del(REDIS_ITEM_KEY + ":" + itemId + ":base");
            result = jedisClientSingle.del(REDIS_ITEM_KEY + ":" + itemId + ":desc");
            result = jedisClientSingle.del(REDIS_ITEM_KEY + ":" + itemId + ":param");
        }catch (Exception e){
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok(result);
    }
}

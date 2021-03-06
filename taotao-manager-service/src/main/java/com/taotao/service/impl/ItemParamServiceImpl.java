package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

/**
 * 商品规格参数模板管理
 * @author yufeng 2018/1/3
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Override
	public TaotaoResult getItemParamByCid(Long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//判断是否查询到结果
		if (list != null && list.size() > 0) {
			return TaotaoResult.ok(list.get(0));
		}
		
		return TaotaoResult.ok();
	}

	/**
	 * 添加某一类商品规格参数模板
	 * @param itemParam
	 * @return
	 */
	@Override
	public TaotaoResult insertItemParam(TbItemParam itemParam) throws Exception {
		//补全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		//插入到规格参数模板表
		int count = itemParamMapper.insert(itemParam);
		if (count == 0){
		    return TaotaoResult.build(500, "插入商品规则参数错误");
        }
		return TaotaoResult.ok(count);
	}

    @Override
    public EasyUIDataGridResult selectItemParamList(Integer page, Integer rows) {
        //查询商品规则参数列表
        TbItemParamExample itemParamExample = new TbItemParamExample();
        //分页处理
        PageHelper.offsetPage(page,rows);
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(itemParamExample);
        //创建一个返回值对象
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        //取记录总条数，这里要把list作为参数，否则拿不到总条数
        PageInfo<TbItemParam> pageInfo=new PageInfo<>(list);
        easyUIDataGridResult.setTotal(pageInfo.getTotal());
        //设置List
        easyUIDataGridResult.setRows(list);
        return easyUIDataGridResult;
    }

	@Override
	public TaotaoResult deleteItemParamsByIds(List<Long> ids) throws Exception {
	    int count = 0;
	    for (Long id : ids){
	        count += itemParamMapper.deleteByPrimaryKey(id);
        }
        if (count == 0){
            return TaotaoResult.build(500, "删除商品规则参数错误");
        }
		return TaotaoResult.ok(count);
	}
}

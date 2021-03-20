package com.dongyimai.sellergoods.service.impl;
import java.util.List;

import com.dongyimai.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.dongyimai.mapper.TbSellerMapper;
import com.dongyimai.pojo.TbSeller;
import com.dongyimai.pojo.TbSellerExample;
import com.dongyimai.pojo.TbSellerExample.Criteria;
import com.dongyimai.sellergoods.service.SellerService;

import com.dongyimai.entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private TbSellerMapper sellerMapper;

	@Override
	public void updateStatus(String status,String sellerId) {
		//1、查询对象
		TbSeller seller = sellerMapper.selectByPrimaryKey(sellerId);
		seller.setStatus(status);
		//2、修改状态
		sellerMapper.updateByPrimaryKey(seller);

		//优化 绝对优化
		//优化 1、系统配置与部署 2、数据库(优化) 3、代码 能使用抽象 不使用循环 流程控制
		//循环 for 			for-each 3个以内		 iterator
	}

	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeller> findAll() {
		return sellerMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSeller> page=   (Page<TbSeller>) sellerMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSeller seller) {
		sellerMapper.insert(seller);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeller seller){
		sellerMapper.updateByPrimaryKey(seller);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbSeller findOne(String sellerId){
		return sellerMapper.selectByPrimaryKey(sellerId);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] sellerIds) {
		for(String sellerId:sellerIds){
			sellerMapper.deleteByPrimaryKey(sellerId);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSeller seller, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSellerExample example=new TbSellerExample();
		Criteria criteria = example.createCriteria();
		
		if(seller!=null){			
						if(seller.getName()!=null && seller.getName().length()>0){
				criteria.andNameLike("%"+seller.getName()+"%");
			}			if(seller.getNickName()!=null && seller.getNickName().length()>0){
				criteria.andNickNameLike("%"+seller.getNickName()+"%");
			}			if(seller.getPassword()!=null && seller.getPassword().length()>0){
				criteria.andPasswordLike("%"+seller.getPassword()+"%");
			}			if(seller.getEmail()!=null && seller.getEmail().length()>0){
				criteria.andEmailLike("%"+seller.getEmail()+"%");
			}			if(seller.getMobile()!=null && seller.getMobile().length()>0){
				criteria.andMobileLike("%"+seller.getMobile()+"%");
			}			if(seller.getTelephone()!=null && seller.getTelephone().length()>0){
				criteria.andTelephoneLike("%"+seller.getTelephone()+"%");
			}			if(seller.getStatus()!=null && seller.getStatus().length()>0){
				criteria.andStatusEqualTo(seller.getStatus());
			}			if(seller.getAddressDetail()!=null && seller.getAddressDetail().length()>0){
				criteria.andAddressDetailLike("%"+seller.getAddressDetail()+"%");
			}			if(seller.getLinkmanName()!=null && seller.getLinkmanName().length()>0){
				criteria.andLinkmanNameLike("%"+seller.getLinkmanName()+"%");
			}			if(seller.getLinkmanQq()!=null && seller.getLinkmanQq().length()>0){
				criteria.andLinkmanQqLike("%"+seller.getLinkmanQq()+"%");
			}			if(seller.getLinkmanMobile()!=null && seller.getLinkmanMobile().length()>0){
				criteria.andLinkmanMobileLike("%"+seller.getLinkmanMobile()+"%");
			}			if(seller.getLinkmanEmail()!=null && seller.getLinkmanEmail().length()>0){
				criteria.andLinkmanEmailLike("%"+seller.getLinkmanEmail()+"%");
			}			if(seller.getLicenseNumber()!=null && seller.getLicenseNumber().length()>0){
				criteria.andLicenseNumberLike("%"+seller.getLicenseNumber()+"%");
			}			if(seller.getTaxNumber()!=null && seller.getTaxNumber().length()>0){
				criteria.andTaxNumberLike("%"+seller.getTaxNumber()+"%");
			}			if(seller.getOrgNumber()!=null && seller.getOrgNumber().length()>0){
				criteria.andOrgNumberLike("%"+seller.getOrgNumber()+"%");
			}			if(seller.getLogoPic()!=null && seller.getLogoPic().length()>0){
				criteria.andLogoPicLike("%"+seller.getLogoPic()+"%");
			}			if(seller.getBrief()!=null && seller.getBrief().length()>0){
				criteria.andBriefLike("%"+seller.getBrief()+"%");
			}			if(seller.getLegalPerson()!=null && seller.getLegalPerson().length()>0){
				criteria.andLegalPersonLike("%"+seller.getLegalPerson()+"%");
			}			if(seller.getLegalPersonCardId()!=null && seller.getLegalPersonCardId().length()>0){
				criteria.andLegalPersonCardIdLike("%"+seller.getLegalPersonCardId()+"%");
			}			if(seller.getBankUser()!=null && seller.getBankUser().length()>0){
				criteria.andBankUserLike("%"+seller.getBankUser()+"%");
			}			if(seller.getBankName()!=null && seller.getBankName().length()>0){
				criteria.andBankNameLike("%"+seller.getBankName()+"%");
			}	
		}
		
		Page<TbSeller> page= (Page<TbSeller>)sellerMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}

package com.slrclub.free.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slrclub.free.domain.SlrContentsItem;
import com.slrclub.free.persistence.SlrContentsItemMapper;

@Service
public class SlrContentsItemService {

	@Autowired
	private SlrContentsItemMapper itemMapper;

	public void insertItem(SlrContentsItem contents) {
		itemMapper.insertContentsItem(contents);
	}
	
	public void insertContents(SlrContentsItem contents) {
		itemMapper.insertContents(contents);
	}
	

	public void insertContents(ArrayList<SlrContentsItem> slrContentsItemList) {
		if(slrContentsItemList == null) {
			return;
		}
		
		for (SlrContentsItem contents : slrContentsItemList) {
			insertContents(contents);
		}
	}
	
	public void insertItem(ArrayList<SlrContentsItem> slrContentsItemList) {
		if(slrContentsItemList == null) {
			return;
		}
		
		for (SlrContentsItem contents : slrContentsItemList) {
			insertItem(contents);
		}
	}
	
//
//	public List<SlrContentsItem> getItemListByPage(int page) {
//		return itemMapper.getContentsItemListByPage(page);
//	}

	public SlrContentsItem getItemByCid(int cid) {
		System.out.println("SlrContentsItemService >> getItemByCid");
		return itemMapper.getContentsItemByCid(cid);
	}
}

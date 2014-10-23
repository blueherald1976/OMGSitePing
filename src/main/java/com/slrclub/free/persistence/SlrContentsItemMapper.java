package com.slrclub.free.persistence;

import java.util.List;

import com.slrclub.free.domain.SlrContentsItem;

public interface SlrContentsItemMapper {
	void insertContentsItem(SlrContentsItem contents);
//	List<SlrContentsItem> getContentsItemListByPage(int page);
	SlrContentsItem getContentsItemByCid(int cid);
//	SlrContentsItem SlrContentsItemService(int cid);

	void insertContents(SlrContentsItem contents);
}

/*Copyright (c) 2015-2016 WaveMaker.com All Rights Reserved.
 This software is the confidential and proprietary information of WaveMaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with WaveMaker.com*/
package com.wavemaker.sampleapps.wavekart.eshopping.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.file.model.Downloadable;

import com.wavemaker.sampleapps.wavekart.eshopping.OrderLineItems;
import com.wavemaker.sampleapps.wavekart.eshopping.OrderLineItemsId;


/**
 * ServiceImpl object for domain model class OrderLineItems.
 *
 * @see OrderLineItems
 */
@Service("eshopping.OrderLineItemsService")
public class OrderLineItemsServiceImpl implements OrderLineItemsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderLineItemsServiceImpl.class);


    @Autowired
    @Qualifier("eshopping.OrderLineItemsDao")
    private WMGenericDao<OrderLineItems, OrderLineItemsId> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<OrderLineItems, OrderLineItemsId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "eshoppingTransactionManager")
    @Override
	public OrderLineItems create(OrderLineItems orderLineItems) {
        LOGGER.debug("Creating a new OrderLineItems with information: {}", orderLineItems);
        OrderLineItems orderLineItemsCreated = this.wmGenericDao.create(orderLineItems);
        return orderLineItemsCreated;
    }

	@Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public OrderLineItems getById(OrderLineItemsId orderlineitemsId) throws EntityNotFoundException {
        LOGGER.debug("Finding OrderLineItems by id: {}", orderlineitemsId);
        OrderLineItems orderLineItems = this.wmGenericDao.findById(orderlineitemsId);
        if (orderLineItems == null){
            LOGGER.debug("No OrderLineItems found with id: {}", orderlineitemsId);
            throw new EntityNotFoundException(String.valueOf(orderlineitemsId));
        }
        return orderLineItems;
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public OrderLineItems findById(OrderLineItemsId orderlineitemsId) {
        LOGGER.debug("Finding OrderLineItems by id: {}", orderlineitemsId);
        return this.wmGenericDao.findById(orderlineitemsId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "eshoppingTransactionManager")
	@Override
	public OrderLineItems update(OrderLineItems orderLineItems) throws EntityNotFoundException {
        LOGGER.debug("Updating OrderLineItems with information: {}", orderLineItems);
        this.wmGenericDao.update(orderLineItems);

        OrderLineItemsId orderlineitemsId = new OrderLineItemsId();
        orderlineitemsId.setOrderId(orderLineItems.getOrderId());
        orderlineitemsId.setProductId(orderLineItems.getProductId());

        return this.wmGenericDao.findById(orderlineitemsId);
    }

    @Transactional(value = "eshoppingTransactionManager")
	@Override
	public OrderLineItems delete(OrderLineItemsId orderlineitemsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting OrderLineItems with id: {}", orderlineitemsId);
        OrderLineItems deleted = this.wmGenericDao.findById(orderlineitemsId);
        if (deleted == null) {
            LOGGER.debug("No OrderLineItems found with id: {}", orderlineitemsId);
            throw new EntityNotFoundException(String.valueOf(orderlineitemsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public Page<OrderLineItems> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all OrderLineItems");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Page<OrderLineItems> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all OrderLineItems");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service eshopping for table OrderLineItems to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

	@Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public long count(String query) {
        return this.wmGenericDao.count(query);
    }



}

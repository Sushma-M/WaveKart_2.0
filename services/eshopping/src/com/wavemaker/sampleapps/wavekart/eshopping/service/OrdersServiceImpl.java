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
import com.wavemaker.sampleapps.wavekart.eshopping.Orders;


/**
 * ServiceImpl object for domain model class Orders.
 *
 * @see Orders
 */
@Service("eshopping.OrdersService")
public class OrdersServiceImpl implements OrdersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Autowired
	@Qualifier("eshopping.OrderLineItemsService")
	private OrderLineItemsService orderLineItemsService;

    @Autowired
    @Qualifier("eshopping.OrdersDao")
    private WMGenericDao<Orders, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Orders, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "eshoppingTransactionManager")
    @Override
	public Orders create(Orders orders) {
        LOGGER.debug("Creating a new Orders with information: {}", orders);
        Orders ordersCreated = this.wmGenericDao.create(orders);
        if(ordersCreated.getOrderLineItemses() != null) {
            for(OrderLineItems orderLineItemse : ordersCreated.getOrderLineItemses()) {
                orderLineItemse.setOrders(ordersCreated);
                LOGGER.debug("Creating a new child OrderLineItems with information: {}", orderLineItemse);
                orderLineItemsService.create(orderLineItemse);
            }
        }
        return ordersCreated;
    }

	@Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public Orders getById(Integer ordersId) throws EntityNotFoundException {
        LOGGER.debug("Finding Orders by id: {}", ordersId);
        Orders orders = this.wmGenericDao.findById(ordersId);
        if (orders == null){
            LOGGER.debug("No Orders found with id: {}", ordersId);
            throw new EntityNotFoundException(String.valueOf(ordersId));
        }
        return orders;
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public Orders findById(Integer ordersId) {
        LOGGER.debug("Finding Orders by id: {}", ordersId);
        return this.wmGenericDao.findById(ordersId);
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "eshoppingTransactionManager")
	@Override
	public Orders update(Orders orders) throws EntityNotFoundException {
        LOGGER.debug("Updating Orders with information: {}", orders);
        this.wmGenericDao.update(orders);

        Integer ordersId = orders.getOrderId();

        return this.wmGenericDao.findById(ordersId);
    }

    @Transactional(value = "eshoppingTransactionManager")
	@Override
	public Orders delete(Integer ordersId) throws EntityNotFoundException {
        LOGGER.debug("Deleting Orders with id: {}", ordersId);
        Orders deleted = this.wmGenericDao.findById(ordersId);
        if (deleted == null) {
            LOGGER.debug("No Orders found with id: {}", ordersId);
            throw new EntityNotFoundException(String.valueOf(ordersId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public Page<Orders> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Orders");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Page<Orders> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Orders");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service eshopping for table Orders to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

	@Transactional(readOnly = true, value = "eshoppingTransactionManager")
	@Override
	public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "eshoppingTransactionManager")
    @Override
    public Page<OrderLineItems> findAssociatedOrderLineItemses(Integer orderId, Pageable pageable) {
        LOGGER.debug("Fetching all associated orderLineItemses");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("orders.orderId = '" + orderId + "'");

        return orderLineItemsService.findAll(queryBuilder.toString(), pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service OrderLineItemsService instance
	 */
	protected void setOrderLineItemsService(OrderLineItemsService service) {
        this.orderLineItemsService = service;
    }

}


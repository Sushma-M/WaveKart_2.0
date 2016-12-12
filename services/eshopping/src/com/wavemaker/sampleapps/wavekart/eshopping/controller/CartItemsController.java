/*Copyright (c) 2015-2016 WaveMaker.com All Rights Reserved.
 This software is the confidential and proprietary information of WaveMaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with WaveMaker.com*/
package com.wavemaker.sampleapps.wavekart.eshopping.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wavemaker.sampleapps.wavekart.eshopping.CartItems;
import com.wavemaker.sampleapps.wavekart.eshopping.CartItemsId;
import com.wavemaker.sampleapps.wavekart.eshopping.service.CartItemsService;

/**
 * Controller object for domain model class CartItems.
 * @see CartItems
 */
@RestController("eshopping.CartItemsController")
@Api(value = "CartItemsController", description = "Exposes APIs to work with CartItems resource.")
@RequestMapping("/eshopping/CartItems")
public class CartItemsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartItemsController.class);

    @Autowired
    @Qualifier("eshopping.CartItemsService")
    private CartItemsService cartItemsService;

    @ApiOperation(value = "Creates a new CartItems instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public CartItems createCartItems(@RequestBody CartItems cartItems) {
        LOGGER.debug("Create CartItems with information: {}", cartItems);
        cartItems = cartItemsService.create(cartItems);
        LOGGER.debug("Created CartItems with information: {}", cartItems);
        return cartItems;
    }

    @ApiOperation(value = "Returns the CartItems instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public CartItems getCartItems(@RequestParam(value = "cartId", required = true) Integer cartId, @RequestParam(value = "productId", required = true) Integer productId) throws EntityNotFoundException {
        CartItemsId cartitemsId = new CartItemsId();
        cartitemsId.setCartId(cartId);
        cartitemsId.setProductId(productId);
        LOGGER.debug("Getting CartItems with id: {}", cartitemsId);
        CartItems cartItems = cartItemsService.getById(cartitemsId);
        LOGGER.debug("CartItems details with id: {}", cartItems);
        return cartItems;
    }

    @ApiOperation(value = "Updates the CartItems instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public CartItems editCartItems(@RequestParam(value = "cartId", required = true) Integer cartId, @RequestParam(value = "productId", required = true) Integer productId, @RequestBody CartItems cartItems) throws EntityNotFoundException {
        cartItems.setCartId(cartId);
        cartItems.setProductId(productId);
        LOGGER.debug("CartItems details with id is updated with: {}", cartItems);
        return cartItemsService.update(cartItems);
    }

    @ApiOperation(value = "Deletes the CartItems instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteCartItems(@RequestParam(value = "cartId", required = true) Integer cartId, @RequestParam(value = "productId", required = true) Integer productId) throws EntityNotFoundException {
        CartItemsId cartitemsId = new CartItemsId();
        cartitemsId.setCartId(cartId);
        cartitemsId.setProductId(productId);
        LOGGER.debug("Deleting CartItems with id: {}", cartitemsId);
        CartItems cartItems = cartItemsService.delete(cartitemsId);
        return cartItems != null;
    }

    /**
     * @deprecated Use {@link #findCartItems(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of CartItems instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<CartItems> searchCartItemsByQueryFilters(Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering CartItems list");
        return cartItemsService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the list of CartItems instances matching the search criteria.")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<CartItems> findCartItems(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering CartItems list");
        return cartItemsService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportCartItems(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        return cartItemsService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns the total count of CartItems instances.")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Long countCartItems(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
        LOGGER.debug("counting CartItems");
        return cartItemsService.count(query);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service CartItemsService instance
	 */
    protected void setCartItemsService(CartItemsService service) {
        this.cartItemsService = service;
    }
}
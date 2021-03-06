/*Copyright (c) 2015-2016 WaveMaker.com All Rights Reserved.
 This software is the confidential and proprietary information of WaveMaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with WaveMaker.com*/
package com.wavemaker.sampleapps.wavekart.eshopping;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * CartDetails generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`CART_DETAILS`", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"`USER_ID`"})})
public class CartDetails implements Serializable {

    private Integer cartId;
    private int userId;
    private List<CartItems> cartItemses = new ArrayList<>();
    private UserDetails userDetails;

    @Id
    @Column(name = "`CART_ID`", nullable = false, scale = 0, precision = 10)
    public Integer getCartId() {
        return this.cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    @Column(name = "`USER_ID`", nullable = false, scale = 0, precision = 10)
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "cartDetails")
    public List<CartItems> getCartItemses() {
        return this.cartItemses;
    }

    public void setCartItemses(List<CartItems> cartItemses) {
        this.cartItemses = cartItemses;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`USER_ID`", referencedColumnName = "`USER_ID`", insertable = false, updatable = false)
    public UserDetails getUserDetails() {
        return this.userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        if(userDetails != null) {
            this.userId = userDetails.getUserId();
        }

        this.userDetails = userDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartDetails)) return false;
        final CartDetails cartDetails = (CartDetails) o;
        return Objects.equals(getCartId(), cartDetails.getCartId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCartId());
    }
}


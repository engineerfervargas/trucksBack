package com.test.demo.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders", schema = "dbo")
public class Order {
	
    @Id
    @GenericGenerator(name = "generator", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "generator")
    private String uuid;
    
    @ManyToOne
    @JoinColumn(name = "storeUuid")
    private Store store;
    @OneToOne
    @JoinColumn(name = "userUuid")
    private User user;
    @OneToOne
    @JoinColumn(name = "clientUuid")
    private Client client;
    
    @Column(name = "sale_date")
    private Timestamp saleDate;
    private String ip;
    private int status;
    private float subtotal;
    private float total;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_uuid")
    private List<OrderTruck> trucks;
    
    
}

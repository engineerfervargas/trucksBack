package com.test.demo.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.demo.entity.Client;
import com.test.demo.entity.Order;
import com.test.demo.entity.OrderTruck;
import com.test.demo.entity.Store;
import com.test.demo.entity.Truck;
import com.test.demo.entity.User;
import com.test.demo.model.Status;
import com.test.demo.model.request.OrderRequest;
import com.test.demo.model.response.OrderDetail;
import com.test.demo.model.response.OrderResponse;
import com.test.demo.repository.ClientRepository;
import com.test.demo.repository.OrderRepository;
import com.test.demo.repository.StoreRepository;
import com.test.demo.repository.TruckRepository;
import com.test.demo.service.OrderService;
import com.test.demo.util.SessionUtil;

@Service
public class OrderServiceImp implements OrderService {
	
	@Autowired
	private OrderRepository repository;
	@Autowired
	private StoreRepository storeRepo;
	@Autowired
	private TruckRepository truckRepo;
	@Autowired
	private ClientRepository clientRepo;
	@Autowired
	private SessionUtil util;

	@Override
	@Transactional
	public OrderResponse make(OrderRequest request) throws ParseException {
		synchronized (this){
			List<String> hawas = new ArrayList<String>();
			Map<String, Integer> hawasMap = fixHawas(request.getHawas());
			hawasMap.forEach((k, v) -> {
				hawas.add(k.toString());
			});
			List<Truck> trucks = truckRepo.findByHawaIn(hawas);
			List<Truck> filter = trucks.parallelStream().filter(
					t -> (Integer)t.getQuantity() >= hawasMap.get(t.getHawa())
					).collect(Collectors.toList());
			if(filter.size() == trucks.size()) {
				User user = util.getLoggedUser();
				Store store = storeRepo.findByUuid(request.getStore().toString()).get(); 
				Client client = clientRepo.findByUuid(request.getClient().toString()).get();
				String uuid = UUID.randomUUID().toString();
				List<OrderTruck> orderTruck = orderTrucks(trucks, hawasMap, uuid);
				float subtotal = orderTruck.stream().map(el -> el.getSubtotal()).reduce(0f, Float::sum);
				float total = orderTruck.stream().map(el -> el.getTotal()).reduce(0f, Float::sum);
				Order order = Order.builder()
						.uuid(uuid.toString())
						.store(store)
						.user(user)
						.client(client)
						.saleDate(date())
						.ip(request.getIp())
						.status(Status.PENDING.getValue())
						.subtotal(subtotal)
						.total(total)
						.trucks(orderTruck)
						.build();
				repository.save(order);
				
				OrderResponse response = OrderResponse.builder()
						.uuid(order.getUuid())
						.store(store.getName())
						.date(order.getSaleDate().toString())
						.user(user.getFirstName() + " " + user.getLastName())
						.client(client.getFirstName() + " " + client.getLastName())
						.trucks(orderDetail(orderTruck))
						.subtotal(subtotal)
						.total(total)
						.status(Status.PENDING.name())
						.ip(request.getIp())
						.build();
				
				updateInventary(trucks, hawasMap);
				
				return response;
			}
		}
		return null;
	}
	
	private Map<String, Integer> fixHawas(List<Map<String, Integer>> data){
		return data.stream().collect(LinkedHashMap<String, Integer>::new,
				(map, el) -> el.forEach((k, v) -> map.put(k, v)),
				(map1, map2) -> {});
	}
	
	private List<OrderTruck> orderTrucks(List<Truck> trucks, Map<String, Integer> hawas, String uuidOrder) {
		List<OrderTruck> orderTruck = new LinkedList<>();
		trucks.forEach(t -> {
			int quantity = hawas.get(t.getHawa());
			float subTotal = t.getPrice() * quantity;
			float percentageDiscount = 1.00f - t.getDiscount();
			float total = t.getPrice() * quantity * percentageDiscount;
			orderTruck.add(OrderTruck.builder()
					.orderUuid(uuidOrder)
					.truck(t)
					.quantity(quantity)
					.price(t.getPrice())
					.discount(t.getDiscount())
					.subtotal(subTotal)
					.total(total)
					.build());
		});
		return orderTruck;
	}
	
	private List<OrderDetail> orderDetail(List<OrderTruck> orderTruck) {
		return orderTruck.parallelStream().collect(
				LinkedList<OrderDetail>::new, 
				(list, el) -> {
					list.add(OrderDetail.builder()
							.hawa(el.getTruck().getHawa())
							.description(el.getTruck().getDescription())
							.quantity(el.getQuantity())
							.price(el.getPrice())
							.discount(el.getDiscount())
							.subtotal(el.getSubtotal())
							.total(el.getTotal())
							.build());
				}, 
				(list1, list2) -> list1.addAll(list2));
	}
	
	private void updateInventary(List<Truck> trucks, Map<String, Integer> hawas) {
		trucks.forEach(t -> {
			int quantitySold = hawas.get(t.getHawa());
			int newQuantity = t.getQuantity() - quantitySold;
			t.setQuantity(newQuantity);
			truckRepo.save(t);
		});	
	}

	@Override
	public List<OrderResponse> orders() {
		List<Order> orders = repository.findAll();
		List<OrderResponse> responses = new LinkedList<OrderResponse>();
		orders.forEach(o -> {
			Store store = o.getStore();
			User user = o.getUser();
			Client client = o.getClient();
			
			responses.add(
				OrderResponse.builder()
					.uuid(o.getUuid())
					.store(store.getName())
					.date(o.getSaleDate().toString())
					.user(user.getFirstName() + " " + user.getLastName())
					.client(client.getFirstName() + " " + client.getLastName())
					.trucks(orderDetail(o.getTrucks()))
					.subtotal(o.getSubtotal())
					.total(o.getTotal())
					.status(status(o.getStatus()))
					.ip(o.getIp())
					.build()
				);
		});
		return responses;
	}
	
	private String status(int value) {
		switch (value) {
			case 0:
				return Status.PENDING.name();
			case 1:
				return Status.COMPLETED.name();
			case 2:
				return Status.CANCELED.name();
			default:
				return Status.PENDING.name();
		}
	}
	
	private Timestamp date() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String timeString = df.format(System.currentTimeMillis());
		Date date = df.parse(timeString);
		return new Timestamp(date.getTime());
	}

	@Override
	public void updateStatus(String uuid, int status) {
		Optional<Order> result = repository.findByUuid(uuid);
		if(result.isPresent()) {
			Order order = result.get();
			String str = status(status);
			Status s = Status.valueOf(str);
			order.setStatus(s.getValue());
			repository.save(order);
		}
	}

}

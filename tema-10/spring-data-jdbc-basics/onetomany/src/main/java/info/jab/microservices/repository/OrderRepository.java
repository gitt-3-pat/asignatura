package info.jab.microservices.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import info.jab.microservices.model.PurchaseOrder;

interface OrderRepository extends CrudRepository<PurchaseOrder, Long> {

	@Query("select count(*) from order_item")
	int countItems();

	@Query("select * from purchase_order where purchase_order.shipping_address= :address")
	List<PurchaseOrder> findByShippingAddress(@Param("address") String address);
}

package cnabookstore.order;

import javax.persistence.*;

import cnabookstore.order.exeption.UnableToCancelOrderException;
import cnabookstore.order.external.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long orderId;
    private Long bookId;

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    private Long pointId;
    private Long customerId;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    private String customerName;
    private Integer quantity;

    public Integer getPointValue() {
        return pointValue;
    }

    public void setPointValue(Integer pointValue) {
        this.pointValue = pointValue;
    }

    private Integer pointValue;
    private String deliveryAddress;

    @Column(columnDefinition = "varchar(255) default 'ORDERED'")
    private String orderStatus;

    @PostPersist
    public void onPostPersist() {

        /* 주문 생성시 "ORDERED" 상태일 경우만 메시지 발송 */
        if ("ORDERED".equals(orderStatus)){
            Ordered ordered = new Ordered();
            BeanUtils.copyProperties(this, ordered);
            ordered.publishAfterCommit();
        }
    }

    @PrePersist
    public void onPrePersist(){

        if("null".equals(orderStatus) || orderStatus == null){
            orderStatus = "ORDERED";
        }

        // mappings goes here
        try {
            Book book = OrderApplication.applicationContext.getBean(BookService.class)
                    .queryBook(bookId);
        }
        catch(Exception e){
            orderStatus = "Book_Not_Verified";
        }

        try {
            Customer customer = OrderApplication.applicationContext.getBean(CustomerService.class)
                    .queryCustomer(customerId);
        }
        catch(Exception e){
            orderStatus = "Customer_Not_Verified";
        }

        try {
            Point point = OrderApplication.applicationContext.getBean(PointService.class)
                    .queryPoint(pointId);
        }
        catch(Exception e){
            orderStatus = "Point_Not_Verified";
        }
    }

    @PreRemove()
    public void onPreRemove() throws UnableToCancelOrderException {

        if("ORDERED".equals(this.getOrderStatus())) {
            OrderCanceled orderCanceled = new OrderCanceled();
            BeanUtils.copyProperties(this, orderCanceled);
            orderCanceled.setStatus("CANCELED");
            orderCanceled.publishAfterCommit();
        }
        else{
            throw new UnableToCancelOrderException();
        }
    }


    // getters and setters
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBookId() {
        return bookId;
    }
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}

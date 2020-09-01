package cnabookstore.order;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

import cnabookstore.order.external.Book;
import cnabookstore.order.external.BookService;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long orderId;
    private Long bookId;
    private Long customerId;
    private Integer quantity;
    private String deliveryAddress;
    private String orderStatus;

    @PostPersist
    public void onPostPersist(){

        Ordered ordered = new Ordered();
        BeanUtils.copyProperties(this, ordered);
        ordered.publishAfterCommit();

    }

    @PrePersist
    public void onPrePersist(){

        // mappings goes here
        Book book = OrderApplication.applicationContext.getBean(BookService.class)
                .queryBook(bookId);

    }

    @PreRemove()
    public void onPreRemove(){
        OrderCanceled orderCanceled = new OrderCanceled();
        BeanUtils.copyProperties(this, orderCanceled);
        orderCanceled.setStatus("CANCELED");
        orderCanceled.publishAfterCommit();
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

package com.patzgn.foodieapp.order;

import com.patzgn.foodieapp.item.Item;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class OrderPanelController {

    private OrderRepository orderRepository;

    @GetMapping("/panel/zamowienia")
    String getAllOrders(@RequestParam(required = false) OrderStatus status,
                        Model model) {
        List<Order> orders;
        if (status == null) {
            orders = orderRepository.findAll();
        } else {
            orders = orderRepository.findAllByStatus(status);
        }
        model.addAttribute("orders", orders);
        return "panel/orders";
    }

    @GetMapping("/panel/zamowienie/{id}")
    String singleOrder(@PathVariable Long id,
                       Model model) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(o -> singleOrderPanel(o, model))
                .orElse("redirect:/");
    }

    @PostMapping("/panel/zamowienie/{id}")
    String changeOrderStatus(@PathVariable Long id,
                             Model model) {
        Optional<Order> order = orderRepository.findById(id);
        order.ifPresent(o -> {
            o.setStatus(OrderStatus.nextStatus(o.getStatus()));
            orderRepository.save(o);
        });
        return order.map(o -> singleOrderPanel(o, model))
                .orElse("redirect:/");
    }

    private String singleOrderPanel(Order order, Model model) {
        model.addAttribute("order", order);
        model.addAttribute("sum", order.getItems().stream().mapToDouble(Item::getPrice).sum());
        return "panel/order";
    }

}

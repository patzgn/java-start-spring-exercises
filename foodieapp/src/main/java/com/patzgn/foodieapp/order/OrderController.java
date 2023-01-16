package com.patzgn.foodieapp.order;

import com.patzgn.foodieapp.common.Message;
import com.patzgn.foodieapp.item.Item;
import com.patzgn.foodieapp.item.ItemRepository;
import com.patzgn.foodieapp.order.ClientOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class OrderController {

    private ClientOrder clientOrder;
    private ItemRepository itemRepository;
    private OrderRepository orderRepository;

    @GetMapping("/zamowienie/dodaj")
    String addItemToOrder(@RequestParam Long itemId,
                          Model model) {
        Optional<Item> item = itemRepository.findById(itemId);
        item.ifPresent(clientOrder::add);
        if (item.isPresent()) {
            model.addAttribute("message",
                    new Message("Dodano", "Do zamówienia dodano: " + item.get().getName()));
        } else {
            model.addAttribute("message",
                    new Message("Nie dodano", "Pobrano błędne id z menu: " + itemId));
        }
        return "message";
    }

    @GetMapping("/zamowienie")
    String getCurrentOrder(Model model) {
        model.addAttribute("order", clientOrder.getOrder());
        model.addAttribute("sum", clientOrder
                .getOrder()
                .getItems().stream()
                .mapToDouble(Item::getPrice)
                .sum());
        return "order";
    }

    @PostMapping("/zamowienie/realizuj")
    String proceedOrder(@RequestParam String address,
                        @RequestParam String telephone,
                        Model model) {
        Order order = clientOrder.getOrder();
        order.setAddress(address);
        order.setTelephone(telephone);
        orderRepository.save(order);
        clientOrder.clear();
        model.addAttribute("message",
                new Message("Dziękujemy", "Zamówienie przekazane do realizacji"));
        return "message";
    }

}

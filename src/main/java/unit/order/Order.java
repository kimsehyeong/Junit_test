package unit.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import unit.beverage.Beverage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Order {

    private LocalDateTime localDateTime;
    private List<Beverage> beverages;

    public Order(LocalDateTime localDateTime, List<Beverage> beverages) {
        this.localDateTime = localDateTime;
        this.beverages = beverages;
    }
}

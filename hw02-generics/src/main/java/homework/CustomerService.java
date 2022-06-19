package homework;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> customerToStringMap = new TreeMap<>(
            Comparator.comparing(Customer::getScores)
    );

    public Map.Entry<Customer, String> getSmallest() {
        return copyEntry(customerToStringMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return copyEntry(customerToStringMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customerToStringMap.put(customer, data);
    }

    private Map.Entry<Customer, String> copyEntry(Map.Entry<Customer, String> entry) {
        return entry == null
                ? null
                : new AbstractMap.SimpleEntry<>(entry.getKey().copy(), entry.getValue());
    }
}

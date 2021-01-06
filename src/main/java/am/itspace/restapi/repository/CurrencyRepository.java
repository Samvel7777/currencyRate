package am.itspace.restapi.repository;

import am.itspace.restapi.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}

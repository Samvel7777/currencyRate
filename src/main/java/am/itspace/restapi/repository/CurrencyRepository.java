package am.itspace.restapi.repository;

import am.itspace.restapi.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    List<Currency> findAllByOrderByCreatedDateDesc();
}

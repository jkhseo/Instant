package hello;

import org.springframework.data.repository.CrudRepository;

import hello.Restaurant;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

}

package ru.elena.testwork.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.elena.testwork.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    
}

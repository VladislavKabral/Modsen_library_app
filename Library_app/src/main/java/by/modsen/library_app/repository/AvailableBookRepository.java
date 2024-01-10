package by.modsen.library_app.repository;

import by.modsen.library_app.model.AvailableBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableBookRepository extends JpaRepository<AvailableBook, Integer> {
}

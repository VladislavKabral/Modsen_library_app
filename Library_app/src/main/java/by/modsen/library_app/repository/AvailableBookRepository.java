package by.modsen.library_app.repository;

import by.modsen.library_app.model.AvailableBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvailableBookRepository extends JpaRepository<AvailableBook, Integer> {
    Optional<AvailableBook> findByBook_Id(int bookId);
}

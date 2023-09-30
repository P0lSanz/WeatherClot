package edu.fje2.daw2.spring1.repositoris;

import edu.fje2.daw2.spring1.model.Ciutat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiutatRepository extends MongoRepository<Ciutat, String> {
    Ciutat findByNom(String nom);
    void deleteByNom(String nom);
    public String findIdByNom(String nom);
    void deleteById(String nom);

}
